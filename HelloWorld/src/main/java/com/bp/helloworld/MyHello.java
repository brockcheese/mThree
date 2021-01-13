/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.helloworld;

import java.util.Scanner;

/**
 *
 * @author BrockPace
 */
public class MyHello {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MyHello m = new MyHello();
        m.setName(scan.next());
        System.out.println("Well, hello there " + m.getName() );
    }
    
}
