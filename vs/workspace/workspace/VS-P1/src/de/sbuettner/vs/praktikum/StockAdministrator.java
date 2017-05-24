package de.sbuettner.vs.praktikum;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;



public class StockAdministrator {
	private Map<String, Integer> stock;
	private List<String> shops;
	FileReader reader;
	Properties props = new Properties();
	
	public StockAdministrator() {
		stock = new HashMap<String, Integer>();
		shops = new ArrayList<String>();
		try {
			reader = new FileReader( "shops.properties" );
			props.load(reader);
			for (int i=0; i<props.size();i++) {
				shops.add(props.getProperty("shop"+ Integer.toString(i+1)));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Properties loaded!");
		System.out.print(shops.toString());
	}
	
	public synchronized int getStock(String s) {
		Integer r = null;
		if (stock.containsKey(s)) {
			r = stock.get(s);
		}
		return r;
	}
	
	public synchronized void updateStock(String Article, int value) {
		if (stock.containsKey(Article)) {
			stock.remove(Article);
			stock.put(Article, value);
		} else {
			stock.put(Article, value);
		}
	}
	
	public double order(String Article, int Amount) {
		TTransport transport;
		String cheapestshop= "localhost:9006";
		String[] split;
		
		double price = 10000;
		try {
				for (String s : shops) {
					split = s.split(":");
					transport = new TSocket(split[0], Integer.parseInt(split[1]));
					transport.open();

					TProtocol protocol = new TBinaryProtocol(transport);
					ShopService.Client client = new ShopService.Client(protocol);

					if (client.getPrice(Article)<price) {
						price = client.getPrice(Article);
						cheapestshop = s;
					}
					transport.close();
					System.out.println(price);
				}

				split = cheapestshop.split(":");
				transport = new TSocket(split[0], Integer.parseInt(split[1]));
				transport.open();

				TProtocol protocol = new TBinaryProtocol(transport);
				ShopService.Client client = new ShopService.Client(protocol);

			   price = client.getPrice(Article);
			   System.out.println(price);
			   client.order(Article, Amount);

			   transport.close();
		} catch (Exception e) {
			   e.printStackTrace();
		}
		return price;
	}
	
	public Map<String, Integer> getStock() {
		return stock;
	}
}
