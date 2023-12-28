import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        int portNumber = 8090;
        InetAddress hostName = InetAddress.getByName("localhost");

        Socket clientSocket = new Socket(hostName, portNumber);


        // String message;

        while (true) {

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            BufferedReader keyInput = new BufferedReader(new InputStreamReader(System.in));

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message = keyInput.readLine();

            out.println(message);
            out.flush();

            //System.out.println("Message: " + message);
            //System.out.println(in.readLine());


            if (message.equals("exit")) {

                System.out.println("exiting");
                out.close();
               // in.close();
                clientSocket.close();
                break;

            }

            // System.out.println("Message: ");

        }

    }

}
