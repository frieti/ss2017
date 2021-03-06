package de.sbuettner.vs.praktikum;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Manufacturer extends Thread implements MqttCallback {
	int shopid;
	MqttClient client;
	Map<String, Double> articles = new HashMap<String, Double>();
	Properties props = new Properties();
	Reader reader;

	@Override
	public void run() {
		try {
			reader = new FileReader("Manufacturer.properties");
			props.load(reader);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shopid = (int) (Math.random() * 1000.0);
		startprice();
		try {
			String target = "tcp://" + props.getProperty("ip");
			client = new MqttClient(target, "ManufacturerID");
			client.connect();
			client.setCallback(this);

			for (Map.Entry<String, Double> entry : articles.entrySet()) {
				client.subscribe("VS/Manufacturer/need/" + entry.getKey());
			}
			client.subscribe("VS/Manufacturer/order/" + shopid);
			while(true);
			//client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	private void startprice() {
		articles.put("Milch", 1.1);
		articles.put("Apfelsaft", 0.8);
		articles.put("Wasser", 0.15);
	}

	private double price(String article) {
		double newP = articles.get(article) * Math.random() * 0.1;
		articles.replace(article, newP);
		return newP;
	}

	@Override
	public void connectionLost(Throwable throwable) {
	}

	@Override
	public void messageArrived(String t, MqttMessage m) throws Exception {
		System.out.println(new String(t));
		System.out.println(new String(m.getPayload()));
		if (t.contains("VS/Manufacturer/offer")) {
			// String needrequest = new String(m.getPayload());
			// String[] needrequestS = needrequest.split(":");
			String[] topicS = t.split("/");
			String article = topicS[3];
			String response = Integer.toString(shopid) + ":" + Double.toString(price(article));
			MqttMessage responseM = new MqttMessage(response.getBytes());
			client.publish("VS/Manufacturer/offer/" + article, responseM);
		} else if (t.contains("VS/Manufacturer/order")) {
			String[] mm = (new String(m.getPayload())).split(":");
			String article = mm[0];
			String amount = mm[1];
			System.out.println("Somebody ordered " + amount + "Units of " + article + "!");
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken t) {
	}
}
