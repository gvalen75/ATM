package com.practice.ATM;

import org.springframework.data.jpa.repository.JpaRepository;

interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
