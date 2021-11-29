package com.pz35.carfactory.factories;

import com.pz35.carfactory.entities.CarBody;
import com.pz35.carfactory.entities.CarDetail;

public class CarBodyFactory implements DetailFactory {
    public CarDetail get() {
        return new CarBody();
    }
}
