package com.example.simulator;

import com.example.msg.TestMsg;

public class Config {

    private static final Config CONFIG = new Config();

    private Config() {

    }

    public static Config getInstance() {
        return CONFIG;
    }

    private byte[] msg;

    {
        TestMsg testMsg = new TestMsg();
        testMsg.setBody("123".getBytes());
        msg = testMsg.getBytes();
//        msg = ("123"+System.getProperty("line.separator")).getBytes();
    }


    private int threadNum;

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }


    public byte[] getMsg() {
        return msg;
    }
}
