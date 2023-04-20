package com.practice.ATM;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class BankAccountModelAssembler implements RepresentationModelAssembler<BankAccount, EntityModel<BankAccount>> {
    @Override
    public EntityModel<BankAccount> toModel(BankAccount bankAccount) {
        return EntityModel.of(bankAccount,
                linkTo(methodOn(BankAccountController.class).one(bankAccount.getId())).withSelfRel(),
                linkTo(methodOn(BankAccountController.class).all()).withRel("BankAccounts"));
    }
}
