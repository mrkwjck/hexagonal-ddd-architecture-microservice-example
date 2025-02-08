package mrkwjck.infrastructure.adapter.persistence;

import org.iban4j.Iban;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import mrkwjck.domain.account.model.Account;


@Mapper
interface AccountJpaEntityMapper {

    AccountJpaEntityMapper INSTANCE = Mappers.getMapper(AccountJpaEntityMapper.class);

    @Mapping(source = "accountNumber", target = "accountNumber", qualifiedByName = "toAccountNumberString")
    AccountJpaEntity toAccountJpaEntity(Account account);

    @Mapping(source = "accountNumber", target = "accountNumber", qualifiedByName = "toAccountNumberIban")
    Account toAccountDomain(AccountJpaEntity entity);

    @Named("toAccountNumberString")
    default String toAccountNumberString(Iban accountNumber) {
        return accountNumber.toString();
    }

    @Named("toAccountNumberIban")
    default Iban toAccountNumberIban(String accountNumber) {
        return Iban.valueOf(accountNumber);
    }

}
