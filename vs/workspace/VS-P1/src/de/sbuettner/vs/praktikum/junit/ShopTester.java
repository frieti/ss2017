package de.sbuettner.vs.praktikum.junit;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import de.sbuettner.vs.praktikum.ShopService;
import de.sbuettner.vs.praktikum.StockAdministrator;

public class ShopTester {
	public static void main(String[] args) throws TException {
		long timeBef = System.currentTimeMillis();
		double price;
		TTransport transport;
		String cheapestshop = "localhost:9006";
		String[] split = cheapestshop.split(":");
		System.out.println("Strarting Test");
		for (int i = 0; i < 10000; i++) {
			transport = new TSocket(split[0], Integer.parseInt(split[1]));

			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			ShopService.Client client = new ShopService.Client(protocol);

			price = client.getPrice("Milch");
			transport.close();
		}
		long timeAfter = System.currentTimeMillis();
		long timeAllResponses = timeAfter - timeBef;
		System.out.println("Needed: " + timeAllResponses + " msec");
	}
}
