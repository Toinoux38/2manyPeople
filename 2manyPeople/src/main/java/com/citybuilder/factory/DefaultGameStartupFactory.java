package com.citybuilder.factory;

import com.citybuilder.CityBuilder;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.Loan;

import java.util.ArrayList;

public class DefaultGameStartupFactory implements GameStartupFactory {
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final int DEFAULT_MONEY = 10000;

    @Override
    public City createCity(String name, float hazardRate) {
        return new CityBuilder()
                .setName(name)
                .setMap(DEFAULT_WIDTH, DEFAULT_HEIGHT)
                .setLoans(new ArrayList<Loan>())
                .setHazardRate(hazardRate)
                .setMoney(DEFAULT_MONEY)
                .build();
    }
} 