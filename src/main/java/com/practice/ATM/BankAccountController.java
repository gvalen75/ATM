package com.practice.ATM;

import org.springframework.web.bind.annotation.RestController;

@RestController
class BankAccountController {
    private final BankAccountRepository repository;

    public BankAccountController(BankAccountRepository repository) {
        this.repository = repository;
    }

    
}
