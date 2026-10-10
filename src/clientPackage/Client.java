package clientPackage;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // La première étape :
            System.out.println("Je suis un client pas encore connecté...");
            
            // Connexion au serveur
            Socket socket = new Socket("127.0.0.1", 1234);
            
            System.out.println("je suis un client connecté");
            
            // Préparation des flux et scanner
            Scanner scanner = new Scanner(System.in);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            
            while (true) {
                // a) Le Client est demandé de taper un entier x au clavier.
                System.out.print("Veuillez taper un entier x (0 pour quitter) : ");
                int x = scanner.nextInt();
                
                // b) Le client envoie cet entier au serveur.
                dos.writeInt(x);
                
                if (x == 0) {
                    break;
                }
                
                // f) Le client reçoit le résultat.
                int result = dis.readInt();
                System.out.println("Le résultat reçu du serveur est : " + result);
            }
            
            // La dernière étape : Fermer socket
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
