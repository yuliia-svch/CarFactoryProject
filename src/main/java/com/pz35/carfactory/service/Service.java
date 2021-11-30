package com.pz35.carfactory.service;

import com.pz35.carfactory.configurator.Configurator;
import com.pz35.carfactory.entities.Accessory;
import com.pz35.carfactory.entities.Car;
import com.pz35.carfactory.entities.CarBody;
import com.pz35.carfactory.entities.Engine;
import com.pz35.carfactory.logger.Logger;
import com.pz35.carfactory.logic.AppInitializer;
import com.pz35.carfactory.logic.AppRunner;

import java.util.List;

public class Service {
    private static Service instance;

    private AppRunner appRunner;
    private final Configurator configurator = new Configurator();

    private Service() {

        appRunner = new AppRunner(new AppInitializer(3000, configurator.GetStorageSize(), configurator.GetDealersCount(), configurator.GetProvidersCount(), configurator.GetCollectorsCount()));
        appRunner.start();
        Logger.getInstance().writeLog("App started. Runner initialized.");
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public List<Engine> getEngines() {
        return appRunner.getAppInitializer().getEngineWarehouse().getWarehouse();
    }

    public List<Accessory> getAccessories() {
        return appRunner.getAppInitializer().getAccessoryWarehouse().getWarehouse();
    }

    public List<CarBody> getBodies() {
        return appRunner.getAppInitializer().getCarBodyWarehouse().getWarehouse();
    }

    public List<Car> getCars() {
        return appRunner.getAppInitializer().getCarWarehouse().getCarList();
    }

    public void setEngineFactorySpeed(int i) {
        appRunner.getAppInitializer().setEngineProviderWaitTime(i);
    }

    public void setAccessoryFactorySpeed(int i) {
        appRunner.getAppInitializer().setAccessoryProviderWaitTime(i);
    }

    public void setBodyFactorySpeed(int i) {
        appRunner.getAppInitializer().setCarBodyProviderWaitTime(i);
    }

    public void terminate() {
        appRunner.terminate();
    }

    public void setDealerSpeed(int i) {
        appRunner.getAppInitializer().setDealersWaitTime(i);
    }

    public int getEnginesCount() {
        return appRunner.getAppInitializer().getEngineProviderCreatedParticlesCount();
    }

    public int getAccessoryCount() {
        return appRunner.getAppInitializer().getAccessoryProviderCreatedParticlesCount();
    }

    public int getBodyCount() {
        return appRunner.getAppInitializer().getCarBodyProviderCreatedParticlesCount();
    }

    public int getCarCount() {
        return appRunner.getAppInitializer().getCarsCount();
    }
}
