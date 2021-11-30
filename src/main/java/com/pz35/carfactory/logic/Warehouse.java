package com.pz35.carfactory.logic;


import com.pz35.carfactory.entities.CarDetail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse<T extends CarDetail> {
    private Boolean changed;

    private Integer maxSize;
    private final List<T> storage = Collections.synchronizedList(new ArrayList<>());



    public Warehouse(int maxSize) {
        this.maxSize = maxSize;
    }

    synchronized T get() {
        return storage.remove(0);
    }

    synchronized void add(T particle) {
        storage.add(particle);
    }

    boolean tryAdd() {
        return storage.size() < maxSize;
    }

    boolean tryGet() {
        return storage.size() > 0;
    }

    synchronized Boolean changedSinceLastCheck() {
        Boolean res = changed;
        changed = false;
        return res;
    }

    public List<T> getWarehouse() {
        return storage;
    }
}
