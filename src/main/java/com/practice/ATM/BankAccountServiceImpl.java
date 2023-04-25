package com.practice.ATM;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository repository;
    private final BankAccountModelAssembler assembler;
    public BankAccountServiceImpl(BankAccountRepository repository, BankAccountModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    @Override
    public CollectionModel<EntityModel<BankAccount>> getBankAccounts() {
        List<EntityModel<BankAccount>> bankAccounts = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bankAccounts, linkTo(methodOn(BankAccountController.class).all()).withSelfRel());
    }

    @Override
    public EntityModel<BankAccount> getBankAccount(Long id) {
        BankAccount bankAccount = repository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));

        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccount);
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, id)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, id)).withRel("Withdraw"));
        return entityModel;
    }

    @Override
    public ResponseEntity<?> createBankAccount(BankAccount newBankAccount) {
        EntityModel<BankAccount> entityModel = assembler.toModel(repository.save(newBankAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public ResponseEntity<?> deleteBankAccount(Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> deposit(Long id, Double depositAmount) {
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

    @Override
    public ResponseEntity<?> withdraw(Long id, Double withdrawAmount) {
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
}
