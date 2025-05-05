package com.citybuilder.modelBis.purchasable.spacious.zone;

import com.citybuilder.modelBis.purchasable.spacious.SpaciousPurchasable;

import java.awt.*;

public class Zone extends SpaciousPurchasable {
    protected int growthValue;
    protected int happinessValue;
    protected int populationValue;
    protected int TaxesIncome;

    public Zone() {
        this.size = new Dimension(4, 4);
        this.price = 0;
        this.growthValue = 0;
        this.happinessValue = 0;
        this.populationValue = 15;

    }
}
