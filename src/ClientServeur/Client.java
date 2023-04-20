package ClientServeur;

import java.io.IOException;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            //création du Socket
            DatagramSocket clientSocket = new DatagramSocket();

            //Envoie du message
            String message = "Bonjour Serveur!";
            byte[] bufferEnvoie = message.getBytes();
            InetAddress adresseServeur = InetAddress.getByName("127.0.0.1");
            int portServeur = 3000;
            DatagramPacket paquet = new DatagramPacket(bufferEnvoie, bufferEnvoie.length, adresseServeur, portServeur);
            System.out.println("Envoie du paquet: " + paquet);
            clientSocket.send(paquet);

            //Reception du message de retour du serveur
            byte[] bufferReception = new byte[1024];
            DatagramPacket paquetDepuisServeur = new DatagramPacket(bufferReception, bufferReception.length);
            clientSocket.receive(paquetDepuisServeur);
            System.out.println(new String(paquetDepuisServeur.getData(), 0, paquetDepuisServeur.getLength()) + paquet.getAddress() + ":" + paquet.getPort());

            clientSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
