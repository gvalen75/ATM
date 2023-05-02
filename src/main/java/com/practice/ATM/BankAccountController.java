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
    private final BankAccountModelAssembler assembler;

    public BankAccountController(BankAccountServiceImpl bankAccountService, BankAccountModelAssembler assembler) {
        this.bankAccountService = bankAccountService;
        this.assembler = assembler;
    }

    @GetMapping("/BankAccounts")
    CollectionModel<EntityModel<BankAccount>> all(){
        List<EntityModel<BankAccount>> bankAccounts = bankAccountService.getBankAccounts().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(bankAccounts, linkTo(methodOn(BankAccountController.class).all()).withSelfRel());
    }
    @GetMapping("/BankAccounts/{id}")
    EntityModel<BankAccount> one(@PathVariable Long id){
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccountService.getBankAccount(id));
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, id)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, id)).withRel("Withdraw"));
        return entityModel;
    }
    @PostMapping("/BankAccounts")
    ResponseEntity<?> newBankAccount(@RequestBody BankAccount newBankAccount){
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccountService.createBankAccount(newBankAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/BankAccounts/{id}/Deposit")
    ResponseEntity<?> deposit(@RequestBody Double depositAmount, @PathVariable Long id){
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccountService.deposit(id, depositAmount));
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit(depositAmount, id)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, id)).withRel("Withdraw"));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @PutMapping("/BankAccounts/{id}/Withdraw")
    ResponseEntity<?> withdraw(@RequestBody Double withdrawAmount, @PathVariable Long id){
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccountService.withdraw(id, withdrawAmount));
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, id)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw(withdrawAmount, id)).withRel("Withdraw"));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @DeleteMapping("/BankAccounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id){
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }
}
