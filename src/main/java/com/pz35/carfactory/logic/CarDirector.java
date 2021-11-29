package com.pz35.carfactory.logic;

import com.pz35.carfactory.entities.Accessory;
import com.pz35.carfactory.entities.Car;
import com.pz35.carfactory.entities.CarBody;
import com.pz35.carfactory.entities.Engine;
import com.pz35.carfactory.threadpool.ThreadPool;

public class CarDirector {
    private Warehouse<Accessory> accessoryStorage;
    private Warehouse<Engine> engineStorage;
    private Warehouse<CarBody> bodyStorage;

    private CarWarehouse carStorage;

    private boolean shouldStop = false;
    private long waitTime;
    private int generatedParticlesCount = 0;
    private boolean first = true;

    private static ThreadPool threadPool;
    private class CreateCar implements Runnable {
        public void run() {
            if (accessoryStorage.tryGet() &&
                    engineStorage.tryGet() &&
                    bodyStorage.tryGet()) {
                Car car = Car.builder()
                        .engine(engineStorage.get())
                        .accessory(accessoryStorage.get())
                        .body(bodyStorage.get())
                        .build();
                carStorage.addCar(car);
            }
        }
    }

    public CarDirector(Warehouse<Accessory> aw,
                       Warehouse<Engine> ew,
                       Warehouse<CarBody> bw,
                       CarWarehouse cw,
                       long waitTime,
                       ThreadPool tp) {
        accessoryStorage = aw;
        engineStorage = ew;
        bodyStorage = bw;
        carStorage = cw;

        this.waitTime = waitTime;

        threadPool = tp;
    }

    static public void init()
    {
        threadPool.start();
    }
    public void run() {
        while (!shouldStop) {
            while (!accessoryStorage.tryGet() ||
                    !engineStorage.tryGet() ||
                    !bodyStorage.tryGet())
            {
                ;
            }
            Runnable r = new CreateCar();
            generatedParticlesCount++;
            threadPool.enqueue(r);

            synchronized (Thread.currentThread()) {
                try {
                    Thread.currentThread().wait(waitTime);
                } catch (InterruptedException e) {
                    shouldStop = true;
                }
            }
        }
    }

    public void terminate() {
        shouldStop = true;
        threadPool.setShouldStop();
        threadPool.interrupt();
        Thread.currentThread().interrupt();
        threadPool.dequeue();
    }
    public int getNumberOfCreatedParticles() { return generatedParticlesCount; }

}
