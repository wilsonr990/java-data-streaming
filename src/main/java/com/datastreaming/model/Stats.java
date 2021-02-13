package com.datastreaming.model;

import java.math.BigInteger;

public class Stats {
    private Integer total;
    private Double sumX;
    private Double avgX;
    private BigInteger sumY;
    private Double avgY;

    public Stats() {
        this.total = 0;
        this.sumX = 0.0;
        this.avgX = 0.0;
        this.sumY = BigInteger.valueOf(0);
        this.avgY = 0.0;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getSumX() {
        return sumX;
    }

    public void setSumX(Double sumX) {
        this.sumX = sumX;
    }

    public Double getAvgX() {
        return avgX;
    }

    public void setAvgX(Double avgX) {
        this.avgX = avgX;
    }

    public BigInteger getSumY() {
        return sumY;
    }

    public void setSumY(BigInteger sumY) {
        this.sumY = sumY;
    }

    public Double getAvgY() {
        return avgY;
    }

    public void setAvgY(Double avgY) {
        this.avgY = avgY;
    }
}
