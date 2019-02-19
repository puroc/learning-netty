package com.example.simulator.io;

import com.example.simulator.Client;
import com.example.simulator.Config;
import com.example.simulator.Counter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Component
public class SocketClient implements Client {

    @Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    @Value("${debug}")
    private String debug;

    private Socket client;
    private OutputStream writer;

    @Override
    public boolean connect() {
        try {
            client = new Socket(host, Integer.parseInt(port));
            writer = client.getOutputStream();
            for (int i = 0; i < Config.getInstance().getThreadNum(); i++) {
                this.send("Thread-" + i);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void send(String threadName) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if ("true" .equalsIgnoreCase(debug)) {
                        sendDebugMsg();
                    } else {
                        sendMsg4Ever();
                    }
                }
            });
            thread.setName(threadName);
            thread.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void sendMsg4Ever() {
        while (true) {
            try {
                sendMsg();
                Counter.getInstance().add();
                Thread.sleep(1);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private void sendDebugMsg() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("please input");
                String str = scan.next();
                if ("1" .equalsIgnoreCase(str)) {
                    sendMsg();
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsg() throws IOException {
        writer.write(Config.getInstance().getMsg());
        writer.flush();
    }
}