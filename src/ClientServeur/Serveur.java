package ClientServeur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.net.*;

public class Serveur {
    public static void main(String[] args) {
        try {
            // Création du socket UDP du serveur
            DatagramSocket serverSocket = new DatagramSocket(null);

            // Allouer la plage de ports réduite
            int minPort = 8000;
            int maxPort = 8010;
            for (int port = minPort; port <= maxPort; port++) {
                try {
                    serverSocket.bind(new InetSocketAddress(port));
                    break;
                } catch (Exception e) {
                    System.out.println("Impossible de lier le port " + port + ", en essayant le suivant...");
                }
            }

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            System.out.println("Serveur démarré sur le port " + serverSocket.getLocalPort());

            while (true) {
                // Réception du paquet du client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                // Conversion du message reçu en une chaîne de caractères
                String message = new String(receivePacket.getData()).trim();

                // Affichage du message reçu
                System.out.println("Message reçu du client " + receivePacket.getAddress() + ":" + receivePacket.getPort() + " : " + message);

                // Réponse du serveur
                String replyMessage = "Je reçois ton message";
                sendData = replyMessage.getBytes();

                // Envoi de la réponse au client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);

                // Réinitialisation du tableau de données pour la prochaine réception
                receiveData = new byte[1024];
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
