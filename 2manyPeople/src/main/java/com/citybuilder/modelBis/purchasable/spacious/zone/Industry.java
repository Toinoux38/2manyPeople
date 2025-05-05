package com.citybuilder.modelBis.purchasable.spacious.zone;

public class Industry extends Zone {
    public Industry() {
        this.name = "Industry";
        this.TaxesIncome = 20 * this.populationValue * this.happinessValue;
    }
}
