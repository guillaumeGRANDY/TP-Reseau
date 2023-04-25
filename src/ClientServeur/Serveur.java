package ClientServeur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Serveur{
    public static void main(String[] args) {
        try {
            // Création du socket UDP du serveur
            DatagramSocket serverSocket = new DatagramSocket(9876);

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            System.out.println("Serveur démarré.");

            while (true) {
                // Réception du paquet du client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // Conversion du message reçu en une chaîne de caractères
                String message = new String(receivePacket.getData()).trim();

                // Affichage du message reçu
                System.out.println("Message reçu du client : " + message);

                // Réponse du serveur
                String replyMessage = "Je reçois ton message";
                sendData = replyMessage.getBytes();

                // Récupération de l'adresse IP et du port du client
                InetAddress clientIPAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Envoi de la réponse au client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, clientPort);
                serverSocket.send(sendPacket);

                // Réinitialisation du tableau de données pour la prochaine réception
                receiveData = new byte[1024];
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
