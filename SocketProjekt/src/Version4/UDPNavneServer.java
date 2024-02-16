package Version4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class UDPNavneServer {
    public static void main(String args[]) throws Exception {

        HashMap hashMap = new HashMap();
        hashMap.put("thor", "10.10.137.228");
        hashMap.put("steffen", "10.10.139.173");
        hashMap.put("rasmus", "10.10.131.151");

        DatagramSocket navneSocket = new DatagramSocket(6789);
        byte[] receiveData = new byte[1024]; // Buffer to store incoming data.
        byte[] sendData = new byte[1024]; // Buffer to store outgoing data.

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            navneSocket.receive(receivePacket);
            System.out.println("Packet received");

            String navnFraClient = new String(receiveData, 0, receivePacket.getLength());

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            String valueFraMap = (String) hashMap.get(navnFraClient) + "\n";

            sendData = valueFraMap.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            navneSocket.send(sendPacket);
            System.out.println("Packet sent");
        }
    }
}
