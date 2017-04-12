package de.sbuettner.vs;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread{
	
	Socket client;
	FileWriter fw;
    BufferedWriter bw;
	String line;
	BufferedReader fromClient;
	DataOutputStream toClient;
	
	public ClientHandler(Socket client) {
		this.client = client;
	}
	
	public void run() {
		try {
			fw = new FileWriter("history.log");
			bw = new BufferedWriter(fw);
			fromClient = new BufferedReader( 						// Datastream FROM Client
					new InputStreamReader(client.getInputStream()));
			toClient = new DataOutputStream(client.getOutputStream()); 	// Datastream TO Client
			while (receiveRequest()) {								// As long as connection exists
				sendResponse();
			}
			fromClient.close();
			toClient.close();
			client.close();
			System.out.println("Session ended, Server remains active");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	boolean receiveRequest() throws IOException {
		boolean holdTheLine = true;
		line = fromClient.readLine();
		SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		System.out.println(myDate.format(new Date()) + " : " + (line));
		bw.write("history.log");
		bw.append(myDate.format(new Date()) + " : " + line);
		if (line.equals(".")) {										// End of session?
			holdTheLine = false;
			fw.close();
		}
		return holdTheLine;
	}

	void sendResponse() throws IOException {
		toClient.writeBytes(line);				// Send answer
	}

}