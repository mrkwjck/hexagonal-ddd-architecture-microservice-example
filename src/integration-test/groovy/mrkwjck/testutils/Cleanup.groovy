package mrkwjck.testutils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class Cleanup {

    @Autowired
    JdbcTemplate jdbcTemplate

    void deleteAllAccounts() {
        jdbcTemplate.execute("delete from accounts")
    }

    void deleteAllTransactions() {
        jdbcTemplate.execute("delete from transactions")
    }

    void cleanDatabase() {
        deleteAllTransactions()
        deleteAllAccounts()
    }

}
