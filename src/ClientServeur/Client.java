package ClientServeur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Création du socket UDP
            DatagramSocket clientSocket = new DatagramSocket();

            // Définition de l'adresse IP et du port du serveur
            InetAddress serverIPAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;

            // Boucle d'envoi de messages
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Lecture du message à envoyer
                System.out.print("Entrez un message à envoyer au serveur : ");
                String message = scanner.nextLine();

                // Envoi du message au serveur
                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverPort);
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
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
