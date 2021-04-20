import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NumbersAdder implements Runnable{

    Socket client;
    String [] numbers;
    BufferedWriter lineWriter;
    InputStreamReader reader;

    NumbersAdder(Socket newClient){
        client = newClient;
        try {
            lineWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            reader = new InputStreamReader(client.getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    boolean checkInputCorrectness(String input){
        boolean spaceInLastIndex = false;
        if(input.length() == 0){
            return false;
        }
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) != ' ' && input.charAt(i) != '\r'
                    && input.charAt(i) != '\n' && !Character.isDigit( input.charAt(i) ) ){
                return false;
            }
            else if(input.charAt(i) == ' '){
                if(i == 0 || i == input.length() - 1){
                    return false;
                }
                else{
                    if(spaceInLastIndex){
                        return false;
                    }
                    else {
                        spaceInLastIndex = true;
                    }

                }
            }
            else{
                spaceInLastIndex = false;
            }
        }
        return true;
    }

    void addNumbersAndSend(String input) throws Exception{
            if(!checkInputCorrectness(input)){
                    lineWriter.write("ERROR\r\n");
                    lineWriter.flush();
                    return;
            }

        int sum = 0;
        numbers = input.split(" ");

        for(String s : numbers){

            sum += Integer.parseInt(s);
        }
            lineWriter.write(sum + "\r\n");
            lineWriter.flush();

    }

    @Override
    public void run() {

        StringBuilder builder = new StringBuilder();

        try {

            while(true){

                builder.append((char) reader.read());
                if(builder.length() != 0 && builder.charAt(builder.length() - 1) == '\n'){
                    addNumbersAndSend(builder.substring(0, builder.length() - 2));
                    builder.setLength(0);
                }

            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
