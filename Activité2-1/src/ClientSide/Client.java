package ClientSide;
import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String host = "localhost"; // adresse du serveur
        int port = 1234;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecté au serveur " + host + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("➡️ Entrez une opération (ex: 55 * 25): ");
            String operation = console.readLine();


            // validation syntaxique
            if (!operation.matches("\\d+\\s*[+\\-*/]\\s*\\d+")) {
                System.out.println("❌ Erreur : syntaxe invalide !");
                return;
            }

            // envoyer au serveur
            out.println(operation);

            // recevoir le résultat
            String response = in.readLine();
            System.out.println(" Réponse du serveur : " + response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
