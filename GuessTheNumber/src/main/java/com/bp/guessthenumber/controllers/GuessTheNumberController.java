/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.guessthenumber.controllers;

import com.bp.guessthenumber.models.Game;
import com.bp.guessthenumber.models.Round;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.bp.guessthenumber.service.GuessTheNumberServiceLayer;

/**
 *
 * @author brockpace
 */
@RestController
@RequestMapping("/bullsandcows")
public class GuessTheNumberController {
    
    private final GuessTheNumberServiceLayer service;

    public GuessTheNumberController(GuessTheNumberServiceLayer service) {
        this.service = service;
    }
    
    @GetMapping("/game")
    public List<Game> allGame() {
        return service.game();
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> findById(@PathVariable int gameId) {
        Game result = service.findById(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);        
    }
    
    @GetMapping("/rounds/{gameId}")
    public List<Round> allRound(@PathVariable int gameId) {
        return service.rounds(gameId);
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int begin() {
        return service.begin();
    }
    
    @PostMapping("/guess")
    public Round guess(@RequestBody Round round){
        return service.round(round);
    }
}
