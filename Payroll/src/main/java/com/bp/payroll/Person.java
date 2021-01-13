/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.payroll;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author BrockPace
 */
public class Person implements Comparable<Person> {
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    
    @Override
    public int compareTo(Person p) {
        return (age - p.getAge());
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", age=" + age + ", address=" + address + '}';
    }
    
}
