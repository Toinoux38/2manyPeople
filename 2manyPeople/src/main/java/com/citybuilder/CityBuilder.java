package com.citybuilder;

import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.Loan;

import java.awt.*;
import java.util.List;

public class CityBuilder {
    protected String name;
    protected Cell[][] map;
    protected List<Loan> loans;
    protected float hazardRate;
    protected int money;

    public CityBuilder setName(String name){
        this.name = name;
        return this;
    }
    public CityBuilder setMap(int width,int height){
        map = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = new Cell(false, new Point(x, y)); // Default to non-water
            }
        }
        return this;
    }
    public CityBuilder setLoans(List<Loan> loans){
        this.loans = loans;
        return this;
    }
    public CityBuilder setHazardRate(float hazardRate){
        this.hazardRate = hazardRate;
        return this;
    }
    public CityBuilder setMoney(int money){
        this.money = money;
        return this;
    }
    public City build(){
        return new CityFactory().Factory(name, map, loans, hazardRate, money);
    }
}
