package com.pz35.carfactory.logic;

public class AppRunner extends Thread {
    private AppInitializer appInitializer;

    public AppRunner() {
        appInitializer = new AppInitializer(3000, 20, 2, 2,1);
    }

    public void run() {
        for (CarDirector d : appInitializer.getCarDirectors()) {
            d.start();
        }
        for (DetailProvider d : appInitializer.getAccessoryProviders()) {
            d.start();
        }
        for (DetailProvider d : appInitializer.getEngineProviders()) {
            d.start();
        }
        for (DetailProvider d : appInitializer.getBodyProviders()) {
            d.start();
        }
        for (Dealer d : appInitializer.getDealers()) {
            d.start();
        }
    }

    public void terminate() {

        for(DetailProvider p : appInitializer.getBodyProviders())
        {
            p.terminate();
        }
        for(DetailProvider p : appInitializer.getEngineProviders())
        {
            p.terminate();
        }
        for(DetailProvider p : appInitializer.getAccessoryProviders())
        {
            p.terminate();
        }
        for(CarDirector m : appInitializer.getCarDirectors())
        {
            m.terminate();
        }
        for (Dealer d : appInitializer.getDealers()) {
            d.terminate();
        }
        try {
            for(DetailProvider p : appInitializer.getBodyProviders())
            {
                p.join();
            }
            for(DetailProvider p : appInitializer.getEngineProviders())
            {
                p.join();
            }
            for(DetailProvider p : appInitializer.getAccessoryProviders())
            {
                p.join();
            }
            for(CarDirector m : appInitializer.getCarDirectors())
            {
                m.join();
            }
            for (Dealer d : appInitializer.getDealers()) {
                d.join();
            }
        } catch (InterruptedException e) {

        }

    }

}
