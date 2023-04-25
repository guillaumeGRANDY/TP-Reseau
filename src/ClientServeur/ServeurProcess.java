package ClientServeur;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServeurProcess implements Runnable {

    private final DatagramSocket socket;
    private final DatagramPacket receivePacket;
    private final String clientAddress;
    private final int clientPort;

    public ServeurProcess(DatagramSocket socket, DatagramPacket receivePacket, String clientAddress, int clientPort) {
        this.socket = socket;
        this.receivePacket = receivePacket;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
    }

    @Override
    public void run() {
        try {
            // Récupération du message envoyé par le client
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Affichage du message
            System.out.println("Message reçu de " + clientAddress + ":" + clientPort + " : " + message);

            // Envoi d'une réponse au client
            String response = "Je t'ai entendu";
            DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.length(), InetAddress.getByName(clientAddress), clientPort);
            socket.send(sendPacket);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de la réponse au client: " + e.getMessage());
        }
    }
}
