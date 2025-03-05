package mrkwjck.infrastructure.adapter.persistence;

import mrkwjck.domain.transaction.model.Transaction;
import mrkwjck.domain.transaction.model.TransactionId;
import org.iban4j.Iban;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
interface TransactionJpaEntityMapper {

    TransactionJpaEntityMapper INSTANCE = Mappers.getMapper(TransactionJpaEntityMapper.class);

    @Mapping(target = "account", expression = "java(accountJpaEntity)")
    @Mapping(source = "id", target = "id", qualifiedByName = "toLongId")
    TransactionJpaEntity toTransactionJpaEntity(Transaction transaction, @Context AccountJpaEntity accountJpaEntity);

    @Mapping(source = "account", target = "accountNumber", qualifiedByName = "toIbanAccountNumber")
    @Mapping(source = "id", target = "id", qualifiedByName = "toTransactionId")
    Transaction toTransaction(TransactionJpaEntity transactionJpaEntity);

    @Named("toLongId")
    default Long toLongId(TransactionId id) {
        return id.value();
    }

    @Named("toTransactionId")
    default TransactionId toTransactionId(Long id) {
        return new TransactionId(id);
    }

    @Named("toIbanAccountNumber")
    default Iban toIbanAccountNumber(AccountJpaEntity accountJpaEntity) {
        return Iban.valueOf(accountJpaEntity.getAccountNumber());
    }
}
