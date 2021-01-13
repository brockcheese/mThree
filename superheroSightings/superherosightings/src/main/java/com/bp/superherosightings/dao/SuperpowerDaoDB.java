/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

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
public class SuperpowerDaoDB implements SuperpowerDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String SELECT_SUPERPOWER_BY_ID = "SELECT * FROM superpower "
                    + "WHERE id = ?";
            return jdbc.queryForObject(SELECT_SUPERPOWER_BY_ID, 
                    new SuperpowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String SELECT_ALL_SUPERPOWERS = "SELECT * FROM superpower";
        return jdbc.query(SELECT_ALL_SUPERPOWERS, new SuperpowerMapper());
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        final String INSERT_SUPERPOWER = "INSERT INTO superpower(superpower) "
                + "VALUES(?)";
        jdbc.update(INSERT_SUPERPOWER, superpower.getSuperpower());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(newId);
        return superpower;
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        final String UPDATE_SUPERPOWER = "UPDATE superpower SET superpower = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superpower.getSuperpower(),
                superpower.getId());
    }

    @Override
    public void deleteSuperpowerById(int id) {
        final String DELETE_CHARACTER_ORGANIZATION = "DELETE co.* FROM "
                + "character_organization co JOIN `character` c "
                + "ON co.characterId = c.id WHERE c.superpowerId = ?";
        
        jdbc.update(DELETE_CHARACTER_ORGANIZATION, id);
        
        final String DELETE_SIGHTING = "DELETE s.* FROM sighting s "
                + "JOIN `character` c ON s.characterId = c.id "
                + "WHERE c.superpowerId = ?";
        
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_CHARACTER = "DELETE FROM `character` "
                + "WHERE superpowerId = ?";
        
        jdbc.update(DELETE_CHARACTER, id);
        
        final String DELETE_SUPERPOWER = "DELETE FROM superpower WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }
    
    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("id"));
            superpower.setSuperpower(rs.getString("superpower"));
            
            return superpower;
        }
        
    }
}
