package com.practice.ATM;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class BankAccountController {
    BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/BankAccounts")
    CollectionModel<EntityModel<BankAccount>> all(){
        return bankAccountService.getBankAccounts();
    }
    @GetMapping("/BankAccounts/{id}")
    EntityModel<BankAccount> one(@PathVariable Long id){
        return bankAccountService.getBankAccount(id);
    }
    @PostMapping("/BankAccounts")
    ResponseEntity<?> newBankAccount(@RequestBody BankAccount newBankAccount){
        return bankAccountService.createBankAccount(newBankAccount);
    }

    @PutMapping("/BankAccounts/{id}/Deposit")
    ResponseEntity<?> deposit(@RequestBody Double depositAmount, @PathVariable Long id){
        return bankAccountService.deposit(id, depositAmount);
    }
    @PutMapping("/BankAccounts/{id}/Withdraw")
    ResponseEntity<?> withdraw(@RequestBody Double withdrawAmount, @PathVariable Long id){
        return bankAccountService.withdraw(id, withdrawAmount);
    }
    @DeleteMapping("/BankAccounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id){
        return bankAccountService.deleteBankAccount(id);
    }
}
