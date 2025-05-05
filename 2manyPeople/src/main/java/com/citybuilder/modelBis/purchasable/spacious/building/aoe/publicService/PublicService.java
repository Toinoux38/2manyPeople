package com.citybuilder.modelBis.purchasable.spacious.building.aoe.publicService;


import com.citybuilder.modelBis.purchasable.spacious.building.aoe.BuildingWithAOE;

import java.awt.*;

public class PublicService extends BuildingWithAOE {
    public PublicService() {
        super();
        this.radius = 10;
        this.price = 15000;
        this.size = new Dimension(2, 2);
    }
}
