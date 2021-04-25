import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class NumbersAdder implements Runnable {

    Socket client;
    String[] numbers;
    BufferedWriter lineWriter;
    InputStreamReader reader;

    NumbersAdder(Socket newClient) {
        client = newClient;
        try {
            lineWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.US_ASCII));
            reader = new InputStreamReader(client.getInputStream(), StandardCharsets.US_ASCII);
            client.setSoTimeout(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean checkInputCorrectness(String input) {
        boolean spaceInLastIndex = false;
        if (input.length() == 0) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != ' ' && input.charAt(i) != '\r'
                    && input.charAt(i) != '\n' && !Character.isDigit(input.charAt(i))) {
                return false;
            } else if (input.charAt(i) == ' ') {
                if (i == 0 || i == input.length() - 1) {
                    return false;
                } else {
                    if (spaceInLastIndex) {
                        return false;
                    } else {
                        spaceInLastIndex = true;
                    }

                }
            } else {
                spaceInLastIndex = false;
            }
        }
        return true;
    }

    void addNumbersAndSend(String input) throws Exception {
        boolean error;
        int sum = 0;
        int number = 0;
        String message;
        numbers = input.split(" ");

        error = !checkInputCorrectness(input);



        for (String s : numbers) {
            try {

                number = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                error = true;
            }
            if(sum < Integer.MAX_VALUE - number){
                sum += number;
            }
            else{
                error = true;
            }
        }

        if (error) {
            message = "ERROR\r\n";
        } else {
            message = sum + "\r\n";
        }
        lineWriter.write(message);
        lineWriter.flush();

    }

    void closeConnection(){
        try{
            reader.close();
            lineWriter.close();
            client.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        StringBuilder builder = new StringBuilder();

        try {

            while (true) {

                builder.append((char) reader.read());
                if (builder.length() > 1 && builder.charAt(builder.length() - 2) == '\r' && builder.charAt(builder.length() - 1) == '\n') {
                    addNumbersAndSend(builder.substring(0, builder.length() - 2));
                    builder.setLength(0);
                }

            }

        }
        catch (SocketTimeoutException e){
            closeConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
