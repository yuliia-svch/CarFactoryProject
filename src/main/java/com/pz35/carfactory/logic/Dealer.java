package com.pz35.carfactory.logic;

import com.pz35.carfactory.entities.Car;
import com.pz35.carfactory.logger.Logger;

import java.util.ArrayList;

public class Dealer extends Thread{
    int id;
    boolean shouldStop = false;
    long waitTime;
    CarWarehouse carWarehouse;
    ArrayList<Car> cars = new ArrayList<>();

    static int lastId = 0;
    synchronized static int getFreeId()
    {
        return ++lastId;
    }
    public Dealer(CarWarehouse cs,
                     long waitTime) {
        id = getFreeId();
        this.waitTime = waitTime;
        carWarehouse = cs;
        Logger.getInstance().writeLog("Dealer " + id + " created.");
    }

    public void run() {
        while (!shouldStop) {
            synchronized (Thread.currentThread()) {
                try {
                    while (!carWarehouse.tryGet()) {
                        ;
                    }
                    synchronized (carWarehouse) {
                        if (carWarehouse.tryGet()) {
                            cars.add(carWarehouse.getCar(0));
                        }
                    }
                    Thread.currentThread().wait(waitTime);
                } catch (InterruptedException e) {
                    shouldStop = true;
                }

            }

        }
    }

    public void terminate() {
        shouldStop = true;
        Thread.currentThread().interrupt();
    }

    void setWaitTime(long milliseconds) {
        this.waitTime = milliseconds;
    }
}
