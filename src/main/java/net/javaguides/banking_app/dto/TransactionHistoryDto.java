package net.javaguides.banking_app.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionHistoryDto {

    private Long id;

    private Long accountId;

    @Min(value = 0, message = "Transaction amount must be a positive number")
    private double transactionAmount;

    @NotNull(message = "Transaction type is required")
    private String transactionType;

    private LocalDateTime transactionDate;

    private double balanceAfterTransaction;
}
