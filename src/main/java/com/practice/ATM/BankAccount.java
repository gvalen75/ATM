package com.practice.ATM;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
class BankAccount {

    private @Id @GeneratedValue Long id;
    private Long accountNumber;
    private Double accountBalance;

    BankAccount(){}

    public BankAccount(Long accountNumber, Double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(accountBalance, that.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, accountBalance);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
