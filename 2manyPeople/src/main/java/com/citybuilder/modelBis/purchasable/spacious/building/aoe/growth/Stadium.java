package com.citybuilder.modelBis.purchasable.spacious.building.aoe.growth;

import java.awt.*;

public class Stadium extends Entertainement{
    public Stadium(){
        super();
        this.name = "Stadium";
        this.maintainanceCost = 5000;
        this.price = 50000;
        this.radius = 15;
        this.growthBoost = 15;
        this.size = new Dimension(3,4);
    }
}
