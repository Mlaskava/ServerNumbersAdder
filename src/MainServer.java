import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {



    static final int port = 2020;

    public void startAcceptingClients(){

            ExecutorService addingStarter = Executors.newCachedThreadPool();
            ServerSocket serverSocket;
            Socket newClient;

            try{
                serverSocket = new ServerSocket(port);
                while(true){

                    newClient = serverSocket.accept();
                    addingStarter.submit(new NumbersAdder(newClient));

                }
            }

            catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }



    }
    public static void main(String [] args){
        MainServer server = new MainServer();
        server.startAcceptingClients();
    }
}
