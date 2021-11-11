import io.grpc.ManagedChannel;
import ir.imancn.messenger.*;

import java.util.Date;

public class Main {
    public static void main(String[] args){
        ManagedChannel channel = new ServerConnection().getChannel();

        MessageServiceGrpc.MessageServiceBlockingStub stub = MessageServiceGrpc.newBlockingStub(channel);

        System.out.println("Test");
        MessageResponse response = stub.send(MessageInfo.newBuilder()
                .setFromId("User1")
                .setToId("User2")
                .setBody("Hi User2!")
                .setDate(new Date().toString())
                .build());
        System.out.println("res1 : ");
        System.out.println(response);

         response = stub.send(MessageInfo.newBuilder()
                .setFromId("User2")
                .setToId("User1")
                .setBody("Hiii!")
                .setDate(new Date().toString())
                .build());
        System.out.println(response);

         response = stub.send(MessageInfo.newBuilder()
                .setFromId("User2")
                .setToId("User3")
                .setBody("Hi User3 :)")
                .setDate(new Date().toString())
                .build());
        System.out.println(response);

        Messages messages = stub.getMessages(Chat.newBuilder()
                .setFromId("User1")
                .setToId("User2")
                .build());
        System.out.println("The messages that User1 sent to User2 : " + messages + "\n");

        messages = stub.getMessages(Chat.newBuilder()
                .setFromId("User2")
                .setToId("User1")
                .build());
        System.out.println("The messages that User2 sent to User1 : " + messages + "\n");

        Users users = stub.openChats(UserInfo.newBuilder()
                .setUserId("User2")
                .build());

        System.out.print("User2 has open chat with ");
        for (int i = 0; i < users.getUserCount(); i++) {
            System.out.print(users.getUser(i) + " ");
            if(i + 1 == users.getUserCount())
                System.out.println("\n");
            else
                System.out.print("and ");
        }


        channel.shutdown();
    }
}
