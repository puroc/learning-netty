package com.example.simulator;

import com.example.simulator.io.SocketClient;
import com.example.simulator.netty.TcpNettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Worker implements CommandLineRunner {

    @Autowired
    private TcpNettyClient tcpNettyClient;

    @Autowired
    private SocketClient socketClient;

    @Override
    public void run(String... args) throws Exception {
        tcpNettyClient.connect();
//        socketClient.connect();

    }
}
