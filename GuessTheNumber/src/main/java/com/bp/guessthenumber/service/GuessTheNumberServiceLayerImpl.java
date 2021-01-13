/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.guessthenumber.service;

import com.bp.guessthenumber.data.GuessTheNumberDao;
import com.bp.guessthenumber.data.GuessTheNumberDatabaseDao;
import com.bp.guessthenumber.models.Game;
import com.bp.guessthenumber.models.Round;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author brockpace
 */
@Repository
public class GuessTheNumberServiceLayerImpl implements GuessTheNumberServiceLayer{

    private final GuessTheNumberDao dao;
    
    @Autowired
    public GuessTheNumberServiceLayerImpl(JdbcTemplate jdbcTemplate) {
        this.dao = new GuessTheNumberDatabaseDao(jdbcTemplate);
    }
    
    @Override
    public int begin() {
        Game game = new Game();
        int n = 0;
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for(int i = 0; i < 4; i++) {
            n *= 10;
            n += numbers.get(i);
        }
        String ans = String.valueOf(n);
        game.setAns(ans);
        return dao.begin(game);
    }

    @Override
    public Round round(Round round) {
        Date date = Calendar.getInstance().getTime();
        round.setTime(new Time(date.getTime()));
        Game game = dao.findById(round.getGameId());
        int e = 0;
        int p = 0;
        for (int i = 0; i < 4; i++) {
            if (game.getAns().charAt(i) == round.getGuess().charAt(i))
                e++;
            else {
                for(int j = 0; j < 4; j++) {
                    if (i != j && round.getGuess().charAt(i) == game.getAns().charAt(j))
                        p++;
                }
            }
        }
        if (e == 4) {
            game.setFinished(true);
            dao.update(game);
        }
        round.setResult("e:" + e + ":p:" + p);
        return dao.guess(round);
    }

    @Override
    public List<Game> game() {
        List<Game> games = dao.game();
        for (Game game : games) {
            if (!game.isFinished())
                game.setAns("XXXX");
        }
        return games;
    }

    @Override
    public Game findById(int gameId) {
        Game game = dao.findById(gameId);
        if (!game.isFinished())
            game.setAns("XXXX");
        return game;
    }

    @Override
    public List<Round> rounds(int gameId) {
        return dao.rounds(gameId);
    }
    
}
