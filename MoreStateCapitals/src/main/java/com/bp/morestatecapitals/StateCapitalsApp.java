/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.morestatecapitals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author BrockPace
 */
public class StateCapitalsApp {
    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Capital> states = new HashMap<>();
        Scanner sc = new Scanner(new BufferedReader(
                new FileReader("MoreStateCapitals.txt")));
        int countLines = 0;
        while(sc.hasNextLine()) {
            countLines++;
            String currentLine = sc.nextLine();
            String[] fields = currentLine.split("::", 4);
            Capital c = new Capital(fields[1], Integer.parseInt(fields[2]), 
                    Double.parseDouble(fields[3]));
            states.put(fields[0], c);
        }
        System.out.println(countLines + " STATE/CAPITAL PAIRS LOADED.");
        System.out.println("==============================");
        Set<String> keys = states.keySet();
        for (String k : keys) {
            Capital c = states.get(k);
            System.out.println(k + " - " + c.getName() + " | Pop: " 
                    + c.getPopulation() + " | Area: " 
                    + c.getSquareMileage() + " sq mi");
        }
        
        System.out.println();
        Scanner inputScan = new Scanner(System.in);
        System.out.print("Please enter the lower limit for capital city "
                + "population: ");
        System.out.flush();
        int lowerLimit = inputScan.nextInt();
        System.out.println(lowerLimit);
        
        System.out.println();
        System.out.println("LISTING CAPITALS WITH POPULATIONS GREATER THAN " + 
                lowerLimit + ":");
        System.out.println();
        for (String k : keys) {
            Capital c = states.get(k);
            if (c.getPopulation() > lowerLimit) {
                System.out.println(k + " - " + c.getName() + " | Pop: " 
                    + c.getPopulation() + " | Area: " 
                    + c.getSquareMileage() + " sq mi");
            }
        }
        
        System.out.println();
        System.out.print("Please enter the upper limit for capital city "
                + "sq mileage: ");
        System.out.flush();
        int upperLimit = inputScan.nextInt();
        System.out.println(upperLimit);
        
        System.out.println();
        System.out.println("LISTING CAPITALS WITH AREAS LESS THAN " + 
                upperLimit + ":");
        System.out.println();
        for (String k : keys) {
            if (states.get(k).getSquareMileage() < upperLimit) {
                System.out.println(k + " - " + states.get(k).getName() + " | Pop: " 
                    + states.get(k).getPopulation() + " | Area: " 
                    + states.get(k).getSquareMileage() + " sq mi");
            }
        }
    }
}
