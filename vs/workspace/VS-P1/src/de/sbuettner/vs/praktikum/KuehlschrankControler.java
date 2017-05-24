package de.sbuettner.vs.praktikum;


//import java.util.Scanner;

public class KuehlschrankControler {
	static SocketServer socketserver = new SocketServer();
	static Http_server httpserver = new Http_server();
	static StockAdministrator s;
	public static void main(String[] args) throws Exception {
		//int menu = 100;

		//Scanner sc = new Scanner(System.in);// Einlesen der Eingabe in den String "eingabe";
		System.out.println("KuehlschrankControler\nTim Friedrich und Sebastian Büttner");
		
		s = new StockAdministrator();
		s.updateStock("Milch", 1500);
		s.updateStock("Asaft", 2000);
		
		httpserver.putFuellstaende(s);
		socketserver.putFuellstaende(s);
		
		socketserver.start();
		httpserver.start();
	}
	
	
}
