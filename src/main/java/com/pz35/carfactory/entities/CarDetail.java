package com.pz35.carfactory.entities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CarDetail {

    protected final LocalTime creationTime;
    protected Integer id;

    protected static Integer lastId = 1;

    protected CarDetail() {
        creationTime = LocalTime.now();
        id = getAvailableId();

    }

    private synchronized static Integer getAvailableId() {
        synchronized (lastId) {
            return lastId++;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getCreationTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateFormatter.format(creationTime);
    }
}
