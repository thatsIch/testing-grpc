package de.thatsich.grpc.chat;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created by thatsIch on 03.04.2017.
 */
public class ChatServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting chat server on port 8080");
        Server server = ServerBuilder.forPort(8080)
                .addService(new ChatService())
                .build();

        server.start();
        System.out.println("Started chat server on port 8080");
        server.awaitTermination();
    }

}
