package de.sbuettner.socket_http_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {
	public static void main(String[] args) {
		ServerSocket server;
		try {
			server = new ServerSocket(8282);

			System.out.println("Listening for connection on port 8080 ....");
			while (true) {
				try (Socket socket = server.accept()) {
					Date today = new Date();
					String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
					socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}