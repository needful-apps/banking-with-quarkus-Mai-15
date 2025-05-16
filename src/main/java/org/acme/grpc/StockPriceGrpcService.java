package org.acme.grpc;


import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import org.acme.StockGrpc;
import org.acme.StockReply;
import org.acme.StockRequest;

import java.time.Duration;

@GrpcService
public class StockPriceGrpcService implements StockGrpc {
    @Override
    public Multi<StockReply> sayStock(StockRequest request) {
        var reply = StockReply.newBuilder()
                .setPrice(14.33)
                .setSymbol(request.getName())
                .setTimestamp(System.currentTimeMillis())
                .build();

        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(2))
                .onItem()
                .transformToMulti(tick -> Multi.createFrom()
                        .item(reply))
                .concatenate();
    }
}
