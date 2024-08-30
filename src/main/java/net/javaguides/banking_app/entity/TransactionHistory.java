package net.javaguides.banking_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_history")
@Entity
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Account ID is required")
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Min(value = 0, message = "Transaction amount must be zero or positive")
    @Column(name = "transaction_amount", nullable = false)
    private double transactionAmount;

    @NotBlank(message = "Transaction type must not be blank")
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @NotNull(message = "Transaction date is required")
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Min(value = 0, message = "Balance after transaction must be zero or positive")
    @Column(name = "balance_after_transaction", nullable = false)
    private double balanceAfterTransaction;  // New column for remaining balance
}
