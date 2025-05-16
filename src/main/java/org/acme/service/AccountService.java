package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.acme.model.Account;
import org.acme.AddAccountRequest;
import org.acme.repository.AccountRepository;

import java.util.HashMap;
import java.util.UUID;

@ApplicationScoped
@Log
public class AccountService {

    @Inject
    AccountRepository accountRepository;


    @Transactional
    public String addAccount(AddAccountRequest request) {
        log.info("Adding account: " + request.getName());

//        String accountId = UUID.randomUUID().toString();

        var newIban = "CH"+ UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 19);

        Account account = new Account(
                newIban,
                request.getName(),
                request.getBalance());

        accountRepository.persist(account);

        return account.getId();
    }

    @Transactional
    public void changeAccountBalance(String id, double amount) {
        var account = getAccountById(id);
        account.setBalance(account.getBalance() + amount);
    }

    @Transactional
    public Account getAccountById(String id) {
        return accountRepository.findByIdByNativeQuery(id);
    }

}
