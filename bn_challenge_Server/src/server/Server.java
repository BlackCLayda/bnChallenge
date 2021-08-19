package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	// set port number for TCP connection
	static public final int DEFAULT_PORT = 8080;

	// Main method of TCP client
	public static void main(String[] args) throws IOException {

		// Get the IP address
		InetAddress ip = InetAddress.getLocalHost();

		// Set socket (bind with IP and port)
		ServerSocket ss = new ServerSocket(DEFAULT_PORT);
		System.out.println("Server is launched\n");

		while (true) {

			// Waiting to accept clients
			System.out.println("Waiting for connection...\n");
			Socket s = ss.accept();
			System.out.println("Client accepted :" + s);

			// Send client to be handled
			new Services(s).start();

		}
	}
}
