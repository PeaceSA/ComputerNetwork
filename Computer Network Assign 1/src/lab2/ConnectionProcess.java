package lab2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionProcess extends Thread {
	private Socket socket;
	private Scanner receive;
	private PrintWriter send;
	// private InputStream recieve;
	private byte[] buf;
	private int i;
	private String receivedString;

	public ConnectionProcess(Socket socket, byte[] buf, int i) {
		this.socket = socket;
		this.buf = buf;
		this.i = i;
	}

	@Override
	public void run() {

		try {

			receive = new Scanner(socket.getInputStream());

			// recieve = socket.getInputStream();
			send = new PrintWriter(socket.getOutputStream(), true);

			HttpRequest();
			HttpRespond();

			// while (true) {
			//
			// // recieve.read(buf);
			//
			// /* Create a string from the bytes */
			// // receivedString = new String(buf).trim();
			//
			// /* This code will check if the input stream is empty */
			// if (!receivedString.isEmpty()) {
			//
			// System.out.println("<---------- FROM CLIENT [" + i + "]");
			// System.out.println(receivedString);
			//
			// send.println(receivedString);
			// System.out.println("---------> TO CLIENT [" + i + "]");
			//
			// /* To clean up the buffer */
			// buf = new byte[buf.length];
			// } else
			// break;
			// }

			socket.close();

		} catch (IOException e) {
			System.out.println("ERR INSIDE YOUR RUN METHOD");
			e.printStackTrace();
		}

	}

	private void HttpRequest() {

		while (receive.hasNext()) {
			receivedString += "\n" + receive.nextLine();

		}

		System.out.println("<---------- FROM CLIENT [" + i + "]");

		System.out.println(receivedString);

	}

	private void HttpRespond() {
		send.println(receivedString);
		System.out.println("---------> TO CLIENT [" + i + "]");

	}

}
