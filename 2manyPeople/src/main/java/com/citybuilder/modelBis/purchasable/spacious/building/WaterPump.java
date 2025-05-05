package com.citybuilder.modelBis.purchasable.spacious.building;

import com.citybuilder.modelBis.interfaces.IConsumeElectricity;

import java.awt.*;

public class WaterPump extends RessourcesProvider implements IConsumeElectricity {
    public WaterPump(){
        this.name = "Water Pump";
        this.price = 15000;
        this.maintainanceCost = 2500;
        this.efficacity = 1;
        this.size = new Dimension(2, 2);
        this.nbCaseProvided = 10;
    }
}
