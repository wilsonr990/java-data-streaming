package com.datastreaming.model;

import java.time.Instant;

public class Event {
    private Instant timestamp;
    private Double x;
    private Integer y;

    public Event(long timestamp, Double x, Integer y) {
        this.timestamp = Instant.ofEpochMilli(timestamp);
        this.x = x;
        this.y = y;
    }

    public Instant getInstant() {
        return timestamp;
    }

    public Double getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

}
