
package lab2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyWebServer {
	public static final int BUFSIZE = 1024;
	public static final int MYPORT = 8888;
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

			ConnectionProcess process =new ConnectionProcess(socket, buf, i);
			process.start();
			

		}

	}

}

