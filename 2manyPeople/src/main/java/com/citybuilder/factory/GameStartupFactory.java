package com.citybuilder.factory;

import com.citybuilder.modelBis.City;

public interface GameStartupFactory {
    City createCity(String name, float hazardRate);
} 