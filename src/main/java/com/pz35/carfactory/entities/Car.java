package com.pz35.carfactory.entities;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public class Car {

    private Engine engine;
    private CarBody body;
    private Accessory accessory;

    private Integer id;
    private LocalDateTime creationTime;

    private static Integer lastId = 1;

    public static Integer getAvailableId() {
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

    public String getType() {
        return "Car";
    }
}
