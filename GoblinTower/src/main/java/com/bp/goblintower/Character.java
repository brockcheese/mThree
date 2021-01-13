/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.goblintower;

import java.util.Random;

/**
 *
 * @author BrockPace
 */
public class Character {
    protected int maxHealthPoints;
    protected int healthPoints;
    protected int attackPower;
    protected int defensePower;
    protected boolean defenseMode;
    protected static Random r = new Random();
    
    public int getHealthPoints() {
        return healthPoints;
    }
    
    public void setMode(boolean defenseMode) {
        this.defenseMode = defenseMode;
    }
    
    public boolean getMode() {
        return defenseMode;
    }
    
    public void fight(Character c) {
        int damage = attackPower;
        if (c.defenseMode)
            damage -= c.defensePower;
        if (damage < 0) damage = 0;
        c.healthPoints -= damage;
        if (c.healthPoints < 0) c.healthPoints = 0;
        System.out.println(damage + " damage dealt for " + c.healthPoints + "/" + c.maxHealthPoints + "hp.");
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }
}
