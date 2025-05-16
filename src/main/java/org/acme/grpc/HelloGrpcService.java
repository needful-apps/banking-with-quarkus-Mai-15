package org.acme.grpc;

import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import org.acme.HelloGrpc;
import org.acme.HelloReply;
import org.acme.HelloRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.logging.Logger;

@GrpcService
public class HelloGrpcService implements HelloGrpc {

    private final Logger LOG = Logger.getLogger(HelloGrpcService.class.getName());

    @ConfigProperty(name = "banking.hello.welcome", defaultValue = "No value found")
    String welcomeMessage;

    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        LOG.warning("Received request to say hello: " + request);
        return Uni.createFrom().item(welcomeMessage+ " Hello " + request.getName() + "!")
                .map(msg -> HelloReply.newBuilder().setMessage(msg).build());
    }

}
