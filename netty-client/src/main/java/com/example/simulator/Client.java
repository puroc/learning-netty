package com.example.simulator;

public interface Client {

    boolean connect();

    void send(String threadName);
}
