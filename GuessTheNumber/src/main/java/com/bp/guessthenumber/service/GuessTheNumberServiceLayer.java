/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.guessthenumber.service;

import com.bp.guessthenumber.models.Game;
import com.bp.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author brockpace
 */
public interface GuessTheNumberServiceLayer {
    
    public int begin();
    
    public Round round(Round round);
    
    public List<Game> game();
    
    public Game findById(int gameId);
    
    public List<Round> rounds(int gameId);
    
}
