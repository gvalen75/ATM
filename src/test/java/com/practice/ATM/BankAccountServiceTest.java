package com.practice.ATM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Autowired
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;
    private BankAccount bankAccount1;
    private BankAccount bankAccount2;
    List<BankAccount> bankAccounts;

    @BeforeEach
    public void setUp() {
        bankAccounts = new ArrayList<>();
        bankAccount1 = new BankAccount(500.0);
        bankAccount2 = new BankAccount(100.0);
        bankAccounts.add(bankAccount1);
        bankAccounts.add(bankAccount2);
    }
    @AfterEach
    public void tearDown(){
        bankAccount1 = null;
        bankAccount2 = null;
        bankAccounts = null;
    }
    @Test
    public void getBankAccountsTest(){
        bankAccountRepository.save(bankAccount1);
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
        //List<BankAccount> bankAccounts1 = bankAccountService.getBankAccounts();
    }

}
