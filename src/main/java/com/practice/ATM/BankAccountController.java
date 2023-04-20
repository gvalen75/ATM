package com.practice.ATM;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BankAccountController {
    private final BankAccountRepository repository;

    public BankAccountController(BankAccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/BankAccounts")
    List<BankAccount> all(){
        return repository.findAll();
    }
    @GetMapping("/BankAccounts/{id}")
    BankAccount one(@PathVariable Long id){
        return repository.findById(id)
        .orElseThrow(() -> new BankAccountNotFoundException(id));
    }
    @PostMapping("/BankAccounts")
    BankAccount newBankAccount(@RequestBody BankAccount newBankAccount){
        return repository.save(newBankAccount);
    }

    @PutMapping("/BankAccounts/{id}/Deposit")
    BankAccount deposit(@RequestBody Double depositAmount, @PathVariable Long id){
        return repository.findById(id)
                .map(bankAccount -> {
                    bankAccount.deposit(depositAmount);
                    return repository.save(bankAccount);
                })
                .orElseThrow(() -> new BankAccountNotFoundException(id));
    }
    @PutMapping("/BankAccounts/{id}/Withdraw")
    BankAccount withdraw(@RequestBody Double withdrawAmount, @PathVariable Long id){
        return repository.findById(id)
                .map(bankAccount -> {
                    bankAccount.withdraw(withdrawAmount);
                    return repository.save(bankAccount);
                })
                .orElseThrow(() -> new BankAccountNotFoundException(id));
    }
    @DeleteMapping("/BankAccounts/{id}")
    void deleteAccount(@PathVariable Long id){
        repository.deleteById(id);
    }
}
