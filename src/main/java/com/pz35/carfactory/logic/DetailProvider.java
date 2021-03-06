package com.pz35.carfactory.logic;


import com.pz35.carfactory.entities.CarDetail;
import com.pz35.carfactory.factories.DetailFactory;
import com.pz35.carfactory.logger.Logger;

public class DetailProvider<T extends CarDetail> extends Thread {
    private long waitTime;
    private boolean shouldStop = false;
    private Warehouse<T> warehouse;
    private DetailFactory factory;
    private int generatedParticlesCount = 0;
    public DetailProvider(Warehouse<T> warehouse, DetailFactory factory, long initialWaitTime) {
        this.warehouse = warehouse;
        this.factory = factory;
        waitTime = initialWaitTime;
    }
    void setWaitTime(long milliseconds) {
        waitTime = milliseconds;
    }
    public void run() {
        while (!shouldStop) {
            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait(waitTime);
                } catch (InterruptedException e) {
                    shouldStop = true;
                    System.out.println("Unexpected error");
                }
            }
            synchronized (warehouse) {
                if (warehouse.tryAdd()) {
                    T detail = (T) factory.get();
                    warehouse.add(detail);
                    generatedParticlesCount++;
                    Logger.getInstance().writeLog("Detail " + detail.getId() + " added to the warehouse by provider.");
                }
            }

        }
    }
    public int getNumberOfCreatedParticles() { return generatedParticlesCount; }
    public void terminate() {
        shouldStop = true;
        Thread.currentThread().interrupt();
    }
}
