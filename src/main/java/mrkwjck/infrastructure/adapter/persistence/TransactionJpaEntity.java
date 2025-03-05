package mrkwjck.infrastructure.adapter.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
class TransactionJpaEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_sequence")
    @SequenceGenerator(name = "transaction_id_sequence", allocationSize = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountJpaEntity account;

    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;
}
