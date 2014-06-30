/*
 * Copyright (c) 2014 Inventit Inc.
 */
package io.inventit.dev.mqtt.paho;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Before;
import org.junit.Test;

/**
 * This test is skipped when building with maven.
 */
public class MqttWebSocketAsyncClientInContainerTest {

	@Before
	public void setUp() throws Exception {
		JettyStrErrLogUtils.enableLog();
		PahoConsoleLogger.enableLog();
	}

	@Test
	public void test() throws Exception {
		// Plain MQTT
		// final String uriString = "tcp://your-mqtt-broker:1883";

		// MQTT over WebSocket
		final String uriString = "wss://your-ws-broker/mqtt";

		// Credentials
		final String clientId = "123";
		final String userName = "";
		final String password = "";

		final MqttWebSocketAsyncClient client = new MqttWebSocketAsyncClient(
				uriString, clientId, new MemoryPersistence());
		final MqttConnectOptions options = new MqttConnectOptions();
		options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
		options.setCleanSession(true);
		options.setUserName(userName);
		options.setPassword(password.toCharArray());
		final IMqttToken token = client.connect(options);
		client.setCallback(new MqttCallback() {

			@Override
			public void messageArrived(String topic, MqttMessage message)
					throws Exception {
				System.out.println("messageArrived:" + topic + "/" + message);
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
				System.out.println("deliveryComplete:" + token);
			}

			@Override
			public void connectionLost(Throwable cause) {
				System.out.println("connectionLost");
				cause.printStackTrace();
			}
		});

		try {
			final long start = System.currentTimeMillis();
			while (client.isConnected() == false
					&& System.currentTimeMillis() - start < 5000) {
				Thread.sleep(100);
			}
			System.out.println("OK!");
		} finally {
			System.out.println(token.isComplete());
			client.disconnect();
			client.close();
		}
	}

}
