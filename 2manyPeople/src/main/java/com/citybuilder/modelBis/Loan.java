package com.citybuilder.modelBis;

public class Loan {
    protected String name;
    protected int amount;
    protected int duration;
    protected int interest;

    public Loan(String name, int amount, int duration, int interest) {
        this.name = name;
        this.amount = amount;
        this.duration = duration;
        this.interest = interest;
    }
}
