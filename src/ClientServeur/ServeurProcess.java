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
    private String pseudo;
    private static ArrayList<ServeurProcess> serveurProcesses = new ArrayList<>();

    public ServeurProcess(Socket serveurSocket) {
        this.serveurSocket = serveurSocket;
        try {
            out = new PrintWriter(serveurSocket.getOutputStream());
            in = new Scanner(serveurSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serveurProcesses.add(this);
    }

    @Override
    public void run() {

        newPseudo();
        sendSystemMessageAll(pseudo+" à rejoint la conversation");

        String message="";
        try {
            if(in.hasNextLine()) {
                message = in.nextLine();
            }
            while (!message.equals("quit")) {
                if(message.equals("pseudo"))
                {
                    String oldPseudo=pseudo;
                    newPseudo();
                    sendSystemMessageAll(oldPseudo+" à changer de pseudo pour "+pseudo);
                }
                else
                {
                    sendMessageAll(message);
                }
                if (in.hasNextLine()) message = in.nextLine();
            }

            serveurSocket.close();
            serveurProcesses.remove(this);
            sendSystemMessageAll(pseudo+" à quitté la conversation");
            if(serveurProcesses.size()==1)
            {
                sendSystemMessageAll("Tu es seul sur le serveur");
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

    public void newPseudo()
    {
        out.println("Entrez un pseudo");
        out.flush();

        if(in.hasNextLine()) {
            pseudo = in.nextLine();
            out.println("Votre pseudo est "+pseudo);
            out.flush();
        }
    }

    public void sendMessage(String message,String pseudoSender) {
        out.println(pseudoSender+" - "+message);
        out.flush();
    }

    public void sendMessageAll(String message) {
        for (ServeurProcess serveurProcess : serveurProcesses) {
            if (serveurProcess != this) serveurProcess.sendMessage(message,pseudo);
        }
    }

    public void sendSystemMessageAll(String message) {
        for (ServeurProcess serveurProcess : serveurProcesses) {
            if (serveurProcess != this) serveurProcess.sendMessage(message,"Système");
        }
    }

}
