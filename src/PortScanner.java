import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.InetSocketAddress;

public class PortScanner {
    public static void main(String[] args) {
        String ip = "127.0.0.1"; // adresse IP à scanner
        int startPort = 1; // premier port à scanner
        int endPort = 1024; // dernier port à scanner

        for (int port = startPort; port <= endPort; port++) {
            try {
                DatagramSocket socket = new DatagramSocket(null);
                socket.setReuseAddress(true);
                socket.bind(new InetSocketAddress(ip, port));
                System.out.println("Port " + port + " ouvert");
                socket.close();
            } catch (SocketException ex) {
                System.out.println("Port " + port + " fermé");
            }
        }
    }
}