package de.sbuettner.vs.praktikum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Scanner;

public class Http_server extends Thread {
	StockAdministrator s;
	KuehlschrankControler k;

	public void putFuellstaende(StockAdministrator s) {
		this.s = s;
	}

	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(9005);

			System.out.println("Listening for connection on port 9005 ....");
			while (true) {
				try (Socket socket = server.accept()) {
					System.out.println("Server accepted!");
					byte[] bytes = new byte[1024];
					socket.getInputStream().read(bytes);
					String rq = new String(bytes);
					System.out.println(rq);
					String[] rql = rq.split("\n");	// Split into Request Lines
					
					if (rql[0].contains("/rest")) {
						/*for (int i=0; i< rql.length;i++) {
							System.out.println("Line " + i + " of rql: " + rql[i]);
						}*/
						
						String[] getsplit = rql[0].split(" ");
						String[] path = getsplit[1].split("/");
						String Artikel = path[2].replaceAll("\\?", "");
						Artikel = Artikel.trim();

						System.out.println("Artikel " + Artikel + " soll bestellt werden!");
						double p = s.order(Artikel, 1000);
						if (p>0) {
							SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String httpResponse = "HTTP/1.1 200 OK\r\n";
							httpResponse += "Content-type: text/html\r\n\r\n";
							httpResponse += "<html><head><meta charset=\"utf-8\"/><title>Kühlschrank Tim Friedrich, Sebastian Büttner</title></head></body>";
							httpResponse += "<p>" + Artikel + " wurde bestellt</p>";
							httpResponse += "<h2>Rechnung</h2>";
							httpResponse += "<p><u>Artikel\t\tMenge\t\tPreis</u></p>";
							httpResponse += "<p>" + Artikel + "\t\t1000\t\t" + p + "</p>";
							httpResponse += "OrderTime: " + myDate.format(new Date());
							httpResponse += "</body></html>";
							socket.getOutputStream().write(httpResponse.getBytes());
						} else {
							String httpResponse = "HTTP/1.1 200 OK\r\n";
							httpResponse += "Content-type: text/html\r\n\r\n";
							httpResponse += "<html><head><meta charset=\"utf-8\"/><title>Kühlschrank Tim Friedrich, Sebastian Büttner</title></head></body>";
							httpResponse += "<p>" + Artikel + " konnte nicht bestellt werden</p>";
							httpResponse += "</body></html>";
							socket.getOutputStream().write(httpResponse.getBytes());
						}
						
					} else {
						if (rq.contains("text/html")) {

							socket.getOutputStream().write(fuellstaendeHtml().getBytes());
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					socket.close();

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String fuellstaendeHtml() throws FileNotFoundException {
		String httpResponse = "HTTP/1.1 200 OK\r\n";
		httpResponse += "Content-type: text/html\r\n\r\n";
		httpResponse += "<html><head><meta charset=\"utf-8\"/><title>Kühlschrank Tim Friedrich, Sebastian Büttner</title></head></body>";
		httpResponse += "<table><tr><th>Artikel</th><th>Füllstand(ml)</th></tr>";
		System.out.print(s.getStock().toString());
		if (!(s.getStock().isEmpty())) {
			for (Entry<String, Integer> e : s.getStock().entrySet()) {
				String Artikel = e.getKey();
				int ml = e.getValue();
				httpResponse += "<tr><form action=\"/rest/" + Artikel + "\" method=\"get\">";
				httpResponse +="<td>" + Artikel + "</td><td>" + Integer.toString(ml) + "</td><td><button type=\"submit\">Auffüllen!</button></td>";
				httpResponse +="</form></tr>";
			}
		} else {
			httpResponse += "Keine Füllstände vorhanden!";
		}
		httpResponse += "</table>";

		Scanner sc = new Scanner(new File("output.log"));
		httpResponse += "<h2>History</h2>";

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			// System.out.println(line + " - next Line");
			if (line.indexOf("from") - 2 > 0) {
				line = line.substring(0, line.indexOf("from") - 2);
				// System.out.println(line + " - repaired Line");
				httpResponse += "<p>" + line + "</p>";

			}
		}

		httpResponse += "</body></html>";
		sc.close();
		return httpResponse;
	}
}