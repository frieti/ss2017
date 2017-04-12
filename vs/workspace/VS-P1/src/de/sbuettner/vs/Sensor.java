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

public class Sensor implements Runnable {

	Socket socket;
	BufferedReader fromServer;
	DataOutputStream toServer;
	UserInterface user = new UserInterface();
	static Properties props = new Properties();
	static Reader reader;
	String name;
	int ml;

	public Sensor(String name, int ml) {
		this.name = name;
		this.ml = ml;
	}

	@Override public void run() {
		try {
			reader = new FileReader("vs.properties");
			props.load(reader);

			socket = new Socket(props.getProperty("serverip", "localhost"),
					Integer.parseInt(props.getProperty("serverport", "9002"))); // Original war Port 9000
			toServer = new DataOutputStream(socket.getOutputStream());			// Datastream FROM Server		
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));	// Datastream TO Server
					
			while (ml!=0) {
				System.out.println("Sending: " + name + ":" + Integer.toString(ml));
				while (sendRequest(name + ":" + Integer.toString(ml))) {			// Send requests while connected
					receiveResponse();												// Process server's answer
				}
				Thread.sleep(1000);
				ml-=100;
			}
			while (sendRequest(".")) {											// Send . as finish of connection
				receiveResponse();												// Process server's answer
			}
			close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			socket.close();
			toServer.close();
			fromServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean sendRequest(String message) throws IOException {
		boolean holdTheLine = false; // Connection exists
		toServer.writeBytes(message + '\n');
	return holdTheLine;
	}

	private void receiveResponse() throws IOException {
		System.out.println("Server answers: " + new String(fromServer.readLine()) + '\n');
	}
}