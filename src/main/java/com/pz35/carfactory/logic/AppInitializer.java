package com.pz35.carfactory.logic;

import com.pz35.carfactory.entities.Accessory;
import com.pz35.carfactory.entities.CarBody;
import com.pz35.carfactory.entities.Engine;
import com.pz35.carfactory.factories.AccessoryFactory;
import com.pz35.carfactory.factories.CarBodyFactory;
import com.pz35.carfactory.factories.EngineFactory;
import com.pz35.carfactory.threadpool.ThreadPool;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AppInitializer {

    private int dealersCount = 1;
    private int providersCount = 1;
    private int directorsCount = 1;
    private Warehouse<Accessory> accessoryWarehouse;
    private Warehouse<Engine> engineWarehouse;
    private Warehouse<CarBody> carBodyWarehouse;
    private CarWarehouse carWarehouse;

    private List<DetailProvider<Engine>> engineProviders;
    private List<DetailProvider<Accessory>> accessoryProviders;
    private List<DetailProvider<CarBody>> bodyProviders;

    private List<CarDirector> carDirectors;

    private ArrayList<Dealer> dealers = new ArrayList<>();

    ThreadPool threadpool;

    public AppInitializer(int initialTime,
                          int maxSize,
                          int dealersCount,
                          int providersCount,
                          int directorsCount) {
        accessoryWarehouse = new Warehouse<>(maxSize);
        engineWarehouse = new Warehouse<>(maxSize);
        carBodyWarehouse = new Warehouse<>(maxSize);
        carWarehouse = CarWarehouse.getINSTANCE();
        this.dealersCount = dealersCount;
        this.directorsCount = directorsCount;
        this.providersCount = providersCount;
        threadpool = new ThreadPool();

        accessoryProviders = new ArrayList<>();
        engineProviders = new ArrayList<>();
        bodyProviders = new ArrayList<>();
        carDirectors = new ArrayList<>();
        for (int i = 0; i < dealersCount; i++) {
            dealers.add(new Dealer(carWarehouse, initialTime));
        }
        for (int i = 0; i < providersCount; i++)
        {
            accessoryProviders.add(new DetailProvider<>(accessoryWarehouse, new AccessoryFactory(), initialTime));
            engineProviders.add(new DetailProvider<>(engineWarehouse, new EngineFactory(), initialTime));
            bodyProviders.add(new DetailProvider<>(carBodyWarehouse, new CarBodyFactory(), initialTime));
        }
        for(int i = 0; i < this.directorsCount; i++)
        {
            carDirectors.add(new CarDirector(accessoryWarehouse,
                    engineWarehouse,
                    carBodyWarehouse,
                    carWarehouse,
                    initialTime,
                    threadpool));
        }
        CarDirector.init();
    }

    public void setAccessoryProviderWaitTime(long milliseconds) {
        for (DetailProvider accessoryProducer : accessoryProviders) {
            accessoryProducer.setWaitTime(milliseconds);
        }
    }

    public void setEngineProviderWaitTime(long milliseconds) {
        for (DetailProvider engineProducer : engineProviders) {
            engineProducer.setWaitTime(milliseconds);
        }
    }

    public void setCarBodyProviderWaitTime(long milliseconds) {
        for (DetailProvider bodyProducer : bodyProviders) {
            bodyProducer.setWaitTime(milliseconds);
        }
    }

    public int getAccessoryProviderCreatedParticlesCount() {
        int all = 0;
        for(DetailProvider p : accessoryProviders)
            all += p.getNumberOfCreatedParticles();
        return all;
    }

    public int getEngineProviderCreatedParticlesCount() {
        int all = 0;
        for(DetailProvider p : engineProviders)
            all += p.getNumberOfCreatedParticles();
        return all;
    }

    public int getCarBodyProviderCreatedParticlesCount() {
        int all = 0;
        for(DetailProvider p : bodyProviders)
            all += p.getNumberOfCreatedParticles();
        return all;
    }

    public int getCarsCount() {
        int all = 0;
        for(CarDirector cm : carDirectors) {
            all+= cm.getNumberOfCreatedParticles();
        }
        return all;
    }

    public void setDealersWaitTime(long milliseconds) {
        for (Dealer d : dealers) {
            d.setWaitTime(milliseconds);
        }
    }
}
