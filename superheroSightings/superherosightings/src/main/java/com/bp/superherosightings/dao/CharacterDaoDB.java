/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.dao.SuperpowerDaoDB.SuperpowerMapper;
import com.bp.superherosightings.entities.SuperCharacter;
import com.bp.superherosightings.entities.Location;
import com.bp.superherosightings.entities.Organization;
import com.bp.superherosightings.entities.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class CharacterDaoDB implements CharacterDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SuperCharacter getCharacterById(int id) {
        try {
            final String GET_CHARACTER_BY_ID = 
                    "SELECT * FROM `character` WHERE id = ?";
            SuperCharacter character = jdbc.queryForObject(GET_CHARACTER_BY_ID, new CharacterMapper(), id);
            character.setSuperpower(getSuperpowerForCharacter(id));
            return character;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private Superpower getSuperpowerForCharacter(int id) {
        final String SELECT_SUPERPOWER_FOR_CHARACTER = "SELECT s.* "
                + "FROM superpower s JOIN `character` c ON s.id = c.superpowerId "
                + "WHERE c.id = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_CHARACTER, 
                new SuperpowerMapper(), id);
    }

    @Override
    public List<SuperCharacter> getAllCharacters() {
        final String GET_ALL_CHARACTERS = "SELECT * FROM `character`";
        List<SuperCharacter> characters = jdbc.query(GET_ALL_CHARACTERS, 
                new CharacterMapper());
        associateSuperpower(characters);
        return characters;
    }
    
    private void associateSuperpower(List<SuperCharacter> characters) {
        for (SuperCharacter character : characters) {
            character.setSuperpower(getSuperpowerForCharacter(character.getId()));            
        }
    }

    @Override
    @Transactional
    public SuperCharacter addCharacter(SuperCharacter character) {
        final String INSERT_CHARACTER = "INSERT INTO `character`(`name`, "
                + "`description`, superpowerId, isVillain) VALUES(?,?,?,?)";
        jdbc.update(INSERT_CHARACTER,
                character.getName(),
                character.getDescription(),
                character.getSuperpower().getId(),
                character.isVillain());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        character.setId(newId);
        return character;
    }

    @Override
    public void updateCharacter(SuperCharacter character) {
        final String UPDATE_CHARACTER = "UPDATE `character` SET `name` = ?, "
                + "`description` = ?, superpowerId = ?, isVillain = ? WHERE id = ?";
        jdbc.update(UPDATE_CHARACTER,
                character.getName(),
                character.getDescription(),
                character.getSuperpower().getId(),
                character.isVillain(),
                character.getId());
    }

    @Override
    @Transactional
    public void deleteCharacterById(int id) {
        final String DELETE_CHARACTER_ORGANIZATION = "DELETE FROM "
                + "character_organization WHERE characterId = ?";
        
        jdbc.update(DELETE_CHARACTER_ORGANIZATION, id);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE "
                + "characterId = ?";
        
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_CHARACTER = "DELETE FROM `character` WHERE id = ?";
        
        jdbc.update(DELETE_CHARACTER, id);
    }

    @Override
    public List<SuperCharacter> getCharactersForLocation(Location location) {
        final String SELECT_CHARACTERS_FOR_LOCATION = "SELECT c.* FROM "
                + "`character` c JOIN sighting s ON c.id = s.characterId "
                + "WHERE s.locationId = ?";
        
        List<SuperCharacter> characters = jdbc.query(SELECT_CHARACTERS_FOR_LOCATION, 
                new CharacterMapper(), location.getId());
        associateSuperpower(characters);
        return characters;
    }

    @Override
    public List<SuperCharacter> getHerosForLocation(Location location) {
        final String SELECT_HEROS_FOR_LOCATION = "SELECT c.* FROM "
                + "`character` c JOIN sighting s ON c.id = s.characterId "
                + "WHERE s.locationId = ? AND c.isVillain = 0";
        
        List<SuperCharacter> characters = jdbc.query(SELECT_HEROS_FOR_LOCATION, 
                new CharacterMapper(), location.getId());
        associateSuperpower(characters);
        return characters;    
    }

    @Override
    public List<SuperCharacter> getVillainsForLocation(Location location) {
        final String SELECT_VILLAINS_FOR_LOCATION = "SELECT c.* FROM "
                + "`character` c JOIN sighting s ON c.id = s.characterId "
                + "WHERE s.locationId = ? AND c.isVillain = 1";
        
        List<SuperCharacter> characters = jdbc.query(SELECT_VILLAINS_FOR_LOCATION, 
                new CharacterMapper(), location.getId());
        associateSuperpower(characters);
        return characters;
    }

    @Override
    public List<SuperCharacter> getCharactersForOrganization(Organization organization) {
        final String SELECT_CHARACTERS_FOR_ORGANIZATION = "SELECT c.* FROM "
                + "`character` c JOIN character_organization co ON "
                + "c.id = co.characterId WHERE co.organizationId = ?";
        
        List<SuperCharacter> characters = jdbc.query(
                SELECT_CHARACTERS_FOR_ORGANIZATION, new CharacterMapper(), 
                organization.getId());
        associateSuperpower(characters);
        return characters;
    }
    
    public static final class CharacterMapper implements RowMapper<SuperCharacter> {

        @Override
        public SuperCharacter mapRow(ResultSet rs, int index) throws SQLException {
            SuperCharacter character = new SuperCharacter();
            character.setId(rs.getInt("id"));
            character.setName(rs.getString("name"));
            character.setDescription(rs.getString("description"));
            character.setVillain(rs.getBoolean("isVillain"));
            
            return character;
        }
        
    }
    
}
