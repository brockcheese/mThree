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
public class Goblin extends Character{
    
    public Goblin() {
        this.maxHealthPoints = r.nextInt(6) + 5;
        this.healthPoints = maxHealthPoints;
        this.attackPower = r.nextInt(2) + 2;
        this.defensePower = r.nextInt(2) + 1;
    }
    
}
