/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.playerdeck;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author BrockPace
 */
public class App {
    
    private static final Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) {
        Deck draw = new Deck();
        Deck discard = new Deck();
        Deck played = new Deck();
        draw.fillDeck();
        while (true) {
            draw.shuffle();
            int num = 0;
            while (!draw.isEmpty()) {
                try {
                    System.out.println("How many card would you like to play?");
                    num = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Not a valid input. Please try again!!!");
                    scan.nextLine();
                }
                while(!draw.isEmpty() && num > 0) {                    
                    Card c = draw.removeCard();
                    System.out.println(c.getRank() + " of " + c.getSuit());
                    played.addCard(c);
                    num--;
                }
                while(!played.isEmpty()) {
                    discard.addCard(played.removeCard());
                }
                
            }
            boolean exit = true;
            scan.nextLine();
            while (exit) {
                exit = false;
                System.out.println("Do you want to play the game again? (yes/no)");
                String response = scan.nextLine();
                switch(response) {
                    case "yes":
                        Deck temp = discard;
                        discard = draw;
                        draw = temp;
                        break;
                    case "no":
                        System.out.println("You are really done? lame.");
                        return;
                    default:
                        System.out.println("What are you even saying bro? Kinda cringe.");
                        exit = true;
                }
            }
        }
    }
}
