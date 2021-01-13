/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.payroll;

/**
 *
 * @author BrockPace
 */
public class Contractor extends Person implements Payable{
    private boolean permanent;
    private double hourlyRate;

    public Contractor(boolean permanent, double hourlyRate, String name, 
            int age, Address address) {
        super(name, age, address);
        this.permanent = permanent;
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "Contractor{" + super.toString() + "permanent=" + permanent + ", hourlyRate=" + hourlyRate + '}';
    }
    
    public double calculateWeeklyPay() {
        return (hourlyRate * 40);
    }
    
    public String getName() {
        return this.getName();
    }
    
}
