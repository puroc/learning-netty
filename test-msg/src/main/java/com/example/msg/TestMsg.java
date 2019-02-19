package com.example.msg;

public class TestMsg {

    private int len;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public static byte[] intToBytes(int n) {
        byte[] b = new byte[4];

        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (n >> (24 - i * 8));

        }
        return b;
    }

    private byte[] body;

    public byte[] getBytes() {
        this.setLen(this.getBody().length+4);
        byte[] lenBuf = intToBytes(this.getLen());
        byte[] bodyBuf = new byte[this.getBody().length];
        byte[] buf = new byte[lenBuf.length+bodyBuf.length];
        int position = 0;
        System.arraycopy(lenBuf, 0, buf, position, lenBuf.length);
        position += lenBuf.length;
        System.arraycopy(this.getBody(), 0, buf, position, bodyBuf.length);
        return buf;
    }

}
