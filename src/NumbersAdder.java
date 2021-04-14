import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NumbersAdder implements Runnable{

    Socket client;

    BufferedWriter lineWriter;


    NumbersAdder(Socket newClient){
        client = newClient;
        try {
            lineWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void addNumbersAndSend(String numbersToAdd){
        try {
            lineWriter.write(numbersToAdd);
            lineWriter.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
