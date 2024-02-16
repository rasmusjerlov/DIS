package Version3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket connSocket;

    public ServerThread(Socket connSocket) {
        this.connSocket = connSocket;
    }

    @Override
    public void run() {
        try {
            // Setup
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());
            BufferedReader inputFromServerUser = new BufferedReader(new InputStreamReader(System.in));

            // Incoming msg
            String receivedMessage = inFromClient.readLine() + "\r";
            System.out.println(receivedMessage);

            // Outwards msg
            String messageToSend = inputFromServerUser.readLine() + "\n";
            outToClient.writeBytes(messageToSend);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
