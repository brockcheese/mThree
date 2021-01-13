/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.dao.CharacterDaoDB.CharacterMapper;
import com.bp.superherosightings.dao.LocationDaoDB.LocationMapper;
import com.bp.superherosightings.entities.Sighting;
import com.bp.superherosightings.entities.SuperCharacter;
import com.bp.superherosightings.entities.Location;
import com.bp.superherosightings.entities.Superpower;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author brockpace
 */
@Repository
public class SightingDaoDB implements SightingDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting "
                    + "WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, 
                    new SightingMapper(), id);
            sighting.setCharacter(getCharacterForSighting(id));
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private SuperCharacter getCharacterForSighting(int id) {
        final String SELECT_CHARACTER_FOR_SIGHTING = "SELECT c.* FROM "
                + "`character` c JOIN sighting s ON c.id = s.characterId "
                + "WHERE s.id = ?";
        SuperCharacter character = jdbc.queryForObject(
                SELECT_CHARACTER_FOR_SIGHTING, new CharacterMapper(), id);
        character.setSuperpower(getSuperpowerForCharacter(character.getId()));
        return character;
    }
    
    private Superpower getSuperpowerForCharacter(int id) {
        final String SELECT_SUPERPOWER_FOR_CHARACTER = "SELECT s.* "
                + "FROM superpower s JOIN `character` c ON s.id = c.superpowerId "
                + "WHERE c.id = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_CHARACTER, 
                new SuperpowerDaoDB.SuperpowerMapper(), id);
    }
    
    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s ON l.id = s.locationId WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, 
                new LocationMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper()); 
        associateCharacterAndLocation(sightings);
        return sightings;
    }
    
    private void associateCharacterAndLocation(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setCharacter(getCharacterForSighting(sighting.getId()));
            sighting.setLocation(getLocationForSighting(sighting.getId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO "
                + "sighting(characterId, locationId, `date`) VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getCharacter().getId(),
                sighting.getLocation().getId(),
                Date.valueOf(sighting.getDate()));
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET characterId = ?, "
                + "locationId = ?, `date` = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getCharacter().getId(),
                sighting.getLocation().getId(),
                Date.valueOf(sighting.getDate()),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsForDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FOR_DATE = "SELECT * FROM sighting "
                + "WHERE `date` = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_DATE, 
                new SightingMapper(), Date.valueOf(date));
        associateCharacterAndLocation(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsForNewsfeed() {
        final String SELECT_SIGHTINGS_FOR_NEWSFEED = "SELECT * FROM sighting "
                + "ORDER BY `date` DESC LIMIT 0, 10";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_NEWSFEED, 
                new SightingMapper());
        associateCharacterAndLocation(sightings);
        return sightings;
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }
        
        
    }
    
}
