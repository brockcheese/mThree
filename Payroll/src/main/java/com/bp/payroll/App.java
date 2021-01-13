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
public class App {
    public static void main(String[] args) {
        Address a = new Address("123 Spooner", "Quohog", "12345");
        Person p = new Person("Stewie", 2, a);
        PersonnelTest pt = new PersonnelTest();
        PayableTest ppt = new PayableTest();
        pt.add(p);
        
        System.out.println(p);
        
        Employee e = new Employee(50.6, "Peter", 50, a);
        Contractor c = new Contractor(true, 40.5, "Lois", 45, a);
        pt.add(e);
        pt.add(c);
        ppt.add(e);
        ppt.add(c);
        e.setSalary(60.9);
        c.setHourlyRate(50.8);
        
        System.out.println(e);
        System.out.println(c);
        pt.printPerson();
        ppt.printPaychecks();
    }
}
