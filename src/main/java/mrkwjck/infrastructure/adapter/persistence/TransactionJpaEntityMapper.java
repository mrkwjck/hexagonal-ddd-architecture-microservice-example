package mrkwjck.infrastructure.adapter.persistence;

import mrkwjck.domain.transaction.model.TransactionId;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import mrkwjck.domain.transaction.model.Transaction;


@Mapper
interface TransactionJpaEntityMapper {

    TransactionJpaEntityMapper INSTANCE = Mappers.getMapper(TransactionJpaEntityMapper.class);

    @Mapping(target = "account", expression = "java(accountJpaEntity)")
    @Mapping(source = "id", target = "id", qualifiedByName = "toId")
    TransactionJpaEntity toTransactionJpaEntity(Transaction transaction, @Context AccountJpaEntity accountJpaEntity);

    @Named("toId")
    default Long toId(TransactionId id) {
        return id.value();
    }

}
