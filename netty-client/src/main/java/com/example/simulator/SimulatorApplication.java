package com.example.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimulatorApplication {

    public static void main(String[] args) {
//        Counter.getInstance().start();
        for (int i = 0; i < args.length; i++) {
            if ("threadNum".equalsIgnoreCase(args[i])){
                Config.getInstance().setThreadNum(Integer.parseInt(args[i+1]));
            }
        }
        SpringApplication.run(SimulatorApplication.class, args);
    }

}

