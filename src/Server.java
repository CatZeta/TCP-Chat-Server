import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber = 8090;
        InetAddress hostName = InetAddress.getByName("localhost");

        ServerSocket serverSocket = new ServerSocket(portNumber);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        while (true) {

            Socket clientSocket = serverSocket.accept();

            executorService.submit(new ClientHandler(clientSocket));

        }
    }

    public static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String message;

                while ((message = in.readLine()) != null) {
                    System.out.println("Read: " + message);
                    out.write(message);

                    if (message.equals("exit")) {
                        System.out.println("closing connection with client");
                        break;
                    }
                }
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
