# MQTT over WebSocket  library for Java

This library offers MQTT client functionality over WebSocket transport with [Paho](http://www.eclipse.org/paho/) library and [Jetty](http://www.eclipse.org/jetty/) library.

# Supported MQTT Version

1. MQTT v3.1   (with Sub-Protocol: `mqttv3.1`)
1. MQTT v3.1.1 (with Sub-Protocol: `mqtt`) ... DEFAULT

# Supported Paho MQTT library version and Jetty WebSocket Client version

1. [Paho org.eclipse.paho.mqtt.java 1.0.0](http://git.eclipse.org/c/paho/org.eclipse.paho.mqtt.java.git/tag/?id=v1.0.0)
1. [Jetty websocket-client 9.2.1.v20140609](http://www.eclipse.org/jetty/documentation/9.2.1.v20140609/jetty-websocket-client-api.html)

# Supported JDK/JRE Version

JDK/JRE 1.7+ is required as Jetty no longer supports JDK/JRE 1.6.

# How to build

Install maven then run the following command on the project root directory.

Note that Paho Java library is included in this project as the binary isn't uploaded to any maven repository yet.

    $ mvn clean package

Then you'll get `mqtt-websocket-java-1.0.0.jar` under the `target` directory.


# How to use
Here is a sample code to use `MqttWebSocketAsyncClient`.

Do NOT use `MqttClient` as it always uses Paho's default async client `MqttAsyncClient`.

      // Plain MQTT
      // final String uriString = "tcp://your-mqtt-broker:1883";

      // MQTT over WebSocket
      final String uriString = "wss://your-ws-broker/mqtt";

      // Credentials
      final String clientId = "your-client-id";
      final String userName = "your-user-name";
      final String password = "your-password";

      final IMqttAsyncClient client = new MqttWebSocketAsyncClient(
      		uriString, clientId, new MemoryPersistence());
      final MqttConnectOptions options = new MqttConnectOptions();
      options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
      options.setCleanSession(true);
      options.setUserName(userName);
      options.setPassword(password.toCharArray());
      client.connect(options);
      client.setCallback(new MqttCallback() {

      	@Override
      	public void messageArrived(String topic, MqttMessage message)
      			throws Exception {
      	}

      	@Override
      	public void deliveryComplete(IMqttDeliveryToken token) {
      	}

      	@Override
      	public void connectionLost(Throwable cause) {
      	}
      });


# Source Code License

All program source codes are available under the MIT style License.

Copyright (c) 2014 Inventit Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
