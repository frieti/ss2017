package de.sbuettner.vs;

//import java.util.Scanner;

public class KuehlschrankControler {
	static SocketServer ks = new SocketServer();
	public static void main(String[] args) throws Exception {
		//int menu = 100;

		//Scanner sc = new Scanner(System.in);// Einlesen der Eingabe in den String "eingabe";
		System.out.println("KuehlschrankControler\nTim Friedrich und Sebastian Büttner");
		
		ks.run(9004);
		
		/*while(menu!=0) {
			System.out.println("(1) Start SocketServer");
			System.out.println("(2) Stop SocketServer");
			System.out.println("(3) Drucke History");
			System.out.println("(0) Quit");
			menu = sc.nextInt();
			
			switch(menu) {
			case 1:
				ks.run(9004);
				break;
			case 2:
				if(ks.isAlive()){
					ks.stop();
					System.out.println("Stopping SockertServer Thread");
				}
				break;
			case 3:
				ks.printHistory();
				break;
			}
		}
		sc.close();
		System.out.println("Beende Programm");*/
	}
}
