package com.practice.ATM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BankAccountRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new BankAccount( 500.0)));
            log.info("Preloading " + repository.save(new BankAccount(10000.50)));
            log.info("Preloading " + repository.save(new BankAccount(20.50)));
        };
    }
}
