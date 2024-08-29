package net.javaguides.banking_app.controller;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
//add account rest api
    @PostMapping
     public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount((accountDto)), HttpStatus.CREATED);
     }
     @GetMapping("/{id}")
    //get account rest api
    public ResponseEntity<AccountDto> getAccountbyId(@PathVariable  long id){
        AccountDto accountDto=accountService.getAccountById(id);
                return ResponseEntity.ok(accountDto);
    }
    // deposit REST API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,@RequestBody  Map<String,Double> request){
        Double amount= request.get("amount");
   AccountDto accountDto= accountService.deposit(id,amount);
     return ResponseEntity.ok(accountDto);
    }

    //withdraw Rest API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw( @PathVariable Long id, @RequestBody  Map<String,Double> request ){
        double amount=request.get("amount");
        AccountDto accountDto= accountService.withdraw(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    //get all account REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
       List<AccountDto> accounts= accountService.getAllAccounts();
       return ResponseEntity.ok(accounts);
    }

    //delete account REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account delete successfully");
    }


}
