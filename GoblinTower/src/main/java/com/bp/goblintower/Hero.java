/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.goblintower;

/**
 *
 * @author BrockPace
 */
public class Hero extends Character{
    private int[] potionArray;
    private int numOfPotions;
    private final int maxNumOfPotions;
    private int gold;
    private int goblinsSlain = 0;
    private int currentLevel = 1;
    
    public Hero(int gold) {
        this.maxHealthPoints = r.nextInt(11) + 20;
        this.healthPoints = maxHealthPoints;
        this.attackPower = r.nextInt(3) + 1;
        this.defensePower = r.nextInt(5) + 1;
        this.maxNumOfPotions = r.nextInt(5) + 1;
        this.potionArray = new int[maxNumOfPotions];
        for(int i = 0; i < maxNumOfPotions; i++) this.potionArray[i] = 2;
        this.numOfPotions = maxNumOfPotions;
        this.gold = gold;
    }
    
    public void LevelUp() {
        currentLevel++;
    }
    
    public void killGoblin() {
        System.out.println("Congratulations Hero. You have killed a goblin and earned 2 gold.");
        gold += 2;
        goblinsSlain++;
    }

    public int getGoblinsSlain() {
        return goblinsSlain;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getGold() {
        return gold;
    }
    
    public void usePotion() {
        if (numOfPotions < 1)
            System.out.println("You are out of potions. You are screwed!");
        else {
            healthPoints += 2;
            potionArray[--numOfPotions] = 0;
            if (healthPoints > maxHealthPoints) healthPoints = maxHealthPoints;
            System.out.println("Used a potion " + healthPoints + "/" + maxHealthPoints + "hp");
        }
    }
    
    public boolean buyPotions(int quantity) {
        int price = quantity * 4;
        boolean ans = false;
        if (price > gold) System.out.println("You do not have enough gold. Lol. You broke.");
        else if (quantity < 0) System.out.println("What are you trying to pull? This is a legitamite business!");
        else if ((quantity + numOfPotions) > maxNumOfPotions) {
            System.out.println("You don't have enough slots. " + numOfPotions + "/" + maxNumOfPotions + "full");
        }
        else {
            ans = true;
            gold -= price;
            for(int i = numOfPotions; i < numOfPotions + quantity; i++) {
                potionArray[i] = 2;
            }
            numOfPotions += quantity;
        }
        return ans;
        
    }
}
