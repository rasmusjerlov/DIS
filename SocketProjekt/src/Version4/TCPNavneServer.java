package Version4;

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
        hashMap.put("thor", "10.10.138.146");
        hashMap.put("steffen", "10.10.139.173");
        hashMap.put("rasmus", "10.10.131.51");

        // connection stuff
        ServerSocket navneSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = navneSocket.accept();
            (new TCPNavneServerThread(connectionSocket, hashMap)).start();
        }
    }
}
