package net.javaguides.banking_app.mapper;

import net.javaguides.banking_app.dto.TransactionHistoryDto;
import net.javaguides.banking_app.entity.TransactionHistory;

public class TransactionHistoryMapper {

    public static TransactionHistory mapToTransactionHistory(TransactionHistoryDto transactionHistoryDto) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setId(transactionHistoryDto.getId());
        transactionHistory.setAccountId(transactionHistoryDto.getAccountId());
        transactionHistory.setTransactionAmount(transactionHistoryDto.getTransactionAmount());
        transactionHistory.setTransactionType(transactionHistoryDto.getTransactionType());
        transactionHistory.setTransactionDate(transactionHistoryDto.getTransactionDate());
        return transactionHistory;
    }

    public static TransactionHistoryDto mapToTransactionHistoryDto(TransactionHistory transactionHistory) {
        return new TransactionHistoryDto(
                transactionHistory.getId(),
                transactionHistory.getAccountId(),
                transactionHistory.getTransactionAmount(),
                transactionHistory.getTransactionType(),
                transactionHistory.getTransactionDate()
        );
    }
}
