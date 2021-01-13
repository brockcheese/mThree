/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.goblintower;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author BrockPace
 */
public class App {
    
    private static final Scanner scan = new Scanner(System.in);
    private static Random r = new Random();
    
    public static void main(String[] args) {
        Hero hero = new Hero(0);
        while (true) {
            int steps = 0;
            while(hero.getHealthPoints() > 0) {
                System.out.println("Press Enter to make a Step");
                scan.nextLine();
                steps++;
                if (r.nextBoolean()) {
                    Goblin goblin = new Goblin();
                    while(hero.getHealthPoints() > 0 && goblin.getHealthPoints() > 0) {
                        boolean errorCheck = true;
                        while(errorCheck) {
                            errorCheck = false;
                            System.out.println("You have encountered a goblin. What will you do?");
                            System.out.println("1. Attack");
                            System.out.println("2. Defend");
                            System.out.println("3. Use Potion");
                            int option;
                                try {
                                    option = scan.nextInt();
                                } catch (InputMismatchException e) {
                                    option = 0;
                                }                                
                                scan.nextLine();
                                hero.setMode(false);
                                goblin.setMode(r.nextBoolean());
                                switch (option) {
                                    case 1:
                                        System.out.println("You attack the goblin.");
                                        hero.fight(goblin);                                       
                                        break;
                                    case 2:
                                        hero.setMode(true);
                                        System.out.println("You defend against the goblin.");
                                        break;
                                    case 3:
                                        hero.usePotion();
                                        break;
                                    default:
                                        errorCheck = true;
                                        System.out.println("Not a valid option. Try again.");
                                }
                                if (!errorCheck && !goblin.getMode() && goblin.getHealthPoints() > 0) {
                                    System.out.println("The goblin fights back.");
                                    goblin.fight(hero);
                                }                     
                            
                        }
                        if (goblin.getHealthPoints() <= 0) {
                            hero.killGoblin();
                        }
                        
                    }
                    
                }
                if (steps%10 == 0 && hero.getHealthPoints() > 0) {
                    hero.LevelUp();
                    System.out.println("You have leveled up to level " +
                            hero.getCurrentLevel() + ".");
                    System.out.println("Would you like to visit the potions shop? (yes/no)");
                    String input = scan.nextLine();
                    switch(input) {
                        case "yes":
                            System.out.println("Potions are 4g each you have " + hero.getGold() + "g");
                            boolean valid = false;
                            while (!valid) {
                                System.out.println("How many potions would you like?");
                                int quantity;
                                try {
                                    quantity = scan.nextInt();
                                }catch (InputMismatchException e) {
                                    quantity = -1;
                                }
                                scan.nextLine();
                                valid = hero.buyPotions(quantity);
                            }
                            
                            break;
                        case "no":
                            System.out.println("Ok. Your Loss");
                            return;
                        default:
                            System.out.println("I don't know what you are saying.");
                    }
                }
            }
            System.out.println("You have died. GAME OVER!!!");
            boolean moveOn = false;
            while (!moveOn){
                System.out.println("Would you like to play again? (yes/no):");
                String input = scan.nextLine();
                switch(input) {
                    case "yes":
                        System.out.println("Creating new character.");
                        hero = new Hero(hero.getGold());
                        moveOn = true;
                        break;
                    case "no":
                        System.out.println("You made it to level " +
                                hero.getCurrentLevel() + " with " +
                                hero.getGoblinsSlain() + " goblins slain!");
                        return;
                    default:
                        System.out.println("I don't know what you are saying.");
                }            
            }
        }
    }
}
