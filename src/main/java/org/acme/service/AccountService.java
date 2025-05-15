package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Account;
import org.acme.AddAccountRequest;

import java.util.HashMap;
import java.util.UUID;

@ApplicationScoped
public class AccountService {
    HashMap<String, Account> accounts = new HashMap<>();

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public String addAccount(AddAccountRequest request) {
        String accountId = UUID.randomUUID().toString();

        var newIban = "CH"+ UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 19);

        Account account = new Account(
                accountId,
                newIban,
                request.getName(),
                request.getBalance());

        accounts.put(accountId, account);

        return accountId;
    }

    public void changeAccountBalance(String id, double amount) {
        var account = accounts.get(id);
        account.setBalance(account.getBalance() + amount);
    }

    public Account getAccountById(String id) {
        return accounts.get(id);
    }

}
