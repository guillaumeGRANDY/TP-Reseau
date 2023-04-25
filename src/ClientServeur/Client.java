package ClientServeur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Création du socket UDP du client
            DatagramSocket clientSocket = new DatagramSocket();

            // Adresse du serveur
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // Plage de ports réduite du serveur
            int minServerPort = 8000;
            int maxServerPort = 8010;

            // Test des ports de la plage de ports réduite du serveur
            for (int port = minServerPort; port <= maxServerPort; port++) {
                try {
                    // Boucle d'envoi de messages
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        // Lecture du message à envoyer
                        System.out.print("Entrez un message à envoyer au serveur : ");
                        String message = scanner.nextLine();

                        // Envoi du message au serveur
                        byte[] sendData = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
                        clientSocket.send(sendPacket);

                        // Réception de la réponse du serveur
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        clientSocket.receive(receivePacket);
                        String replyMessage = new String(receivePacket.getData()).trim();
                        System.out.println("Réponse du serveur : " + replyMessage);

                        // Vérification si l'utilisateur veut continuer à envoyer des messages
                        System.out.print("Voulez-vous envoyer un autre message ? (oui/non) : ");
                        String response = scanner.nextLine().toLowerCase();
                        if (!response.equals("oui")) {
                            break;
                        }
                    }

                    // Fermeture du socket
                    clientSocket.close();
                    return; // Sortie de la boucle des ports si la connexion réussie
                } catch (Exception e) {
                    System.out.println("Impossible de se connecter au serveur sur le port " + port + ", en essayant le suivant...");
                }
            }

            System.err.println("Impossible de se connecter au serveur sur la plage de ports spécifiée.");
            // Fermeture du socket
            clientSocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
