package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.*;
import org.acme.model.Transaction;
import org.acme.repository.AccountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TransactionService {

    @Inject
    AccountService accountService;

    @Inject
    AccountRepository accountRepository;

    HashMap<String, Transaction> transactions = new HashMap<>();

    public AddTransactionReply addTransactionFromRequest(AddTransactionRequest request) {
        var id = UUID.randomUUID().toString();
        var sender = accountRepository.findByIdByNativeQuery(request.getSender());
        var receiver = accountRepository.findByIdByNativeQuery(request.getReceiver());
        var transaction = new Transaction(id, sender, receiver, request.getAmount());

        // Update the account balances
        accountService.changeAccountBalance(request.getSender(), -request.getAmount());
        accountService.changeAccountBalance(request.getReceiver(), request.getAmount());

        transactions.put(id, transaction);
        var reply = AddTransactionReply.newBuilder()
                .setId(id)
                .build();

        return reply;
    }

    public GetTransactionReply getTransactionFromRequest(GetTransactionRequest request) {
        var transaction = transactions.get(request.getId());

        var reply = GetTransactionReply.newBuilder()
                .setSender(transaction.getSender().getId())
                .setReceiver(transaction.getReceiver().getId())
                .setAmount(transaction.getAmount())
                .build();

        return reply;
    }



    public List<Transaction> getTransactionsByIban(String iban) {
//        var transactions = accountRepository.findTransactionsByAccountIban(iban);
//        return transactions;
        return null;
    }



    public GetAllTransactionReply getAllTransactionFromRequest() {
        List<GetTransactionReply> transactionList = new ArrayList<>();

        for (var transaction : transactions.values()) {
            var transactionReply = GetTransactionReply.newBuilder()
                    .setSender(transaction.getSender().getId())
                    .setReceiver(transaction.getReceiver().getId())
                    .setAmount(transaction.getAmount())
                    .build();
            transactionList.add(transactionReply);
        }

        var reply = GetAllTransactionReply.newBuilder()
                .addAllTransactions(transactionList)
                .build();

        return reply;
    }
}
