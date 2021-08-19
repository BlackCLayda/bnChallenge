package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {

	// set port number for TCP connection
	static public final int DEFAULT_PORT = 8080;

	public static void main(String[] args) throws Exception {

		Socket socketClient = connectToServer();
		System.out.println("Connected to server");

		while (true) {

			String[] userRequest = getUserRequest();

			if (userRequest[0].equals("upload")) {
				
				if (userRequest[1]=="") { 
					System.out.println("missing argument");
					continue;
				}
				
				if (userRequest[3].equals("txt")) {

					ClientStream.sendTxtToServer(userRequest, socketClient);

				} else if (userRequest[3].equals("pdf")) {

					ClientStream.sendPdfToServer(userRequest, socketClient);

				}

			} else if (userRequest[0].equals("download")) {
				
				if (userRequest[1]=="") { 
					System.out.println("missing argument");
					continue;
				}

				if (userRequest[3].equals("txt")) {

					ClientStream.ReceiveTxtFromServer(userRequest, socketClient);

				} else if (userRequest[3].equals("pdf")) {

					ClientStream.ReceivePdfFromServer(userRequest, socketClient);

				}

			} else if (userRequest[0].equals("exit")) {
				DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());
				dos.writeUTF(userRequest[0]);
				System.out.println("Exit Program. Thanks to use this Application\n");
				break;
			} else {
				DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());
				dos.writeUTF(userRequest[0]);
				System.out.println(userRequest[0] + " is not a valid command\n");
			}
		}
	}

	private static Socket connectToServer() throws IOException {
		// Get the IP address
		InetAddress ip = InetAddress.getLocalHost();

		// Connect to the server's socket
		return new Socket(ip, DEFAULT_PORT);

	}

	// Function to get user's entry and parse it
	// the result table:
	// 		tab[0]: command
	//		tab[1]: argument
	// 		tab[2]: file name
	// 		tab[3]: file extension.
	
	public static String[] getUserRequest() throws IOException {
		Scanner sc = new Scanner(System.in);
		String[] result = new String[4];

		String[] result_partial = sc.nextLine().split(" ");
		System.arraycopy(result_partial, 0, result, 0, result_partial.length);

		if (result_partial.length == 1) {
			result[1] = "";
			result[2] = "";
			result[3] = "";
		} else {
			StringTokenizer st = new StringTokenizer(result[1], ".");
			String file_path = st.nextToken();

			result[3] = st.nextToken();

			StringTokenizer st1 = new StringTokenizer(file_path, "\\");
			result[2] = "";
			while (st1.hasMoreTokens()) {

				// this will hold the last token when loop completes
				result[2] = st1.nextToken();
			}
		}

		return result;

	}
}