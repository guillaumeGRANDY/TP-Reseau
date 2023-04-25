package ClientServeur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServeurProcess implements Runnable {
    private final Socket serveurSocket;
    private ArrayList<ServeurProcess> serveurProcessTable;
    public ServeurProcess(Socket serveurSocket, ArrayList<ServeurProcess> serveurProcessTable) {
        this.serveurSocket = serveurSocket;
        this.serveurProcessTable=serveurProcessTable;
    }

    public ServeurProcess(Socket serveurSocket) {
        this.serveurSocket = serveurSocket;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(serveurSocket.getInputStream());
            PrintWriter out = new PrintWriter(serveurSocket.getOutputStream());

            System.out.println(in.nextLine());

            out.println("Je t'ai entendu");
            out.flush();

            serveurSocket.close();
        } catch (SocketException e) {
            System.out.println("Erreur Socket " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Erreur Receive");
            throw new RuntimeException(e);
        }
    }
}
