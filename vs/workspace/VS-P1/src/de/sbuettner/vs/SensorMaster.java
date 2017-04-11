package de.sbuettner.vs;

import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

public class SensorMaster {

	static Properties props =new Properties();
	static Reader reader;

	public static void main(String[] args) throws Exception {
		reader = new FileReader( "vs.properties" );
		props.load(reader);
		System.out.println("Properties loaded!:"
		+ "\nserverip:" + props.getProperty("serverip", "localhost")
		+ "\nserverport:" + props.getProperty("serverport", "9002"));
		

	}

}
