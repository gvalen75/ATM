package com.practice.ATM;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BankAccountService {

    public abstract ResponseEntity<?> createBankAccount(BankAccount newBankAccount);
    public abstract EntityModel<BankAccount> getBankAccount(Long id);
    public abstract CollectionModel<EntityModel<BankAccount>> getBankAccounts();
    public abstract ResponseEntity<?> deleteBankAccount(Long id);
    public abstract ResponseEntity<?> deposit(Long id, Double depositAmount);
    public abstract ResponseEntity<?> withdraw(Long id, Double withdrawAmount);
}
