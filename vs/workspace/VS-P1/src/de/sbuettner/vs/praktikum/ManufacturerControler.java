package de.sbuettner.vs.praktikum;

public class ManufacturerControler {
	static Manufacturer m;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		m = new Manufacturer();
		m.start();
		try {
			m.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
