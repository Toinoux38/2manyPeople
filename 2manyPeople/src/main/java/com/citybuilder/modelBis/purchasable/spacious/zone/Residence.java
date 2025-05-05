package com.citybuilder.modelBis.purchasable.spacious.zone;

public class Residence extends Zone {
    public Residence() {
        this.name = "Residence";
        this.TaxesIncome = 15 * this.populationValue * this.happinessValue;
    }
}
