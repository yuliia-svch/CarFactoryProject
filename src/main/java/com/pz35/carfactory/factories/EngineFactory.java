package com.pz35.carfactory.factories;

import com.pz35.carfactory.entities.CarDetail;
import com.pz35.carfactory.entities.Engine;

public class EngineFactory implements DetailFactory {
    public CarDetail get() {
        return new Engine();
    }
}
