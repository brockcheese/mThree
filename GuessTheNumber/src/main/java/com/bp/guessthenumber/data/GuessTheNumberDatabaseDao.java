/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.guessthenumber.data;

import com.bp.guessthenumber.models.Game;
import com.bp.guessthenumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

/**
 *
 * @author brockpace
 */
@Profile("database")
public class GuessTheNumberDatabaseDao implements GuessTheNumberDao{

    private final JdbcTemplate jdbcTemplate;
    
    public GuessTheNumberDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int begin(Game game) {
        
        final String sql = "INSERT INTO Game(Answer) VALUES(?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {
            
            PreparedStatement statement = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, game.getAns());
            return statement;
            
        }, keyHolder);
        
        game.setGameId(keyHolder.getKey().intValue());
        
        return game.getGameId();
    }

    @Override
    public Round guess(Round round) {
        
        final String sql = "INSERT INTO Round(Guess, `Time`, Result, GameId) VALUES(?, ?, ?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {
            
            PreparedStatement statement = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, round.getGuess());
            statement.setString(2, round.getTime().toString());
            statement.setString(3, round.getResult());
            statement.setString(4, String.valueOf(round.getGameId()));
            return statement;
            
        }, keyHolder);
        
        round.setRoundId(keyHolder.getKey().intValue());
        
        return round;
    }

    @Override
    public List<Game> game() {
        final String sql = "SELECT GameId, Answer, Finished FROM Game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game findById(int id) {
        final String sql = "SELECT GameId, Answer, Finished "
                + "FROM Game WHERE GameId = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }

    @Override
    public List<Round> rounds(int id) {
        final String sql = "SELECT * FROM Round "
                + "WHERE GameId = ? ORDER BY `Time` ASC;";
        return jdbcTemplate.query(sql, new RoundMapper(), id);
    }

    @Override
    public boolean update(Game game) {
        final String sql = "UPDATE Game SET Finished = ? WHERE GameId = ?;";
        return jdbcTemplate.update(sql, game.isFinished(), game.getGameId()) > 0;
    }
    
    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setAns(rs.getString("Answer"));
            game.setFinished(rs.getBoolean("Finished"));
            return game;
        }
        
    }
    
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("RoundId"));
            round.setGuess(rs.getString("Guess"));
            round.setTime(rs.getTime("Time", Calendar.getInstance()));
            round.setResult(rs.getString("Result"));
            round.setGameId(rs.getInt("GameId"));
            return round;
        }
        
    }
}
