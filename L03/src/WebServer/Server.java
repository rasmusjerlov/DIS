package WebServer;
import java.io.*;
import java.net.*;
public class Server {
	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(6789);

		Socket connectionSocket = welcomeSocket.accept();
		BufferedReader in = null;
		DataOutputStream out = null;
		in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		out = new DataOutputStream(connectionSocket.getOutputStream());
		try {
			System.out.println("Server ready");
			String readFromClient = in.readLine();

			String[] stringArray = readFromClient.split(" ");
			String filename = "/Users/rasmusjerlov/java-web-server/" + stringArray[1].substring(1);

			String contentType = ContentType(filename);


			byte[] file = read(filename);

			out.writeBytes("HTTP/1.1 200 OK\n");
			out.writeBytes(contentType);
			out.writeBytes("Content-length: " + file.length + "\n");
			out.writeBytes("\n");
			out.write(file, 0, file.length);

			connectionSocket.close();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] read(String aInputFileName) throws FileNotFoundException {
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

			}

		} catch (FileNotFoundException ex) {
			throw ex;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String ContentType(String docuname) {
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
