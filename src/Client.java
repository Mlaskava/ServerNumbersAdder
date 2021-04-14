import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    static final int port = 2020;
    static final String IP = "127.0.0.1";
    Socket socket;
    BufferedWriter lineWriter;
    BufferedReader lineReader;

    void connectToServer(){
        try{
            socket = new Socket(IP, port);
            lineWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            lineReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    void sendLine(String line){

        try {
            lineWriter.write(line);
            lineWriter.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    String getLine(){
        try {
            String line = lineReader.readLine();
            return line;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
