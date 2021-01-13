/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.guessthenumber.data;

import com.bp.guessthenumber.models.Game;
import com.bp.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author brockpace
 */
public interface GuessTheNumberDao {
    
    int begin(Game game);
    
    Round guess(Round round);
    
    boolean update(Game game);
    
    List<Game> game();
    
    Game findById(int id);
    
    List<Round> rounds(int id);
}
