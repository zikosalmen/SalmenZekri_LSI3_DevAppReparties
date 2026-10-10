package serverPackage;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        try {
            // La première étape :
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente la connexion d'un client ");
            
            Socket socket = serverSocket.accept();
            
            // La deuxième étape :
            System.out.println("un client est connecté");
            
            // La dernière étape : Fermer socket
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
