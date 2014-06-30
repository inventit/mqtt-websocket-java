/*
 * Copyright (c) 2014 Inventit Inc.
 */
package io.inventit.dev.mqtt.paho;

public class JettyStrErrLogUtils {

	private JettyStrErrLogUtils() {
	}

	public static void enableLog() {
		System.setProperty("org.eclipse.jetty.util.log.class",
				"org.eclipse.jetty.util.log.StrErrLog");
		System.setProperty("org.eclipse.jetty.LEVEL", "INFO");
		System.setProperty("org.eclipse.jetty.websocket.LEVEL", "DEBUG");
	}
}
