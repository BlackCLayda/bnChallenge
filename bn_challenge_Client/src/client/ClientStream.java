package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientStream {

	Socket s;

	public ClientStream(Socket s) {
		this.s=s;
	}

	/**************************** Client methods *********************************/

	static void ReceiveTxtFromServer(String[] userRequest, Socket socketClient) throws Exception {
		DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());
		DataInputStream dis = new DataInputStream(socketClient.getInputStream());

		dos.writeUTF(userRequest[0]);
		dos.writeUTF(userRequest[1]);
		FileOutputStream fos = new FileOutputStream(userRequest[1]);
		String str = "";
		while (!str.equals("END")) {
			str = dis.readUTF();
			if (str.equals("END")) {
				break;
			}
			byte[] deba = Encrypt.decryptByteArray(str);
			fos.write(deba, 0, deba.length);
		}
		System.out.println("file reveived");

	}

	static void ReceivePdfFromServer(String[] userRequest, Socket socketClient) throws Exception {
		DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());
		DataInputStream dis = new DataInputStream(socketClient.getInputStream());

		dos.writeUTF(userRequest[0]);
		dos.writeUTF(userRequest[1]);
		OutputStream fos = new FileOutputStream(userRequest[2] + "." + userRequest[3]);
		String str = "";
		while (!str.equals("END")) {
			str = dis.readUTF();
			if (str.equals("END")) {
				break;
			}
			byte[] deba = Encrypt.decryptByteArray(str);
			fos.write(deba, 0, deba.length);
			fos.flush();
		}
		fos.close();
		System.out.println("file reveived");

	}

	static void sendPdfToServer(String[] userRequest, Socket socketClient) throws Exception {
		DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());

		dos.writeUTF(userRequest[0]);
		dos.writeUTF(userRequest[2] + ".pdf");
		File f = new File(userRequest[1]);
		byte[] buf = new byte[16];
		InputStream is = new FileInputStream(f);
		int counter = 0;
		while ((counter = is.read(buf, 0, buf.length)) > 0) {
			String eba = Encrypt.encryptByteArray(buf);
			dos.writeUTF(eba);
		}
		dos.writeUTF("END");
		is.close();
		System.out.println("file sent\n");
	}

	static void sendTxtToServer(String[] userRequest, Socket socketClient) throws Exception {
		DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());

		dos.writeUTF(userRequest[0]);
		dos.writeUTF(userRequest[2] + ".txt");
		FileInputStream fis = new FileInputStream(userRequest[1]);
		byte[] buffer = new byte[2];

		while (fis.read(buffer) > 0) {
			String eba = Encrypt.encryptByteArray(buffer);
			dos.writeUTF(eba);
		}
		dos.writeUTF("END");
		fis.close();
		System.out.println("file sent\n");

	}

}
