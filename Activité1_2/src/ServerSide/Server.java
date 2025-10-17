package ServerSide;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 1234; // port d’écoute du serveur
        System.out.println(" Serveur en attente de connexion sur le port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // attendre la connexion d’un client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket.getInetAddress());

            // flux d’entrée/sortie
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // lire la chaîne envoyée par le client
            String operation = in.readLine();
            System.out.println("📩 Opération reçue : " + operation);

            // calculer le résultat
            String result = compute(operation);

            // envoyer le résultat au client
            out.println(result);
            System.out.println("Résultat envoyé : " + result);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // fonction qui calcule l’opération
    private static String compute(String operation) {
        try {
            operation = operation.replaceAll("\\s+", "");
            char operator = ' ';
            if (operation.contains("+")) operator = '+';
            else if (operation.contains("-")) operator = '-';
            else if (operation.contains("*")) operator = '*';
            else if (operation.contains("/")) operator = '/';
            else return "❌ Opération invalide";

            String[] parts = operation.split("\\" + operator);
            if (parts.length != 2) return " Syntaxe incorrecte";

            double op1 = Double.parseDouble(parts[0]);
            double op2 = Double.parseDouble(parts[1]);
            double result = 0;

            switch (operator) {
                case '+': result = op1 + op2; break;
                case '-': result = op1 - op2; break;
                case '*': result = op1 * op2; break;
                case '/': 
                    if (op2 == 0) return "Division par zéro";
                    result = op1 / op2; 
                    break;
            }
            return "Résultat = " + result;
        } catch (Exception e) {
            return "❌ Erreur : " + e.getMessage();
        }
    }
}
