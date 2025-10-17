package ServerSide;
import java.io.*;
import java.net.*;

public class server {
	//initialisation de port
    private static final int PORT = 500;
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        
        try {
            // Création du ServerSocket sur le port 500
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré sur le port " + PORT);
            System.out.println("En attente de connexion client...");
            
            // Attente de connexion d'un client
            clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket.getInetAddress());
            
            // Création des flux d'entrée/sortie
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );
            PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true
            );
            
            // Réception du nombre envoyé par le client
            String nombreRecu = in.readLine();
            System.out.println("Nombre reçu du client : " + nombreRecu);
            
            // Conversion et calcul (multiplication par 5)
            int x = Integer.parseInt(nombreRecu);
            int resultat = x * 5;
            System.out.println("Calcul effectué : " + x + " * 5 = " + resultat);
            
            // Envoi du résultat au client
            out.println(resultat);
            System.out.println("Résultat envoyé au client : " + resultat);
            
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erreur : format de nombre invalide");
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                    System.out.println("Connexion client fermée");
                }
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                    System.out.println("Serveur arrêté");
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }
}