import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class PortScanner {
    public static void main(String[] args) {
        Map<Integer, String> result = scanUdpPorts(1, 5400);
        for (Map.Entry<Integer, String> entry : result.entrySet()) {
            System.out.println("Port " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public static Map<Integer, String> scanUdpPorts(int startPort, int endPort) {
        Map<Integer, String> resultMap = new HashMap<>();
        for (int port = startPort; port <= endPort; port++) {
            try {
                DatagramSocket socket = new DatagramSocket(port);
                socket.close();
                resultMap.put(port, "Fermé");
            } catch (SocketException e) {
                resultMap.put(port, "Ouvert");
            }
        }
        return resultMap;
    }
}