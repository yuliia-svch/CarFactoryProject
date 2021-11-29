package com.pz35.carfactory.logic;

import com.pz35.carfactory.entities.Accessory;
import com.pz35.carfactory.entities.Car;
import com.pz35.carfactory.entities.CarBody;
import com.pz35.carfactory.entities.Engine;
import com.pz35.carfactory.threadpool.ThreadPool;

public class CarDirector {
    Warehouse<Accessory> accessoryStorage;
    Warehouse<Engine> engineStorage;
    Warehouse<CarBody> bodyStorage;

    CarWarehouse carStorage;

    boolean shouldStop = false;
    long waitTime;
    int generatedParticlesCount = 0;
    boolean first = true;
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
    }



}
