package org.acme.service;

import io.quarkus.runtime.Shutdown;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.acme.model.Account;
import org.acme.repository.AccountRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
@Log
public class Housekeeping {
    @Inject
    AccountRepository accountRepository;

    @Startup
    @Transactional
    public void startup() {
        var account = new Account();
        account.setBalance(4500);
        account.setIban("CH1234567890123456789");
        account.setName("Test Account");
        accountRepository.persist(account);
    }
}
