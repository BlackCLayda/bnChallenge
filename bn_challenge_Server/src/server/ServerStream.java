package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerStream {

	Socket s;

	public ServerStream(Socket s) {
		this.s=s;
	}


	/**************************** Server methods *********************************/

	public void sendPdfToClient(String receive_fileName) throws Exception {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		BufferedReader reader = new BufferedReader(new FileReader(receive_fileName + ".txt"));
		while (true) {
			String str = reader.readLine();
			dos.writeUTF(str);
			if (str.equals("END")) {
				break;
			}
		}
		reader.close();

	}

	public void sendTxtToClient(String receive_fileName) throws Exception {
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		BufferedReader reader = new BufferedReader(new FileReader(receive_fileName));
		while (true) {
			String str = reader.readLine();
			dos.writeUTF(str);
			if (str.equals("END")) {
				break;
			}
		}
		reader.close();

	}

	public void receivePdfFromClient(String receive_fileName) throws Exception {
		DataInputStream dis = new DataInputStream(s.getInputStream());
		PrintWriter out = new PrintWriter(receive_fileName + ".txt");
		String str = "";
		while (!str.equals("END")) {
			str = dis.readUTF();
			out.println(str);
		}
		out.close();
		System.out.println("File recieved\n");

	}

	public void receiveTxtFromClient(String receive_fileName) throws Exception {
		DataInputStream dis = new DataInputStream(s.getInputStream());
		PrintWriter out = new PrintWriter(receive_fileName);
		String str = "";
		while (!str.equals("END")) {
			str = dis.readUTF();
			out.println(str);
		}
		out.close();
		System.out.println("File recieved\n");

	}

}
