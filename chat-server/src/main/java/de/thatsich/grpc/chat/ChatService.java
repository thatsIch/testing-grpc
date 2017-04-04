package de.thatsich.grpc.chat;

import io.grpc.stub.StreamObserver;

import java.util.LinkedHashSet;
import java.util.Set;

public class ChatService extends ChatServiceGrpc.ChatServiceImplBase {
    private static Set<StreamObserver<Chat.ChatMessageFromServer>> observers = new LinkedHashSet<>();

    @Override
    public StreamObserver<Chat.ChatMessage> chat(final StreamObserver<Chat.ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<Chat.ChatMessage>() {
            public void onNext(Chat.ChatMessage chatMessage) {
                for (StreamObserver<Chat.ChatMessageFromServer> observer : observers) {
                    observer.onNext(Chat.ChatMessageFromServer.newBuilder()
                    .setName(chatMessage.getName())
                    .setMessage(chatMessage.getMessage())
                    .build());
                }
            }

            public void onError(Throwable t) {
                observers.remove(responseObserver);
            }

            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
        // return super.chat(responseObserver);
    }
}