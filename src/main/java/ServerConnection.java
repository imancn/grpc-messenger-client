import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ir.imancn.messenger.MessageServiceGrpc;

public class ServerConnection {

    private final String SERVER_IP;
    private final int SERVER_PORT;
    private static final Config config = ConfigFactory.load("configFile.conf");
    private ManagedChannel channel;

    public ServerConnection(){
        SERVER_IP = config.getString("server.ip");
        System.out.println(SERVER_IP);
        SERVER_PORT = config.getInt("server.port");
        System.out.println(SERVER_PORT);
        channel = ManagedChannelBuilder.forAddress(SERVER_IP, SERVER_PORT)
                .usePlaintext()
                .build();
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public MessageServiceGrpc.MessageServiceBlockingStub getStub(){
        return MessageServiceGrpc.newBlockingStub(channel);
    }
}
