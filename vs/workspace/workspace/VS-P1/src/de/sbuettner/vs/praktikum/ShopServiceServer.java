package de.sbuettner.vs.praktikum;

import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport; 
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;

public class ShopServiceServer{
	static Boolean testcycle = true;
	static ServiceMqttHandler mq = new ServiceMqttHandler();
	
	public static void StartsimpleServer(ShopService.Processor<ShopServiceHandler> processor) {
		try {
			   TServerTransport serverTransport = new TServerSocket(9006);
			   TServer server = new TSimpleServer(
			     new Args(serverTransport).processor(processor));

			   // Use this for a multithreaded server
			   // TServer server = new TThreadPoolServer(new
			   // TThreadPoolServer.Args(serverTransport).processor(processor));

			   if (testcycle) {
				   for (int i=0; i<5; i++) {
					   System.out.println("mq.easyorder1000(\"Wasser\");");
					   mq.easyorder1000("Wasser");
					   Thread.sleep(3000);
				   }
			   } else {
				   System.out.println("Starting the simple server...");
				   server.serve();
			   }
			   
			   
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
	}

	
	public static void main(String[] args) {
		  StartsimpleServer(new ShopService.Processor<ShopServiceHandler>(new ShopServiceHandler()));
	}

}
