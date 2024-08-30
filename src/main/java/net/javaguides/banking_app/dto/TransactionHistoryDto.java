package net.javaguides.banking_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionHistoryDto {
    private Long id;
    private Long accountId;
    private double transactionAmount;
    private String transactionType;
    private LocalDateTime transactionDate;
    private double balanceAfterTransaction;  // New field for remaining balance
}
