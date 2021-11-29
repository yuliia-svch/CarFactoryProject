package com.pz35.carfactory.threadpool;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool extends Thread{
    private int size;
    private ArrayList<Thread> threads;
    private BlockingQueue<Runnable> threadsQueue;
    ArrayList<Integer> availableThreads = new ArrayList<>();
    boolean shouldStop = false;

    public void setShouldStop() {
        shouldStop = true;
    }

    public ThreadPool() {
        this.size = 5;
        this.threadsQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<Thread>(size);
        for (int i = 0; i < size; i++) {
            availableThreads.add(i);
            threads.add(null);
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void terminate() {
        shouldStop = true;
    }
}
