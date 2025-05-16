package org.acme.client;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import lombok.extern.java.Log;
import org.acme.*;

@Log
@ApplicationScoped
public class StockClient {
    @GrpcClient("stock")
    StockGrpc stockGrpcClient;


//    @Scheduled(every = "10s")
    public void getStockPrices(){
        var request = StockRequest
                .newBuilder()
                        .setName("NKE")
                                .build();
        var result = stockGrpcClient.sayStock(request);

        result.subscribe().with(item -> {
            log.info("Received stock price: " + item.getPrice());
        }, failure -> {
            log.severe("Failed to get stock price: " + failure.getMessage());
        });
    }
}
