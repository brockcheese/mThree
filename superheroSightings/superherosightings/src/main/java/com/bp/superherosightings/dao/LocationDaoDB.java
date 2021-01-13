/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.entities.SuperCharacter;
import com.bp.superherosightings.entities.Location;
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
public class LocationDaoDB implements LocationDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE "
                    + "id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, 
                    new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(`name`, "
                + "`description`, address, latitude, longitude) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET `name` = ?, "
                + "`description` = ?, address = ?, latitude = ?, longitude = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    @Transactional            
    public void deleteLocationById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting "
                + "WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsForCharacter(SuperCharacter character) {
        final String SELECT_LOCATIONS_FOR_CHARACTER = "SELECT l.* FROM "
                + "location l JOIN sighting s ON l.id = s.locationId "
                + "WHERE s.characterId = ?";
        
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_CHARACTER, 
                new LocationMapper(), character.getId());
        return locations;
    }
    
    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getBigDecimal("latitude"));
            location.setLongitude(rs.getBigDecimal("longitude"));
            
            return location;
        }
        
    }
}
