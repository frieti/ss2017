package de.sbuettner.vs.praktikum;

// 05.04.16

/**
 *
 * @author Peter Altenberd (Translated into English by Ronald Moore)
 *         Computer Science Dept. Fachbereich Informatik Darmstadt Univ. of
 *         Applied Sciences Hochschule Darmstadt
 * @author Tim Friedrich, Sebastian Buettner 
 */

import java.io.*;
import java.net.*;
import java.util.Properties;


public class Sensor implements Runnable {

	DatagramSocket socket;
	UserInterface user = new UserInterface();
	Properties props = new Properties();
	Reader reader;
	String name;
	int ml;

	public Sensor(String name, int ml) {
		this.name = name;
		this.ml = ml;
	}

	@Override
	public void run() {
		try {
			reader = new FileReader("vs.properties");
			props.load(reader);

			socket = new DatagramSocket();
			String ip = props.getProperty("serverip", "localhost");
			int port = Integer.parseInt(props.getProperty("serverport", "9002")); // Original war Port 9000
			DatagramPacket pkt;
					
			while (ml>=0) {
				System.out.println("Sending: \"" + name + ":" + Integer.toString(ml) + "\"");
				InetAddress ia = InetAddress.getByName(ip);
				byte[] data = ((name + ":" + Integer.toString(ml))).getBytes("UTF-8");

				pkt = new DatagramPacket(data, data.length, ia, port);
				socket.send(pkt);
				Thread.sleep(1000);
				ml -= 100;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
