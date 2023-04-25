package ClientServeur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServeurProcess implements Runnable {
    private final Socket serveurSocket;
    private PrintWriter out;
    private Scanner in;
    private static ArrayList<ServeurProcess> serveurProcesses = new ArrayList<>();

    public ServeurProcess(Socket serveurSocket, ArrayList<ServeurProcess> serveurProcessesTable) {
        this.serveurSocket = serveurSocket;
        this.serveurProcesses = serveurProcessesTable;
        this.serveurProcesses.add(this);
        try {
            out = new PrintWriter(serveurSocket.getOutputStream());
            in = new Scanner(serveurSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServeurProcess(Socket serveurSocket) {
        this.serveurSocket = serveurSocket;
        try {
            out = new PrintWriter(serveurSocket.getOutputStream());
            in = new Scanner(serveurSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serveurProcesses.add(this);
        sendMessageAll("Serveur - Quelqu'un à rejoint la conversation");
    }

    @Override
    public void run() {
        String message="";
        try {
            if(in.hasNextLine()) {
                message = in.nextLine();
            }
            while (!message.equals("quit")) {
                sendMessageAll(message);
                if(in.hasNextLine()) message = in.nextLine();
            }

            serveurSocket.close();
            serveurProcesses.remove(this);
            sendMessageAll("Serveur - Quelqu'un à quitté la conversation");
            if(serveurProcesses.size()==1)
            {
                sendMessageAll("Serveur - Tu es seul sur le serveur");
            }

            in.close();
            out.close();
        } catch (SocketException e) {
            System.out.println("Erreur Socket " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Erreur Receive");
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    public void sendMessageAll(String message) {
        for (ServeurProcess serveurProcess : serveurProcesses) {
            if (serveurProcess != this) serveurProcess.sendMessage(message);
        }
    }
}
