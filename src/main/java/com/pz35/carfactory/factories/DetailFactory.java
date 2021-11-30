package com.pz35.carfactory.factories;

import com.pz35.carfactory.entities.CarDetail;
import com.pz35.carfactory.logger.Logger;

public interface DetailFactory {
    CarDetail get();
    default void log(CarDetail carDetail){
        Logger.getInstance().writeLog("Car Detail " + carDetail.getClass().getSimpleName() +
                " " + carDetail.getId() +" created.");
    }
}
