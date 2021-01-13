/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.birthdaycalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author BrockPace
 */
public class BirthdayCalculator {
    static final String[] DAY = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
    public static void main(String[] args) {
        System.out.println("Welcome to the 100% Scientifically Accurate Birthday Calculator!");
        System.out.println();
        System.out.println("What's your birthday?");
        Scanner scan = new Scanner(System.in);
        boolean errorCheck;
        do{
            errorCheck = false;
            try{
                Date currentDate = new Date();
                Calendar currentCal = Calendar.getInstance();
                currentCal.setTime(currentDate);
                String rawDate = scan.nextLine();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Date birthday = format.parse(rawDate);
                Calendar c = Calendar.getInstance();
                c.setTime(birthday);
                int birthYear = c.get(Calendar.YEAR);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                System.out.println();
                System.out.println("That means you were born on a " + DAY[dayOfWeek - 1] + "!");
                c.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
                dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                System.out.println("This year it falls on a " + DAY[dayOfWeek - 1] + "…");
                String strDate = format.format(currentDate);
                System.out.println("And since today is " + strDate + ",");
                long numOfDays = ChronoUnit.DAYS.between(currentCal.toInstant(), c.toInstant()) + 1;
                int age = currentCal.get(Calendar.YEAR) - birthYear;
                if (numOfDays < 0){
                    c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
                    numOfDays = ChronoUnit.DAYS.between(currentCal.toInstant(), c.toInstant()) + 1;
                    age++;
                }
                System.out.println("there's only " + numOfDays + " more days until the next one when you turn " + age);
                
            } catch (ParseException e){
                errorCheck = true;
                System.out.println("Please format properly:");
            }
        } while (errorCheck);

    }
}
