package mrkwjck.infrastructure.adapter.rest

import com.fasterxml.jackson.databind.ObjectMapper
import mrkwjck.IntegrationTest
import mrkwjck.domain.AccountDomainService
import mrkwjck.domain.AccountTransactionDomainService
import mrkwjck.domain.account.AccountRepository
import mrkwjck.domain.account.event.FundsDeposited
import mrkwjck.domain.account.event.FundsWithdrawn
import mrkwjck.testutils.Cleanup
import mrkwjck.testutils.MessageCollector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static mrkwjck.testutils.MessageCollector.ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class TransferResourceIntegrationTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private AccountRepository accountRepository

    @Autowired
    private AccountDomainService accountDomainService

    @Autowired
    private AccountTransactionDomainService accountTransactionDomainService

    @Autowired
    private OutputDestination outputDestination

    @Autowired
    private Cleanup cleanup

    void cleanup() {
        cleanup.cleanDatabase()
        outputDestination.clear()
    }

    void "should transfer funds between accounts"() {
        given:
        var messageCollector = new MessageCollector(outputDestination, objectMapper)
        var sourceAccount = accountDomainService.createAccount("Jan Kowalski")
        var targetAccount = accountDomainService.createAccount("Maria Kowalska")
        var depositAmount = new BigDecimal("100.00")
        var transferAmount = new BigDecimal("70.00")
        accountTransactionDomainService.depositFunds(sourceAccount.getAccountNumber(), depositAmount)
        var transferRequest = new TransferFundsRequest(
                sourceAccount.getAccountNumber().toString(),
                targetAccount.getAccountNumber().toString(),
                transferAmount)

        when:
        var transferResponse = mockMvc.perform(post("/api/v1/transfers")
                .content(objectMapper.writeValueAsString(transferRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        sourceAccount = accountRepository.findByAccountNumber(sourceAccount.getAccountNumber())
        targetAccount = accountRepository.findByAccountNumber(targetAccount.getAccountNumber())

        then: "accounts balances are OK"
        transferResponse.status == HttpStatus.OK.value()
        sourceAccount.getBalance() == depositAmount.subtract(transferAmount)
        targetAccount.getBalance() == transferAmount

        and: "source account withdrawal event has been published"
        var fundsWithdrawnEvent =
                messageCollector.receive(ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME, FundsWithdrawn.class)
        fundsWithdrawnEvent != null
        fundsWithdrawnEvent.getAccountNumber() == sourceAccount.getAccountNumber().toString()
        fundsWithdrawnEvent.getAmount() == transferAmount
        fundsWithdrawnEvent.getName() == "FUNDS_WITHDRAWN"

        and: "target account deposit event has been published"
        var fundsDepositedEvent =
                messageCollector.receive(ACCOUNT_EVENTS_OUTPUT_TOPIC_NAME, FundsDeposited.class)
        fundsDepositedEvent != null
        fundsDepositedEvent.getAccountNumber() == targetAccount.getAccountNumber().toString()
        fundsDepositedEvent.getAmount() == transferAmount
        fundsDepositedEvent.getName() == "FUNDS_DEPOSITED"
    }

}
