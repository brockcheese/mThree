/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.morestatecapitals;

/**
 *
 * @author BrockPace
 */
public class Capital {
    private final String name;
    private final int population;
    private final double squareMileage;

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public double getSquareMileage() {
        return squareMileage;
    }

    public Capital(String name, int population, double squareMileage) {
        this.name = name;
        this.population = population;
        this.squareMileage = squareMileage;
    }
    
    
}
