/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.playerdeck;

/**
 *
 * @author BrockPace
 */
public class Card {
    private String title;
    private String description;

    public String getRank() {
        return title;
    }

    public void setRank(String rank) {
        this.title = rank;
    }

    public String getSuit() {
        return description;
    }

    public void setSuit(String suit) {
        this.description = suit;
    }

    public Card(String rank, String suit) {
        this.title = rank;
        this.description = suit;
    }
}
