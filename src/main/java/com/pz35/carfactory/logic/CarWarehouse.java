package com.pz35.carfactory.logic;


import com.pz35.carfactory.entities.Car;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    }

    public Car getCar(Integer id){
        return carList.stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car " + id + " was not found"));
    }
}
