package ClientServeur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            //création du Socket
            Socket clientSocket = new Socket("127.0.0.1", 3000);

            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            //Envoie du message
            out.println("message 1 du client");
            out.flush();

            //Lit le message
            if (in.hasNext()) {
                System.out.println(in.nextLine());
            }

            clientSocket.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
