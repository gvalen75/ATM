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
import java.util.Optional;


import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Autowired
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;
    private BankAccount bankAccount1;
    List<BankAccount> bankAccounts;

    @BeforeEach
    public void setUp() {
        bankAccounts = new ArrayList<>();
        bankAccount1 = new BankAccount(500.0);
        bankAccount1.setId(1L);
        bankAccounts.add(bankAccount1);
    }
    @AfterEach
    public void tearDown(){
        bankAccount1 = null;
    }
    @Test
    void createBankAccountTest(){
        when(bankAccountRepository.save(any())).thenReturn(bankAccount1);
        assertEquals(bankAccount1, bankAccountService.createBankAccount(bankAccount1));
    }
    @Test
    void getBankAccountsTest() throws BankAccountNotFoundException{
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
        assertEquals(bankAccounts, bankAccountService.getBankAccounts());
    }
    @Test
    void getBankAccountsException() throws BankAccountNotFoundException {
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.getBankAccounts());
    }
    @Test
    void getBankAccount() throws BankAccountNotFoundException{
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(new BankAccount(300.0)));
        assertEquals(300.0, bankAccountService.getBankAccount(5L).getAccountBalance());
    }
    @Test
    void getBankAccountException() throws BankAccountNotFoundException{
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.getBankAccount(6L));
    }
    @Test
    void depositTest() throws BankAccountNotFoundException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(new BankAccount(500.0)));
        when(bankAccountRepository.save(any())).thenReturn(new BankAccount(550.0));
        assertEquals(550.0, bankAccountService.deposit(1L, 50.0 ).getAccountBalance());
    }
    @Test
    void depositTestException() throws BankAccountNotFoundException{
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.deposit(1L, 50.0));
    }
    @Test
    void withdrawTest() throws BankAccountNotFoundException {
        when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(new BankAccount(500.0)));
        when(bankAccountRepository.save(any())).thenReturn(new BankAccount(450.0));
        assertEquals(450.0, bankAccountService.withdraw(1L, 50.0 ).getAccountBalance());
    }
    @Test
    void withdrawTestException() throws BankAccountNotFoundException{
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.withdraw(50L, 50.0));
    }
    @Test
    void deleteTest(){
        when(bankAccountRepository.save(any())).thenReturn(bankAccount1);
        bankAccountService.createBankAccount(bankAccount1);
        bankAccountService.deleteBankAccount(1L);
        verify(bankAccountRepository, times(1)).deleteById(1L);
    }
}
