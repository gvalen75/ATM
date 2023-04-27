package com.practice.ATM;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
public @Data class BankAccount {

    private @Id @GeneratedValue Long id;
    private @NonNull Double accountBalance;

}
