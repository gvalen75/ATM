package com.practice.ATM;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(MockitoExtension.class)
class BankAccountControllerTest {

    @Mock
    private BankAccountServiceImpl bankAccountService;

    @Autowired
    @InjectMocks
    private BankAccountController bankAccountController;
    private final BankAccountModelAssembler assembler = new BankAccountModelAssembler();
    BankAccount bankAccount1;
    @BeforeEach
    public void setUp() {
        bankAccount1 = new BankAccount(500.0);
        bankAccount1.setId(1L);
        bankAccountController = new BankAccountController(bankAccountService,assembler);
    }
    @AfterEach
    public void tearDown(){
        bankAccount1 = null;
    }
    @Test
    void getBankAccountsControllerTest() throws BankAccountNotFoundException{
        List<EntityModel<BankAccount>> entityModels = new ArrayList<>();
        List<BankAccount> bankAccounts = new ArrayList<>();
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccount1);
        entityModels.add(entityModel);
        bankAccounts.add(bankAccount1);
        when(bankAccountService.getBankAccounts()).thenReturn(bankAccounts);
        assertEquals(CollectionModel.of(entityModels, linkTo(methodOn(BankAccountController.class).all()).withSelfRel()), bankAccountController.all());
    }
    @Test
    void getBankAccountsControllerTestException() throws BankAccountNotFoundException{
        when(bankAccountService.getBankAccounts()).thenThrow(BankAccountNotFoundException.class);
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountController.all());
    }
    @Test
    void getBankAccountControllerTest() throws BankAccountNotFoundException{
        when(bankAccountService.getBankAccount(any())).thenReturn(bankAccount1);
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccount1);
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, 1L)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, 1L)).withRel("Withdraw"));
        assertEquals(entityModel, bankAccountController.one(1L));
    }
    @Test
    void getBankAccountControllerTestException() throws BankAccountNotFoundException{
        when(bankAccountService.getBankAccount(50L)).thenThrow(BankAccountNotFoundException.class);
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountController.one(50L));
    }
    @Test
    void postNewBankAccountControllerTest(){
        when(bankAccountService.createBankAccount(any())).thenReturn(bankAccount1);
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccount1);
        ResponseEntity<EntityModel<BankAccount>> responseEntity = ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        assertEquals(responseEntity, bankAccountController.newBankAccount(bankAccount1));
    }
    @Test
    void putDepositControllerTest() throws BankAccountNotFoundException{
        when(bankAccountService.deposit(1L, (double) 50)).thenReturn(bankAccount1);
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccount1);
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, 1L)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, 1L)).withRel("Withdraw"));
        ResponseEntity<EntityModel<BankAccount>> responseEntity = ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        assertEquals(responseEntity, bankAccountController.deposit((double) 50, 1L));
    }
    @Test
    void putDepositControllerTestException() throws BankAccountNotFoundException{
        when(bankAccountService.deposit(50L, 50.0)).thenThrow(BankAccountNotFoundException.class);
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountController.deposit(50.0,50L));
    }
    @Test
    void putWithdrawControllerTestException() throws BankAccountNotFoundException{
        when(bankAccountService.withdraw(50L, 50.0)).thenThrow(BankAccountNotFoundException.class);
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountController.withdraw(50.0,50L));
    }
    @Test
    void putWithdrawControllerTest() throws BankAccountNotFoundException{
        when(bankAccountService.withdraw(1L, (double) 50)).thenReturn(bankAccount1);
        EntityModel<BankAccount> entityModel = assembler.toModel(bankAccount1);
        entityModel.add(linkTo(methodOn(BankAccountController.class).deposit((double) 0, 1L)).withRel("Deposit"));
        entityModel.add(linkTo(methodOn(BankAccountController.class).withdraw((double) 0, 1L)).withRel("Withdraw"));
        ResponseEntity<EntityModel<BankAccount>> responseEntity = ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        assertEquals(responseEntity, bankAccountController.withdraw((double) 50, 1L));
    }
    @Test
    void deleteControllerTest(){
        assertEquals(ResponseEntity.noContent().build(), bankAccountController.deleteAccount(1L));
    }


}
