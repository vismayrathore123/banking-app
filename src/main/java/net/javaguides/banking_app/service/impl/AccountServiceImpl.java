package net.javaguides.banking_app.service.impl;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.dto.TransactionHistoryDto;
import net.javaguides.banking_app.entity.Account;
import net.javaguides.banking_app.entity.TransactionHistory;
import net.javaguides.banking_app.mapper.AccountMapper;
import net.javaguides.banking_app.mapper.TransactionHistoryMapper;
import net.javaguides.banking_app.repository.AccountRepository;
import net.javaguides.banking_app.repository.TransactionHistoryRepository;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    // Single constructor to initialize both repositories
    public AccountServiceImpl(AccountRepository accountRepository, TransactionHistoryRepository transactionHistoryRepository) {
        this.accountRepository = accountRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        // Log the transaction
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAccountId(id);
        transactionHistory.setTransactionAmount(amount);
        transactionHistory.setTransactionType("DEPOSIT");
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistoryRepository.save(transactionHistory);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        // Log the transaction
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAccountId(id);
        transactionHistory.setTransactionAmount(amount);
        transactionHistory.setTransactionType("WITHDRAW");
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistoryRepository.save(transactionHistory);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        accountRepository.deleteById(id);
    }

    @Override
    public List<TransactionHistoryDto> getTransactionHistory(Long accountId) {
        List<TransactionHistory> transactions = transactionHistoryRepository.findByAccountId(accountId);
        return transactions.stream()
                .map(TransactionHistoryMapper::mapToTransactionHistoryDto)
                .collect(Collectors.toList());
    }
}
