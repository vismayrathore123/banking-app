package net.javaguides.banking_app.controller;

import net.javaguides.banking_app.dto.TransactionHistoryDto;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionHistoryController {

    private final AccountService accountService;

    public TransactionHistoryController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public List<TransactionHistoryDto> getTransactionHistory(@PathVariable Long accountId) {
        return accountService.getTransactionHistory(accountId);
    }
}
