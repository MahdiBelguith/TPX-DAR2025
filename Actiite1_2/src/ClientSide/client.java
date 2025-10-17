package ClientSide;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
	//initiation de l'address de serveur
    private static final String HOST = "127.0.0.1";
    //initiation de port
    private static final int PORT = 500;
    
    public static void main(String[] args) {
    	//socket pour la connexion client serveur
        Socket socket = null;
        //pour lire les données de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Connexion au serveur aver l'affuchage d'address et de numéro de port
            System.out.println("Tentative de connexion au serveur " + HOST + ":" + PORT);
            socket = new Socket(HOST, PORT);
            System.out.println("Connecté au serveur !");
            
            // Création des flux d'entrée/sortie
            PrintWriter out = new PrintWriter(
            	//socket.getOutputStream() pour récupère le flux de sortie du socket
                socket.getOutputStream(), true
            );
            //lire les données envoiyée d'apres le serveur 
            BufferedReader in = new BufferedReader(
            	//socket.getInputStream() pour convertit les octets reçus en caractères.
            	//InputStreamReader pour lire les données ligne par ligne
                new InputStreamReader(socket.getInputStream())
            );
            
            // Demande à l'utilisateur de saisir un nombre
            System.out.print("Entrez un nombre entier : ");
            int x = scanner.nextInt();
            
            // Envoi du nombre au serveur
            out.println(x);
            System.out.println("Nombre envoyé au serveur : " + x);
            
            // Réception du résultat
            String resultat = in.readLine();
            System.out.println("Résultat reçu du serveur : " + resultat);
            System.out.println("Le serveur a calculé : " + x + " * 5 = " + resultat);
            
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu : " + HOST);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                    System.out.println("Connexion fermée");
                }
                scanner.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }
}