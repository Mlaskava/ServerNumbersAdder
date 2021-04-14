import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String [] args){

        MainServer server = new MainServer();
        Client client = new Client();


        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            server.startAcceptingClients();
        });

        client.connectToServer();

        client.sendLine("TEST\r\n");
        System.out.println(client.getLine());


    }
}
