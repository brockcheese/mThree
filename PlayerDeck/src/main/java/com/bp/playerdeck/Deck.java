/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.playerdeck;

import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author BrockPace
 */
public class Deck {
    Stack<Card> cards;

    
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static final String[] suits = {"Spades", "Clubs", "Hearts", "Diamonds"};

    public Deck() {
        cards = new Stack<>();
    }
    
    public void fillDeck() {
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.push(new Card(rank, suit));
            }
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    public Card removeCard() {
        return cards.pop();
    }
    
    public void addCard(Card newCard) {
        cards.push(newCard);
    }
    
    public boolean isEmpty() {
        return cards.empty();
    }
}
