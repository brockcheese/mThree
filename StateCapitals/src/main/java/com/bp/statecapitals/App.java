/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.statecapitals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author BrockPace
 */
public class App {
    public static void main(String[] args) {
        Map<String, String> states = new HashMap<>();
        states.put("Alabama", "Montgomery");
        states.put("Alaska", "Juneau");
        states.put("Arizona", "Phoenix");
        states.put("Arkansas", "Little Rock");
        states.put("California", "Sacramento");
        states.put("Colorado", "Denver");
        states.put("Connecticut", "Hartford");
        states.put("Delaware", "Dover");
        states.put("Florida", "Tallahassee");
        states.put("Georgia", "Atlanta");
        states.put("Hawaii", "Honolulu");
        states.put("Idaho", "Boise");
        states.put("Illinois", "Springfield");
        states.put("Indiana", "Indianapolis");
        states.put("Iowa", "Des Moines");
        states.put("Kansas", "Topeka");
        states.put("Kentucky", "Frankfort");
        states.put("Louisiana", "Baton Rouge");
        states.put("Maine", "Augusta");
        states.put("Maryland", "Annapolis");
        states.put("Massachusetts", "Boston");
        states.put("Michigan", "Lansing");
        states.put("Minnesota", "Saint Paul");
        states.put("Mississippi", "Jackson");
        states.put("Missouri", "Jefferson City");
        states.put("Montana", "Helena");
        states.put("Nebraska", "Lincoln");
        states.put("Nevada", "Carson City");
        states.put("New Hampshire", "Concord");
        states.put("New Jersey", "Trenton");
        states.put("New Mexico", "Santa Fe");
        states.put("New York", "Albany");
        states.put("North Carolina", "Raleigh");
        states.put("North Dakota", "Bismarck");
        states.put("Ohio", "Columbus");
        states.put("Oklahoma", "Oklahoma City");
        states.put("Oregon", "Salem");
        states.put("Pennsylvania", "Harrisburg");
        states.put("Rhode Island", "Providence");
        states.put("South Carolina", "Columbia");
        states.put("South Dakota", "Pierre");
        states.put("Tennessee", "Nashville");
        states.put("Texas", "Austin");
        states.put("Utah", "Salt Lake City");
        states.put("Vermont", "Montpelier");
        states.put("Virginia", "Richmond");
        states.put("Washington", "Olympia");
        states.put("West Virginia", "Charleston");
        states.put("Wisconsin", "Madison");
        states.put("Wyoming", "Cheyenne");
        
        System.out.println("STATES:");
        System.out.println("=======");
        
        Set<String> keys = states.keySet();
        for (String k : keys) {
            System.out.println(k);
        }
        
        System.out.println();
        System.out.println("CAPITALS:");
        System.out.println("=========");
        
        for (String k : keys) {
            System.out.println(states.get(k));
        }
        
        System.out.println();
        System.out.println("STATE/CAPITAL PAIRS:");
        System.out.println("====================");
        
        for (String k : keys) {
            System.out.println(k + " - " + states.get(k));
        }
    }
}
