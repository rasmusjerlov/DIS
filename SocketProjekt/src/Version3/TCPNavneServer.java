package Version3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class TCPNavneServer {
    public static void main(String[] args) throws IOException {

        HashMap hashMap = new HashMap();
        hashMap.put("thor", "10.10.137.228");
        hashMap.put("steffen", "10.10.139.173");
        hashMap.put("rasmus", "10.10.131.147");

        // connection stuff
        ServerSocket navneSocket = new ServerSocket(6789);
        System.out.println("Serveren venter på klient");
        Socket connectionSocket = navneSocket.accept();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        System.out.println("Klient forbundet til navneserver");
        // ---

        System.out.println("Venter på navn fra klient");
        String navnFraClient = inFromClient.readLine().toLowerCase();

        outToClient.writeBytes((String) hashMap.get(navnFraClient) + "\n");
    }
}
