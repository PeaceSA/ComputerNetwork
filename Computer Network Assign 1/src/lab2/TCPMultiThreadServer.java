
package lab2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMultiThreadServer {
	public static final int BUFSIZE = 1024;
	public static final int MYPORT = 4950;
	public static byte[] buf;
	private static int i;

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		System.out.println("/* * * * * * * * * * * * * * * * * * * * * * * /\n"
				+ "     TCP MULTI THREAD SERVER IS RUNNING ...\n" + "/* * * * * * * * * * * * * * * * * * * * * * * /");
		buf = new byte[BUFSIZE];
		ServerSocket serverSocket = null;
		try {
			/* Create a server socket that is listening to port 4950 */
			serverSocket = new ServerSocket(MYPORT);
		} catch (IOException e) {
			System.out.println("ERR IN THE CREATING A NEW SOCKET ");
			e.printStackTrace();
		}
		/* Create a client socket */
		Socket socket = null;
		while (true) {

			try {
				/* Accept the connection of the client socket */
				socket = serverSocket.accept();
				System.out.println("A CLIENT NUMBER (" + ++i + ") FROM IP <" + socket.getInetAddress()
						+ "> AND PORT NUMBER <" + socket.getPort() + "> STARTED ");

			} catch (IOException e) {

				e.printStackTrace();
			}

			/*
			 * After the acceptance of the socket it starts a new thread for
			 * sending and receiving process
			 */

			Thread t1 = new Thread(new ProcessingSocket(socket, buf, i));
			t1.start();

		}

	}

}

/**
 * A new thread that perform the processing of the sending and receiving the
 * incoming echo message.
 */
class ProcessingSocket implements Runnable {
	private Socket socket;
	private PrintWriter send;
	private InputStream recieve;
	private byte[] buf;
	private int i;

	public ProcessingSocket(Socket socket, byte[] buf, int i) {
		this.socket = socket;
		this.buf = buf;
		this.i = i;

	}

	@Override
	public void run() {

		try {

			recieve = socket.getInputStream();

			while (true) {
				/*
				 * Read some number of the input stream and stores them into
				 * "buf".
				 */
				recieve.read(buf);

				/* Create a string from the bytes */
				String receivedString = new String(buf).trim();

				/* This code will check if the input stream is empty */
				if (!receivedString.isEmpty()) {

					System.out.println("<---------- FROM CLIENT [" + i + "]");
					System.out.println(receivedString);
					send = new PrintWriter(socket.getOutputStream(), true);

					send.println(receivedString);
					System.out.println("---------> TO CLIENT [" + i + "]");

					/* To clean up the buffer */
					buf = new byte[buf.length];
				} else
					break;
			}

			socket.close();

		} catch (IOException e) {
			System.out.println("ERR INSIDE YOUR RUN METHOD");
			e.printStackTrace();
		}

	}
}
