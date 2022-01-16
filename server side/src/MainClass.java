import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClass {

    public static void main(String[] args) throws IOException {
        initializeServer();
    }

    private static void initializeServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5005); //server socket created
        while (true){                          //this while for make server always listening for any request
            Socket s = serverSocket.accept();   //server socket received request and give it to internal socket
            new ChatHandler(s);     //created object of chat handler and pass to it the internal socket
        }
    }
}
