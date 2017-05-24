package de.sbuettner.vs.praktikum;

// 05.04.16

/**
 *
 * @author Peter Altenberd (Translated into English by Ronald Moore)
 *         Computer Science Dept. Fachbereich Informatik Darmstadt Univ. of
 *         Applied Sciences Hochschule Darmstadt
 */

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketServer extends Thread {
	private DatagramSocket socket;
	private StockAdministrator s;
	private FileWriter fw;

	public void putFuellstaende(StockAdministrator s) {
		this.s = s;
	}

	public void run(int port) throws IOException {
		System.out.println("Starting SocketServer Thread!");
		socket = new DatagramSocket(9004);
		fw = new FileWriter(new File("output.log"));

		while (true) {
			// Auf Anfrage warten

			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			socket.receive(packet);

			// Empfänger auslesen

			InetAddress address = packet.getAddress();
			int pport = packet.getPort();
			int len = packet.getLength();
			String line = new String(packet.getData(), "UTF-8");
			System.out.println(line);
			String[] split;
			split = line.split(":");
			if (split.length == 2 && split!=null) {
				//System.out.println("check");
				String key = split[0];
				int value = Integer.parseInt(split[1].trim());
				s.updateStock(key, value);
			}
			SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy

			String logLine = myDate.format(new Date()) + " : " + (line) + " from: " + address + ":" + pport
					+ " of length " + len + "bytes!";

			fw.write(logLine + "\n");

			System.out.println(logLine);
		}

	}

	public void run() {
		try {
			run(9004);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
