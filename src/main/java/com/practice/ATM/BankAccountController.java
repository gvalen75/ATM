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
    BankAccountServiceImpl bankAccountService;

    public BankAccountController(BankAccountServiceImpl bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/BankAccounts")
    CollectionModel<EntityModel<BankAccount>> all(){
        List<EntityModel<BankAccount>> bankAccounts = bankAccountService.getBankAccounts();
        return CollectionModel.of(bankAccounts, linkTo(methodOn(BankAccountController.class).all()).withSelfRel());
    }
    @GetMapping("/BankAccounts/{id}")
    EntityModel<BankAccount> one(@PathVariable Long id){
        return bankAccountService.getBankAccount(id);
    }
    @PostMapping("/BankAccounts")
    ResponseEntity<?> newBankAccount(@RequestBody BankAccount newBankAccount){
        EntityModel<BankAccount> entityModel = bankAccountService.createBankAccount(newBankAccount);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/BankAccounts/{id}/Deposit")
    ResponseEntity<?> deposit(@RequestBody Double depositAmount, @PathVariable Long id){
        EntityModel<BankAccount> entityModel = bankAccountService.deposit(id, depositAmount);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @PutMapping("/BankAccounts/{id}/Withdraw")
    ResponseEntity<?> withdraw(@RequestBody Double withdrawAmount, @PathVariable Long id){
        EntityModel<BankAccount> entityModel = bankAccountService.withdraw(id, withdrawAmount);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @DeleteMapping("/BankAccounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id){
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }
}
