package mrkwjck.infrastructure.adapter.rest

import com.fasterxml.jackson.databind.ObjectMapper
import mrkwjck.IntegrationTest
import mrkwjck.domain.AccountDomainService
import mrkwjck.domain.account.AccountRepository
import mrkwjck.domain.account.event.AccountCreated
import mrkwjck.domain.account.event.FundsDeposited
import mrkwjck.domain.account.event.FundsWithdrawn
import mrkwjck.testutils.Cleanup
import mrkwjck.testutils.MessageCollector
import org.iban4j.Iban
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static mrkwjck.domain.transaction.model.TransactionType.DEPOSIT
import static mrkwjck.domain.transaction.model.TransactionType.WITHDRAWAL
import static mrkwjck.testutils.MessageCollector.ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class AccountResourceIntegrationTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private AccountRepository accountRepository

    @Autowired
    private AccountDomainService accountDomainService

    @Autowired
    private OutputDestination outputDestination

    @Autowired
    private Cleanup cleanup

    void cleanup() {
        cleanup.cleanDatabase()
        outputDestination.clear()
    }

    void "should create account and return account details for this account"() {
        given:
        var accountOwnerName = "Jan Kowalski"
        var request = new CreateAccountRequest(accountOwnerName)
        var messageCollector = new MessageCollector(outputDestination, objectMapper)

        when:
        var createAccountResponse = mockMvc.perform(post("/api/v1/accounts")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        var createAccountResponseDto =
                objectMapper.readValue(createAccountResponse.getContentAsString(), AccountDetailsResponse.class)

        and:
        var accountDetailsResponse = mockMvc.perform(
                get("/api/v1/accounts/%s".formatted(createAccountResponseDto.accountNumber()))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        var accountDetailsResponseDto =
                objectMapper.readValue(accountDetailsResponse.getContentAsString(), AccountDetailsResponse.class)

        then:
        accountDetailsResponse.status == HttpStatus.OK.value()
        accountDetailsResponseDto.accountNumber() == createAccountResponseDto.accountNumber()
        accountDetailsResponseDto.ownerName() == accountOwnerName

        and:
        var event = messageCollector.receive(ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME, AccountCreated.class)
        event != null
        event.getAccountNumber() == createAccountResponseDto.accountNumber()
    }

    void "should return HTTP 404 for non-existing account"() {
        when:
        var accountDetailsResponse = mockMvc.perform(
                get("/api/v1/accounts/%s".formatted(Iban.random())).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()

        then:
        accountDetailsResponse.status == HttpStatus.NOT_FOUND.value()
    }

    void "should deposit and withdraw funds and return list of related transactions"() {
        given:
        var accountOwnerName = "Jan Kowalski"
        var messageCollector = new MessageCollector(outputDestination, objectMapper)
        var account = accountDomainService.createAccount(accountOwnerName)
        var depositAmount = new BigDecimal("45.00")
        var withdrawalAmount = new BigDecimal("15.00")
        var depositFundsRequest = new DepositFundsRequest(depositAmount)
        var withdrawFundsRequest = new WithdrawFundsRequest(withdrawalAmount)

        when: "deposit is performed"
        mockMvc.perform(post("/api/v1/accounts/%s/deposit".formatted(account.getAccountNumber().toString()))
                .content(objectMapper.writeValueAsString(depositFundsRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        account = accountRepository.findByAccountNumber(account.getAccountNumber())

        then:
        account.getBalance() == depositAmount

        and:
        var fundsDepositedEvent =
                messageCollector.receive(ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME, FundsDeposited.class)
        fundsDepositedEvent != null
        fundsDepositedEvent.getAmount() == depositAmount

        when: "withdrawal is performed"
        mockMvc.perform(post("/api/v1/accounts/%s/withdrawal".formatted(account.getAccountNumber().toString()))
                .content(objectMapper.writeValueAsString(withdrawFundsRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        account = accountRepository.findByAccountNumber(account.getAccountNumber())

        then:
        account.getBalance() == depositAmount.subtract(withdrawalAmount)

        and:
        var fundsWithdrawnEvent =
                messageCollector.receive(ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME, FundsWithdrawn.class)
        fundsWithdrawnEvent != null
        fundsWithdrawnEvent.getAmount() == withdrawalAmount

        when: "list of performed transaction is queries"
        var accountTransactionsResponse = mockMvc.perform(
                get("/api/v1/accounts/%s/transactions".formatted(account.getAccountNumber().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        var accountTransactionsResponseDto =
                objectMapper.readValue(accountTransactionsResponse.getContentAsString(), AccountTransactionsResponse.class)

        then:
        accountTransactionsResponseDto.getAccountTransactions().size() == 2
        accountTransactionsResponseDto.getAccountTransactions().find(
                t -> t.type() == WITHDRAWAL.toString() && t.amount() == withdrawalAmount) != null
        accountTransactionsResponseDto.getAccountTransactions().find(
                t -> t.type() == DEPOSIT.toString() && t.amount() == depositAmount) != null
    }

}
