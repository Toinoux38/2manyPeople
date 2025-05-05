package com.citybuilder.modelBis;


import com.citybuilder.modelBis.purchasable.Purchasable;
import com.sun.prism.Texture;

import java.awt.*;

public class Cell {
    protected Boolean isWater;
    protected Point location;
    protected Purchasable content;

    public Cell(Boolean isWater, Point location) {
        this.isWater = isWater;
        this.location = location;
        this.content = null;
    }


    public Boolean getIsWater() {
        return isWater;
    }
    public Point getLocation() {
        return location;
    }
    public Purchasable getContent() {
        return content;
    }

    public void setContent(Purchasable content) {
        this.content = content;
    }
}
