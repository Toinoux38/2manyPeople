package com.citybuilder.modelBis.purchasable.spacious.building.aoe.growth;

import java.awt.*;

public class HighSchool extends Education{
    public HighSchool(){
        super();
        this.name = "High School";
        this.price = 25000;
        this.maintainanceCost = 7000;
        this.growthBoost = 20;
        this.size = new Dimension(2, 1);
    }
}
