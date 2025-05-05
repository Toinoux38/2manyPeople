package com.citybuilder.modelBis.purchasable.spacious.zone;

public class Commerce extends Zone {
    public Commerce() {
        this.name = "Commerce";
        this.TaxesIncome = 25 * this.populationValue * this.happinessValue;
    }
}
