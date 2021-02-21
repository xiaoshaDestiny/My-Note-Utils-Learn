package com.learn.juc.part3;

/**
 * @author xu.rb
 * @since 2021-02-08 21:04
 */
public class NoVisibility {

    private boolean ready;

    private int number;

    public synchronized boolean isReady() {
        return ready;
    }

    public synchronized void setReady(boolean ready) {
        this.ready = ready;
    }

    public synchronized int getNumber() {
        return number;
    }

    public synchronized void setNumber(int number) {
        this.number = number;
    }
}
