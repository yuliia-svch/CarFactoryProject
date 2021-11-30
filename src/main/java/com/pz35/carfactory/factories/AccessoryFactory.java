package com.pz35.carfactory.factories;

import com.pz35.carfactory.entities.Accessory;
import com.pz35.carfactory.entities.CarDetail;

public class AccessoryFactory implements DetailFactory {
    public CarDetail get() {
        CarDetail accessory = new Accessory();
        log(accessory);
        return accessory;
    }
}
