package com.citybuilder;

import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.Loan;

import java.util.List;

public class CityFactory {
    public City Factory(String name, Cell[][] map, List<Loan> loans, float hazardRate, int money){
        return new City(name,map,loans,hazardRate,money);
    }
}
