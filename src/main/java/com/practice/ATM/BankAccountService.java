package com.practice.ATM;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BankAccountService {

    public abstract BankAccount createBankAccount(BankAccount newBankAccount);
    public abstract BankAccount getBankAccount(Long id);
    public abstract List<BankAccount>  getBankAccounts();
    public abstract void deleteBankAccount(Long id);
    public abstract BankAccount deposit(Long id, Double depositAmount);
    public abstract BankAccount withdraw(Long id, Double withdrawAmount);
}
