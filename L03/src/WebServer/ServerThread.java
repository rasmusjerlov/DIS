package WebServer;
import jdk.jfr.ContentType;

import java.net.*;
import java.io.*;
public class ServerThread extends Thread {
	Socket connSocket;

	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
	}

	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());

			// Do the work and the communication with the client here	
			// The following two lines are only an example
			String clientSentence = inFromClient.readLine();

			String[] stringArray = null;
			stringArray = clientSentence.split("\\s+");
//			outToClient.writeBytes("ecco " + clientSentence + '\n' );
			String filename = "/Users/rasmusjerlov/Desktop/myWeb/" + stringArray[1];
			String contentType = ContentType(filename);
			byte[] file = read(filename);

			//HTTP RESPONSE BESKED START
			outToClient.writeBytes("HTTP/1.1 200 OK \n");
			outToClient.writeBytes(contentType);
			outToClient.writeBytes("Connection: Close \n");
			outToClient.writeBytes("\n");

			outToClient.write(file);
			//HTTP RESPONSE BESKED SLUT
			connSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public byte[] read(String aInputFileName) throws FileNotFoundException {
// returns the content of a file in a binary array
		System.out.println("Reading in binary file named : " + aInputFileName);
		File file = new File(aInputFileName);
		System.out.println("File size: " + file.length());
		byte[] result = new byte[(int) file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while (totalBytesRead < result.length) {
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
// input.read() returns -1, 0, or more :
					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				System.out.println("Num bytes read: " + totalBytesRead);
			} finally {
				System.out.println("Closing input stream.");
				input.close();
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public String ContentType(String docuname) {
//returns the Content-Type headerline for a given document-name
		if (docuname.endsWith(".html")) {
			return ("Content-Type: text/html\n");
		} else if (docuname.endsWith(".gif")) {
			return ("Content-Type: image/gif\n");
		} else if (docuname.endsWith(".png")) {
			return ("Content-Type: image/png\n");
		} else if (docuname.endsWith(".jpg")) {
			return ("Content-Type: image/jpg\n");
		} else if (docuname.endsWith(".js")) {
			return ("Content-Type: text/javascript\n");
		} else if (docuname.endsWith(".css")) {
			return ("Content-Type: text/css\n");
		} else {
			return ("Content-Type: text/plain\n");
		}

	}
}
