package ClientServeur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Serveur {

    public static void main(String[] args) {
        try {
            //création du Socket
            DatagramSocket serveurSocket = new DatagramSocket(3000);

            //Reception du message
            byte[] buffer = new byte[1024];
            DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
            serveurSocket.receive(paquet);
            System.out.println("Nouveau client " + paquet.getAddress() + ":" + paquet.getPort());
            System.out.println("Message envoyé côté client: " + new String(paquet.getData(), 0, paquet.getLength()));

            // Ré-envoie du message du client depuis le serveur vers client
            buffer = new String(paquet.getData(), 0, paquet.getLength()).getBytes();
            DatagramPacket serveurPaquet = new DatagramPacket(buffer, buffer.length, paquet.getAddress(), paquet.getPort());
            serveurSocket.send(serveurPaquet);

            //fermeture du socket
            serveurSocket.close();

        } catch (SocketException e) {
            System.out.println("Erreur Socket " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur Receive");
            throw new RuntimeException(e);
        }
    }
}
