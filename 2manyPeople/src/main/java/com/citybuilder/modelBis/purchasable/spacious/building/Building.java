package com.citybuilder.modelBis.purchasable.spacious.building;


import com.citybuilder.modelBis.purchasable.spacious.SpaciousPurchasable;

public class Building extends SpaciousPurchasable {
    protected int maintainanceCost;
    protected float efficacity;

    public Building() {
        this.efficacity = 1.0f;
    }
}
