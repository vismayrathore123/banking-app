package net.javaguides.banking_app.service.impl;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.dto.TransactionHistoryDto;
import net.javaguides.banking_app.entity.Account;
import net.javaguides.banking_app.entity.TransactionHistory;
import net.javaguides.banking_app.exception.CustomException;
import net.javaguides.banking_app.mapper.AccountMapper;
import net.javaguides.banking_app.mapper.TransactionHistoryMapper;
import net.javaguides.banking_app.repository.AccountRepository;
import net.javaguides.banking_app.repository.TransactionHistoryRepository;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
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
        Account account = accountRepository.findById(id).orElseThrow(() -> new CustomException("Account not found", 404));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new CustomException("Account not found", 404));
        account.setBalance(account.getBalance() + amount);
        Account updatedAccount = accountRepository.save(account);

        // Save transaction history
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAccountId(id);
        transactionHistory.setTransactionAmount(amount);
        transactionHistory.setTransactionType("Deposit");
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setBalanceAfterTransaction(account.getBalance());
        transactionHistoryRepository.save(transactionHistory);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new CustomException("Account not found", 404));
        if (account.getBalance() < amount) {
            throw new CustomException("Insufficient balance", 400);
        }
        account.setBalance(account.getBalance() - amount);
        Account updatedAccount = accountRepository.save(account);

        // Save transaction history
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAccountId(id);
        transactionHistory.setTransactionAmount(amount);
        transactionHistory.setTransactionType("Withdraw");
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setBalanceAfterTransaction(account.getBalance());
        transactionHistoryRepository.save(transactionHistory);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new CustomException("Account not found", 404);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionHistoryDto> getTransactionHistory(Long accountId) {
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findByAccountId(accountId);
        return transactionHistoryList.stream()
                .map(TransactionHistoryMapper::mapToTransactionHistoryDto)
                .collect(Collectors.toList());
    }
}
