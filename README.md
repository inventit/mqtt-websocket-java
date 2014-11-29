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

# Dependencies

The following libraries are requried as well.

| GroupId                   | ArtifactId     | Version       |
|---------------------------|----------------|---------------|
|org.eclipse.jetty          |jetty-io        |9.2.1.v20140609|
|org.eclipse.jetty          |jetty-util      |9.2.1.v20140609|
|org.eclipse.jetty.websocket|websocket-api   |9.2.1.v20140609|
|org.eclipse.jetty.websocket|websocket-common|9.2.1.v20140609|

## maven pom.xml settings

Adds the following elements to your pom.xml if you're using maven.

```
  <dependency>
    <groupId>io.inventit.dev</groupId>
    <artifactId>mqtt-websocket-java</artifactId>
    <version>1.0.1</version>
  </dependency>
  <dependency>
    <groupId>org.eclipse.jetty.websocket</groupId>
    <artifactId>websocket-client</artifactId>
    <version>9.2.5.v20141112</version>
  </dependency>
  <dependency>
    <groupId>org.eclipse.paho</groupId>
    <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
    <version>1.0.0</version>
  </dependency>
```

# How to build

Install maven then run the following command on the project root directory.

Note that Paho Java library is included in this project as the binary isn't uploaded to any maven repository yet.

    $ mvn clean package

Then you'll get `mqtt-websocket-java-<version>.jar` under the `target` directory.

# How to use
You can use this library as the same manner as Paho's library but use `MqttWebSocketAsyncClient` instead of Paho's classes such as `MqttClient` and `MqttAsyncClient`.

The `MqttWebSocketAsyncClient` supports the following URI schimes:

1. `ws://<host>:<port>`  ... for a plain WebSocket
1. `wss://<host>:<port>` ... for a WebSocket with SSL/TLS
1. `tcp://<host>:<port>` ... for a plain TCP MQTT socket
1. `ssl://<host>:<port>` ... for a secure SSL/TLS MQTT socket

Here is sample code to use `MqttWebSocketAsyncClient`.

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

# Change History

[1.0.1 : November 29, 2014](https://github.com/inventit/mqtt-websocket-java/releases/tag/1.0.1)

* Upgrades Jetty 9 library
* Adds a new factory method for WebSocketNetworkModule instance
* Adds new constructors with a new paramter for specifying the logger name to MqttWebSocketAsyncClient
* Releases a JDK1.6 class version (50) jar as well in order for Android app to include this library (**Note that Jetty 9 itself doesn't support Android and JDK 1.6**)

[1.0.0 : July 30, 2014](https://github.com/inventit/mqtt-websocket-java/releases/tag/1.0.0)

* Initial
