package org.acme.grpc;

import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.acme.*;
import org.acme.service.AccountService;

@GrpcService
public class AccountGrpcService implements AccountGrpc {

//    HashMap<String, Account> accounts = new HashMap<>();

    @Inject
    AccountService accountService;

    @Override
    @Blocking
    public Uni<AddAccountReply> addAccount(AddAccountRequest request) {

        String id = accountService.addAccount(request);

        var response = AddAccountReply
                        .newBuilder()
                        .setId(id)
                        .build();

        return Uni.createFrom().item(response);
    }

    @Override
    @Blocking
    public Uni<GetAccountReply> getAccount(GetAccountRequest request) {
        var account = accountService.getAccountById(request.getId());

        var response = GetAccountReply
                .newBuilder()
                .setId(account.getId())
                .setIban(account.getIban())
                .setName(account.getName())
                .setBalance(account.getBalance())
//                .setSentTransactions(account.getSentTransactions())
//                .setReceivedTransactions(account.getReceivedTransactions())
                .build();

        return Uni.createFrom().item(response);
    }
}
