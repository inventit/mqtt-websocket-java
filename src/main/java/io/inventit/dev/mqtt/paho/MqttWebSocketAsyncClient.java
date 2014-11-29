/*
 * Copyright (c) 2014 Inventit Inc.
 */
package io.inventit.dev.mqtt.paho;

import java.net.URI;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.TimerPingSender;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class MqttWebSocketAsyncClient extends MqttAsyncClient {

	private static final String CLASS_NAME = MqttWebSocketAsyncClient.class
			.getName();
	private final Logger log;

	private final String serverURI;

	/**
	 * Create a dummy URI in order to by-pass MqttConnectOptions.validateURI()
	 * validation.
	 * 
	 * @param original
	 * @return
	 */
	protected static String createDummyURI(String original) {
		if (!original.startsWith("ws:") && !original.startsWith("wss:")) {
			return original;
		}
		final URI uri = URI.create(original);
		return "tcp://DUMMY-" + uri.getHost() + ":"
				+ (uri.getPort() > 0 ? uri.getPort() : 80);
	}

	protected static boolean isDummyURI(String uri) {
		return uri.startsWith("tcp://DUMMY-");
	}

	public MqttWebSocketAsyncClient(String serverURI, String clientId,
			MqttClientPersistence persistence, MqttPingSender pingSender,
			String loggerName) throws MqttException {

		super(createDummyURI(serverURI), clientId, persistence, pingSender);
		this.serverURI = serverURI;

		final String methodName = "MqttWebSocketAsyncClient";

		this.log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT,
				(loggerName == null || loggerName.length() == 0) ? CLASS_NAME
						: loggerName);

		// @TRACE 101=<init> ClientID={0} ServerURI={1} PersistenceType={2}
		if (log.isLoggable(Logger.FINE)) {
			log.fine(CLASS_NAME, methodName, "101", new Object[] { clientId,
					serverURI, persistence });
		}
	}

	public MqttWebSocketAsyncClient(String serverURI, String clientId,
			MqttClientPersistence persistence, String loggerName)
			throws MqttException {
		this(serverURI, clientId, persistence, new TimerPingSender(),
				loggerName);
	}

	public MqttWebSocketAsyncClient(String serverURI, String clientId,
			MqttClientPersistence persistence) throws MqttException {
		this(serverURI, clientId, persistence, null);
	}

	public MqttWebSocketAsyncClient(String serverURI, String clientId,
			String loggerName) throws MqttException {
		this(serverURI, clientId, new MqttDefaultFilePersistence(), loggerName);
	}

	public MqttWebSocketAsyncClient(String serverURI, String clientId)
			throws MqttException {
		this(serverURI, clientId, (String) null);
	}

	/**
	 * Same as super{@link #createNetworkModules(String, MqttConnectOptions)}
	 */
	@Override
	protected NetworkModule[] createNetworkModules(String address,
			MqttConnectOptions options) throws MqttException,
			MqttSecurityException {
		final String methodName = "createNetworkModules";
		// @TRACE 116=URI={0}
		if (log.isLoggable(Logger.FINE)) {
			log.fine(CLASS_NAME, methodName, "116", new Object[] { address });
		}
		NetworkModule[] networkModules = null;
		String[] serverURIs = options.getServerURIs();
		String[] array = null;
		if (serverURIs == null) {
			array = new String[] { address };
		} else if (serverURIs.length == 0) {
			array = new String[] { address };
		} else {
			array = serverURIs;
		}

		networkModules = new NetworkModule[array.length];
		for (int i = 0; i < array.length; i++) {
			networkModules[i] = createNetworkModule(array[i], options);
		}

		log.fine(CLASS_NAME, methodName, "108");
		return networkModules;
	}

	/**
	 * Factory method to create the correct network module, based on the
	 * supplied address URI.
	 *
	 * @param address
	 *            the URI for the server.
	 * @param options
	 *            MQTT connect options
	 * @return a network module appropriate to the specified address.
	 */
	protected NetworkModule createNetworkModule(String input,
			MqttConnectOptions options) throws MqttException,
			MqttSecurityException {
		final String address = isDummyURI(input) ? this.serverURI : input;
		if (!address.startsWith("ws:") && !address.startsWith("wss:")) {
			return super.createNetworkModules(address, options)[0];
		}

		final String methodName = "createNetworkModule";
		// @TRACE 115=URI={0}
		if (log.isLoggable(Logger.FINE)) {
			log.fine(CLASS_NAME, methodName, "115", new Object[] { address });
		}

		final String subProtocol;
		if (options.getMqttVersion() == MqttConnectOptions.MQTT_VERSION_3_1) {
			// http://wiki.eclipse.org/Paho/Paho_Websockets#Ensuring_implementations_can_inter-operate
			subProtocol = "mqttv3.1";
		} else {
			// http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/cs01/mqtt-v3.1.1-cs01.html#_Toc388534418
			subProtocol = "mqtt";
		}
		return newWebSocketNetworkModule(URI.create(address), subProtocol,
				options);
	}

	/**
	 * A factory method for instantiating a {@link NetworkModule} with websocket
	 * support. Subclasses is able to extend this method in order to create an
	 * arbitrary {@link NetworkModule} class instance.
	 * 
	 * @param uri
	 * @param subProtocol
	 *            Either `mqtt` for MQTT v3 or `mqttv3.1` for MQTT v3.1
	 * @param options
	 * @return
	 */
	protected NetworkModule newWebSocketNetworkModule(URI uri,
			String subProtocol, MqttConnectOptions options) {
		final WebSocketNetworkModule netModule = new WebSocketNetworkModule(
				uri, subProtocol, getClientId());
		netModule.setConnectTimeout(options.getConnectionTimeout());
		return netModule;
	}

}
