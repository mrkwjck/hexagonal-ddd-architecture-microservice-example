package mrkwjck.infrastructure.adapter.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
class AccountJpaEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_sequence")
    @SequenceGenerator(name = "account_id_sequence", allocationSize = 10)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "currency")
    private String currency;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "owner_name")
    private String ownerName;

}
