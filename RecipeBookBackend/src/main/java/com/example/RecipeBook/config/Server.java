package com.example.RecipeBook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class Server {
    @Value("${server.address:192.168.0.8}")
    public final String serverAddress = setServerAddress();

    public String setServerAddress() {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            return "192.168.0.8";
        }
    }
}
