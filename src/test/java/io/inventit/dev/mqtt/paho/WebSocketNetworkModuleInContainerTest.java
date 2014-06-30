/*
 * Copyright (c) 2014 Inventit Inc.
 */
package io.inventit.dev.mqtt.paho;

import java.net.URI;

import org.junit.Test;

public class WebSocketNetworkModuleInContainerTest {

	@Test
	public void test_connection() throws Exception {
		final String uriString = "ws://your.broker.com:80/mqtt";
		final String clientId = "my-client-id";

		final WebSocketNetworkModule module = new WebSocketNetworkModule(
				URI.create(uriString), "mqttv3.1", clientId);
		module.start();
		System.out.println("OK!");

		module.getOutputStream().write("test".getBytes());
		module.getOutputStream().flush();
		module.stop();
		System.out.println("END");
	}

}
