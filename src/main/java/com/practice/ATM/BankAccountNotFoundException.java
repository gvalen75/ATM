package com.practice.ATM;

class BankAccountNotFoundException extends RuntimeException {
    BankAccountNotFoundException(Long id){
        super("could not find Bank Account # " + id);
    }
}
