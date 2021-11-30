package com.pz35.carfactory.controller;

import com.pz35.carfactory.service.Service;
import com.pz35.carfactory.view.View;
import javafx.collections.FXCollections;

public class WelcomeFormController extends View {

    private final Service service = Service.getInstance();
    long waitTime = 500;
    Boolean shouldClose = false;
    ViewThread th;

    public WelcomeFormController() {
        th = new ViewThread();
        th.start();
    }

    @Override
    public void setEngineFactorySpeed() {
        int value = engineSpeed.getValue();
        service.setEngineFactorySpeed(5000 / value);
    }

    @Override
    public void setAccessoryFactorySpeed() {
        int value = accessorySpeed.getValue();
        service.setAccessoryFactorySpeed(5000 / value);
    }

    @Override
    public void setBodyFactorySpeed() {
        int value = shapeSpeed.getValue();
        service.setBodyFactorySpeed(5000 / value);
    }

    @Override
    public void setDealerSpeed() {
        int value = shapeSpeed.getValue();
        service.setDealerSpeed(5000 / value);
    }

    public void terminate() {
        service.terminate();
        th.terminate();
        System.exit(0);
    }


    class ViewThread extends Thread {
        public void run() {
            while (!shouldClose) {
                engineList.clear();
                engineList.addAll(FXCollections.observableArrayList(service.getEngines()));
                bodyList.clear();
                bodyList.addAll(FXCollections.observableArrayList(service.getBodies()));
                accessoryList.clear();
                accessoryList.addAll(FXCollections.observableArrayList(service.getAccessories()));
                carList.clear();
                carList.addAll(FXCollections.observableArrayList(service.getCars()));
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(waitTime);
                    } catch (InterruptedException e) {
                        shouldClose = true;
                    }
                }
            }
        }

        void terminate() {
            shouldClose = true;
        }
    }
}
