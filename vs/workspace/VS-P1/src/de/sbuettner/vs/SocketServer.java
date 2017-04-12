package de.sbuettner.vs;

// 05.04.16

/**
 *
 * @author Peter Altenberd (Translated into English by Ronald Moore)
 *         Computer Science Dept. Fachbereich Informatik Darmstadt Univ. of
 *         Applied Sciences Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;

public class SocketServer extends Thread {

	public void run(int port) {
		System.out.println("Starting SocketServer Thread!");
		ServerSocket contactSocket;
		try {
			contactSocket = new ServerSocket(port);		// original war Port 9999
			while (true) {								// Handle connection request
				Socket client = contactSocket.accept(); // create communication socket
				System.out.println("Connection with: " + client.getRemoteSocketAddress());
				new ClientHandler(client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void run() {
		run(9003);
	}
}
