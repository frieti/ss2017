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
import java.util.Properties;

public class Sensor extends Thread {

	static String line;
	static Socket socket;
	static BufferedReader fromServer;
	static DataOutputStream toServer;
	static UserInterface user = new UserInterface();
	static Properties props =new Properties();
	static Reader reader;

	public static void main(String[] args) throws Exception {
		reader = new FileReader( "vs.properties" );
		props.load(reader);
		System.out.println("Properties loaded!:"
		+ "\nserverip:" + props.getProperty("serverip", "localhost")
		+ "\nserverport:" + props.getProperty("serverport", "9002"));
		
		socket = new Socket(props.getProperty("serverip", "localhost"), Integer.parseInt(props.getProperty("serverport", "9002")));					// Original war Port 9000
		toServer = new DataOutputStream(						// Datastream FROM Server
				socket.getOutputStream());
		fromServer = new BufferedReader(						// Datastream TO Server
				new InputStreamReader(socket.getInputStream()));
		while (sendRequest()) {									// Send requests while connected
			receiveResponse();									// Process server's answer
		}
		socket.close();
		toServer.close();
		fromServer.close();
	}

	private static boolean sendRequest() throws IOException {
		boolean holdTheLine = true;								// Connection exists
		user.output("Enter message for the Server, or end the session with . : ");
		toServer.writeBytes((line = user.input()) + '\n');
		if (line.equals(".")) {									// Does the user want to end the session?
			holdTheLine = false;
		}
		return holdTheLine;
	}

	private static void receiveResponse() throws IOException {
		user.output("Server answers: " + new String(fromServer.readLine()) + '\n');
	}
	
	public void run() {
	}
}