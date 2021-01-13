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
public class Employee extends Person implements Payable {
    private double salary;

    public Employee(double salary, String name, int age, Address address) {
        super(name, age, address);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" + super.toString() + "salary=" + salary + '}';
    }
    
    public double calculateWeeklyPay() {
        return (salary / 52);
    }
    
    public String getName() {
        return this.getName();
    }
    
}
