package org.acme.grpc;

import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import org.acme.HelloGrpc;
import org.acme.HelloReply;
import org.acme.HelloRequest;

import java.util.logging.Logger;

@GrpcService
public class HelloGrpcService implements HelloGrpc {

    private final Logger LOG = Logger.getLogger(HelloGrpcService.class.getName());

    @Override
    @RolesAllowed("Admin")
    public Uni<HelloReply> sayHello(HelloRequest request) {
        LOG.warning("Received request to say hello: " + request);
        return Uni.createFrom().item("Hello " + request.getName() + "!")
                .map(msg -> HelloReply.newBuilder().setMessage(msg).build());
    }

}
