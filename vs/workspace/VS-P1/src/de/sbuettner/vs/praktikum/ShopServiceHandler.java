package de.sbuettner.vs.praktikum;


import org.apache.thrift.TException;

public class ShopServiceHandler implements ShopService.Iface{
	ServiceMqttHandler mq = new ServiceMqttHandler();
	
	@Override
	public double getPrice(String article) throws TException {
		double answer= 0.0;
		switch (article) {
		case "Milch":
			answer = 1.10;
			break;
		case "OSaft":
			answer = 1.20;
			break;
		case "Apfelsaft":
			answer = 0.99;
			break;
		case "Wasser":
			answer = 0.20;
			break;
		}
		return answer;
	}

	@Override
	public boolean order(String article, int amount) throws TException {
		System.out.println("Somebody ordered: \nAmount Price\n" + amount + "    " + article);
		
		try {
			int id = (int) (Math.random() * 1000.0);
			mq.need(article, amount); 
			mq.orderArticle(article, amount, id);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return true;
	}
}
