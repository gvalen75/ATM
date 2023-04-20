package com.practice.ATM;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
class BankAccount {

    private @Id @GeneratedValue Long id;
    private Double accountBalance;

    BankAccount(){}

    public BankAccount(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public void deposit(Double depositAmount){
        this.accountBalance += depositAmount;
    }
    public void withdraw(Double withdrawAmount){
        this.accountBalance -= withdrawAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id)  && Objects.equals(accountBalance, that.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountBalance);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
