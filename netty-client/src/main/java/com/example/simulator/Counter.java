package com.example.simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private static Logger log = LoggerFactory.getLogger(Counter.class);

    private static final Counter COUNTER = new Counter();

    private Counter() {

    }

    public static Counter getInstance() {
        return COUNTER;
    }

    private AtomicLong num = new AtomicLong();

    private Timer timer = new Timer();

    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.error("num:" + num.get());
                num.set(0);
            }
        }, 1000, 1000);
    }

    public void add() {
        num.incrementAndGet();
    }


}
