package com.citybuilder.modelBis.purchasable.spacious.building.aoe;


import com.citybuilder.modelBis.interfaces.IConsumeElectricity;
import com.citybuilder.modelBis.interfaces.IConsumeWater;
import com.citybuilder.modelBis.purchasable.spacious.building.Building;

public class BuildingWithAOE extends Building implements IConsumeWater, IConsumeElectricity {
    protected int radius;

    public BuildingWithAOE() {
        super();
        this.price = 20000;
    }
}
