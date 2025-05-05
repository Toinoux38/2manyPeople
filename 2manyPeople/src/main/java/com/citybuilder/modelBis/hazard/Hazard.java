package com.citybuilder.modelBis.hazard;

public abstract class Hazard {
    protected String name;

    public Hazard(String name) {
        this.name = name;
    }

    public void happened(){}
}
