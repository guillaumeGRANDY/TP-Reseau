package ClientServeur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Client {

    // Fonction pour fermer la connexion et arrêter les threads

    public static void main(String[] args) {
        try {
            //création du Socket
            Socket clientSocket = new Socket("127.0.0.1", 3000);

            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            // Thread pour envoyer des messages
            Thread sendMessageThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (!clientSocket.isClosed()) {
                    String message = scanner.nextLine();
                    out.println(message);
                    out.flush();
                    if (message.equals("quit")) {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            // Thread pour lire des messages
            Thread readMessageThread = new Thread(() -> {
                while (!clientSocket.isClosed()) {
                    if (in.hasNext()) {
                        System.out.println(in.nextLine());
                        System.out.flush();
                    }
                }
            });

            // Lancement des threads
            sendMessageThread.start();
            readMessageThread.start();

            // Attente de la fin des threads
            sendMessageThread.join();
            readMessageThread.join();

            in.close();
            out.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
