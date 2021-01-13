/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.payroll;

import java.util.Arrays;

/**
 *
 * @author BrockPace
 */
public class PayableTest {
    private Payable[] people;
    private int size = 0;
    
    public void add(Payable p) {
        size++;
        if (size == 1) people = new Payable[size];
        else {
            Payable[] temp = new Payable[size];
            for (int i = 0; i < size - 1; i++) {
                temp[i] = people[i];
            }
            people = temp;
        }
        
        people[size - 1] = p;
    }
    
    public void printPaychecks() {
        for(int i = 0; i < people.length ; i++) {
            System.out.println(people[i].calculateWeeklyPay());
        }
    }
    
}
