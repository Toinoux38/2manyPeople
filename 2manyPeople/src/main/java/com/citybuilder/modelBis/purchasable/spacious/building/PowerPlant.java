package com.citybuilder.modelBis.purchasable.spacious.building;

import java.awt.*;

public class PowerPlant extends RessourcesProvider{
    public PowerPlant(){
        this.name = "Power Plant";
        this.price = 15000;
        this.maintainanceCost = 2500;
        this.efficacity = 1;
        this.size = new Dimension(2, 2);
        this.nbCaseProvided = 10;
    }
}
