package de.sbuettner.vs.praktikum;

import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

public class SensorMaster {

	static Properties props = new Properties();
	static Reader reader;

	public static void main(String[] args) throws Exception {
		reader = new FileReader( "vs.properties" );
		props.load(reader);
		System.out.println("Properties loaded!:"
		+ "\nserverip:" + props.getProperty("serverip", "localhost")
		+ "\nserverport:" + props.getProperty("serverport", "9002"));
		
		Thread t1 = new Thread(new Sensor("Milch", 2000));
		Thread t2 = new Thread(new Sensor("OSaft", 1000));
		Thread t3 = new Thread(new Sensor("Apfelsaft", 1500));
		Thread t4 = new Thread(new Sensor("Wasser", 4000));
		
		t1.start();
		Thread.sleep(100);
		t2.start();
		Thread.sleep(100);
		t3.start();
		Thread.sleep(100);
		t4.start();
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
	}
}
