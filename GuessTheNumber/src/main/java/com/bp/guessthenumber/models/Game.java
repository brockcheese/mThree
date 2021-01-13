/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.guessthenumber.models;

/**
 *
 * @author brockpace
 */
public class Game {
    
    private int gameId;
    private String ans;
    private boolean finished;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int GameId) {
        this.gameId = GameId;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
}
