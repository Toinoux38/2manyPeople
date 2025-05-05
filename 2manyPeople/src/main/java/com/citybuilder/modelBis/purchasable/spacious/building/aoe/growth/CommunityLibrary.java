package com.citybuilder.modelBis.purchasable.spacious.building.aoe.growth;

import java.awt.*;

public class CommunityLibrary extends Education{
    public CommunityLibrary(){
        super();
        this.name = "Community Library";
        this.price = 25000;
        this.maintainanceCost = 7000;
        this.growthBoost = 20;
        this.size = new Dimension(2, 3);
    }
}
