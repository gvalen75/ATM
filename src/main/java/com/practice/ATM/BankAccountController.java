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
    private final BankAccountRepository repository;
    private final BankAccountModelAssembler assembler;

    public BankAccountController(BankAccountRepository repository, BankAccountModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/BankAccounts")
    CollectionModel<EntityModel<BankAccount>> all(){
        List<EntityModel<BankAccount>> bankAccounts = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bankAccounts, linkTo(methodOn(BankAccountController.class).all()).withSelfRel());
    }
    @GetMapping("/BankAccounts/{id}")
    EntityModel<BankAccount> one(@PathVariable Long id){
        BankAccount bankAccount = repository.findById(id)
        .orElseThrow(() -> new BankAccountNotFoundException(id));

        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccount);
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, id)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, id)).withRel("Withdraw"));
        return entityModel;
    }
    @PostMapping("/BankAccounts")
    ResponseEntity<?> newBankAccount(@RequestBody BankAccount newBankAccount){
        EntityModel<BankAccount> entityModel = assembler.toModel(repository.save(newBankAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/BankAccounts/{id}/Deposit")
    ResponseEntity<?> deposit(@RequestBody Double depositAmount, @PathVariable Long id){
        BankAccount updatedBankAccount = repository.findById(id)
                .map(bankAccount -> {
                    bankAccount.deposit(depositAmount);
                    return repository.save(bankAccount);
                })
                .orElseThrow(() -> new BankAccountNotFoundException(id));
        EntityModel<BankAccount> entityModel = assembler.toModel(updatedBankAccount);
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit(depositAmount, id)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, id)).withRel("Withdraw"));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @PutMapping("/BankAccounts/{id}/Withdraw")
    ResponseEntity<?> withdraw(@RequestBody Double withdrawAmount, @PathVariable Long id){
        BankAccount updatedBankAccount = repository.findById(id)
                .map(bankAccount -> {
                    bankAccount.withdraw(withdrawAmount);
                    return repository.save(bankAccount);
                })
                .orElseThrow(() -> new BankAccountNotFoundException(id));
        EntityModel<BankAccount> entityModel = assembler.toModel(updatedBankAccount);
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, id)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw(withdrawAmount, id)).withRel("Withdraw"));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    @DeleteMapping("/BankAccounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
