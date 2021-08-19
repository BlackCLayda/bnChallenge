package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

class Services extends Thread {

	Socket s;

	// Construct a services instance for a defined socket.
	public Services(Socket s) {
		this.s = s;
	}

	public void run() {

		String received;
		ServerStream stream = new ServerStream(s);

		try {
			while (true) {

				DataInputStream dis = new DataInputStream(s.getInputStream());
				System.out.println("Waiting for client choice...");

				received = dis.readUTF();
				String receive_fileName = dis.readUTF();
				String extension = getExtention(receive_fileName);

				if (received.equals("upload")) {

					if (extension.equals("txt")) {

						stream.receiveTxtFromClient(receive_fileName);

					} else if (extension.equals("pdf")) {

						stream.receivePdfFromClient(receive_fileName);

					}
				}

				else if (received.equals("download")) {

					if (extension.equals("txt")) {
	
						stream.sendTxtToClient(receive_fileName);

					} else if (extension.equals("pdf")) {

						stream.sendPdfToClient(receive_fileName);

					}

				} else if (received.equals("3")) {

					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("EXCEPTION" + e.getMessage());
			e.printStackTrace();
		}
	}


	private String getExtention(String receive_fileName) {
		StringTokenizer st = new StringTokenizer(receive_fileName, ".");
		@SuppressWarnings("unused")
		String file_name_ = st.nextToken();
		String file_extension = st.nextToken();
		return file_extension;
	}

}
