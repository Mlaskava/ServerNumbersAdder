import java.io.InputStreamReader;
import java.net.Socket;

public class NumbersAdder implements Runnable{

    Socket client;

    int offset = 0;

    NumbersAdder(Socket newClient){
        client = newClient;
    }

    void addNumbersAndSend(String numbersToAdd){

    }


    @Override
    public void run() {

        StringBuilder builder = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
            while(true){
                builder.append((char) inputStreamReader.read());

                if(builder.length() != 0 && builder.charAt(builder.length() - 1) == '\n'){
                    addNumbersAndSend(builder.toString());
                    builder.setLength(0);
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
