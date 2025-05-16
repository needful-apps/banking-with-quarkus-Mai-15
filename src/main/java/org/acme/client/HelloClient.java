package org.acme.client;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.java.Log;
import org.acme.HelloGrpc;
import org.acme.HelloRequest;
import org.acme.StockGrpc;
import org.acme.StockRequest;

@Log
@ApplicationScoped
public class HelloClient {

    @GrpcClient("hello")
    HelloGrpc helloGrpcClient;


    @Scheduled(every = "10s")
    public void getMessage(){
        var request = HelloRequest.newBuilder().setName("Quarkus").build();
        var result = helloGrpcClient.sayHello(request).await().indefinitely();

        log.info(result.getMessage());
    }
}
