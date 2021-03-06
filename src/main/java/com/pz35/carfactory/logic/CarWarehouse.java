package com.pz35.carfactory.logic;


import com.pz35.carfactory.entities.Car;
import com.pz35.carfactory.logger.Logger;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
public class CarWarehouse {
    private static CarWarehouse INSTANCE = null;

    private List<Car> carList;
    private int size;
    private boolean isAvailable;
    private boolean isFull;


    private CarWarehouse(){
        carList = new ArrayList<>();
    }

    public static CarWarehouse getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new CarWarehouse();
        }
        return INSTANCE;
    }

    public void addCar(Car car){
        carList.add(car);
        Logger.getInstance().writeLog("Car " + car.getId() + " added to the warehouse.");
    }

    public Car getCar(Integer id){
        Optional<Car> objCar = Optional.ofNullable(carList.stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car " + id + " was not found")));

        carList.remove(objCar.get());
        Logger.getInstance().writeLog("Car " + objCar.get().getId() + " removed from the warehouse.");
        return objCar.get();
    }
    public boolean tryGet() {
        return carList.size() > 0;
    }
}
