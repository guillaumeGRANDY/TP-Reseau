package ClientServeur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Serveur {

    public static void main(String[] args) {
        try {
            //création du Socket
            ServerSocket listener = new ServerSocket(3000);
            ExecutorService pool = newFixedThreadPool(20);
            while (true) {
                Socket newClient = listener.accept();
                pool.execute(new ServeurProcess(newClient));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
