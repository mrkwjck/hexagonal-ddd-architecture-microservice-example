package mrkwjck.application.port.in;

import java.util.List;

public interface GetAccountTransactionsUseCase
        extends QueryAndResultUseCase<AccountTransactionsQuery, List<AccountTransaction>> {}
