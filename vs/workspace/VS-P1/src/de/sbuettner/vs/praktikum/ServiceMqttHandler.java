package de.sbuettner.vs.praktikum;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class ServiceMqttHandler {
	private MqttClient client;
	private FileReader reader;
	Properties props = new Properties();
	
	public ServiceMqttHandler() {
		try {
			try {
				reader = new FileReader("ServiceMqtt.properties");
				props.load(reader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String target = "tcp://" + props.getProperty("ip");
			client = new MqttClient(target, "ManufacturerID");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void easyorder1000(String article) {
		try {
			need(article, 1000);
			int id = (int) (Math.random() * 1000.0);
			orderArticle(article, 1000, id);
		} catch (MqttException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void need(String article, int amount) throws MqttSecurityException, MqttException {
		client.connect();

		MqttMessage message = new MqttMessage(Integer.toString(amount).getBytes());
		client.publish("VS/Manufacturer/need/" + article, message);
		client.disconnect();
	}
	
	public void orderArticle(String article, int amount, final int id) throws MqttException, InterruptedException {
		client.connect();
		final Map<Integer, Double> offers = new HashMap<Integer,Double>();
		client.setCallback(new MqttCallback() {
			@Override
		       public void connectionLost(Throwable throwable) { }
		 
		       @Override
		       public void messageArrived(String t, MqttMessage m) throws Exception {
		    	   
		         System.out.println(new String(m.getPayload()));
		         String mes = new String(m.getPayload());
		         String[] mesp = mes.split(";");
		         offers.put(Integer.parseInt(mesp[0]), Double.parseDouble(mesp[1]));
		       }
		 
		       @Override
		       public void deliveryComplete(IMqttDeliveryToken t) { }
		       });
		
		client.subscribe("VS/Manufacturer/offer/" + article);
		Thread.sleep(1000); // Sleep damit Manufacturer genug Zeit hat angebote zu schicken
		if (!offers.isEmpty()) {
			int shopkey=offers.keySet().iterator().next();
			for (Map.Entry<Integer, Double> entry: offers.entrySet()){
				if (entry.getValue() < offers.get(shopkey)) {
					shopkey = entry.getKey();
				}
			}
			String topic = "VS/Manufacturer/order/" + Integer.toString(shopkey);
			String m = article + ":" + Integer.toString(amount);
			MqttMessage mm = new MqttMessage(m.getBytes());
			client.publish(topic, mm);
			System.out.println("Order abgesetzt!\nShop: " + shopkey + "\nArtikel: " + article + "\nAmount" + amount);
		}
		
		 
		
		
		client.disconnect();
	}
}
