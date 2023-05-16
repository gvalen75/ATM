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
    public BankAccountServiceImpl(BankAccountRepository repository, BankAccountModelAssembler assembler) {
        this.repository = repository;
    }
    @Override
    public List<BankAccount>  getBankAccounts() {
        List<BankAccount> bankAccounts = repository.findAll();
        if(bankAccounts.isEmpty()){
            throw new BankAccountNotFoundException(0L);
        }
        else{
            return bankAccounts;
        }
    }

    @Override
    public BankAccount getBankAccount(Long id) {
        BankAccount bankAccount = repository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));
        return bankAccount;
    }

    @Override
    public BankAccount createBankAccount(BankAccount newBankAccount) {
        return repository.save(newBankAccount);
    }

    @Override
    public void deleteBankAccount(Long id) {
        repository.deleteById(id);
    }

    @Override
    public BankAccount deposit(Long id, Double depositAmount) {
        BankAccount updatedBankAccount = repository.findById(id)
                .map(bankAccount -> {
                    bankAccount.setAccountBalance(bankAccount.getAccountBalance() + depositAmount);
                    return repository.save(bankAccount);
                })
                .orElseThrow(() -> new BankAccountNotFoundException(id));
        return updatedBankAccount;
    }

    @Override
    public BankAccount withdraw(Long id, Double withdrawAmount) {
        BankAccount updatedBankAccount = repository.findById(id)
                .map(bankAccount -> {
                    bankAccount.setAccountBalance(bankAccount.getAccountBalance() - withdrawAmount);
                    return repository.save(bankAccount);
                })
                .orElseThrow(() -> new BankAccountNotFoundException(id));
        return updatedBankAccount;
    }
}
