/*
 * Copyright (c) 2014 Inventit Inc.
 */
package io.inventit.dev.mqtt.paho;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;

public class MqttWebSocketAsyncClientTest {

	@Test
	public void test_createDummyURI() {
		final URI uri = URI.create(MqttWebSocketAsyncClient
				.createDummyURI("ws://server.address:123/path"));
		assertEquals("tcp", uri.getScheme());
		assertEquals("DUMMY-server.address", uri.getHost());
		assertEquals(123, uri.getPort());
		assertEquals("", uri.getPath());
	}

}
