package com.citybuilder.factory;

import com.citybuilder.CityBuilder;
import com.citybuilder.modelBis.City;
import com.citybuilder.ui.CityConfigDialog;

import java.util.ArrayList;

public class CityConfigFactory {
    public static City createCityFromConfig() {
        CityConfigDialog dialog = new CityConfigDialog();
        return new CityBuilder()
            .setName(dialog.getCityName())
            .setMap(50, 50)
            .setHazardRate(dialog.getHazardRate())
            .setMoney(10000)
            .setLoans(new ArrayList<>())
            .build();
    }
} 