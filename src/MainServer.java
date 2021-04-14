import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    static final int port = 2020;

    public void startAcceptingClients(){
        ServerSocket serverSocket;
        Socket newClient;

        try{
            serverSocket = new ServerSocket(port);
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }

        while(true){
            try {
                newClient = serverSocket.accept();
                new NumbersAdder(newClient).run();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
