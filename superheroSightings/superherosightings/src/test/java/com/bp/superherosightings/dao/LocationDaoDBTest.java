/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.entities.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author brockpace
 */
@SpringBootTest
public class LocationDaoDBTest {
    
    @Autowired
    CharacterDao characterDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperpowerDao superpowerDao;
    
    public LocationDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<SuperCharacter> characters = characterDao.getAllCharacters();
        for(SuperCharacter character : characters) {
            characterDao.deleteCharacterById(character.getId());
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for(Superpower superpower : superpowers) {
            superpowerDao.deleteSuperpowerById(superpower.getId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(new BigDecimal(108.923884));
        location.setLongitude(new BigDecimal(5.283838));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(new BigDecimal(108.923884));
        location.setLongitude(new BigDecimal(5.283838));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setLatitude(new BigDecimal(5.283838));
        location2.setLongitude(new BigDecimal(108.84883737));
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(new BigDecimal(108.923884));
        location.setLongitude(new BigDecimal(5.283838));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        location.setName("New Test Name");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(new BigDecimal(108.923884));
        location.setLongitude(new BigDecimal(5.283838));
        location = locationDao.addLocation(location);
        
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Character Name");
        character.setDescription("Test Character Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        Sighting sighting = new Sighting();
        sighting.setCharacter(character);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(location.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getLocationsForCharacter method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationsForCharacter() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(new BigDecimal(5.68999));
        location.setLongitude(new BigDecimal(120.84939));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("Test Address 2");
        location2.setLatitude(new BigDecimal(120.68999));
        location2.setLongitude(new BigDecimal(5.84939));
        location2 = locationDao.addLocation(location2);
        
        Location location3 = new Location();
        location3.setName("Test Name 3");
        location3.setDescription("Test Description 3");
        location3.setAddress("Test Address 3");
        location3.setLatitude(new BigDecimal(50.2888484));
        location3.setLongitude(new BigDecimal(100.848483905));
        location3 = locationDao.addLocation(location3);
        
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Character Name");
        character.setDescription("Test Character Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Character Name 2");
        character2.setDescription("Test Character Description 2");
        character2.setSuperpower(superpower);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);  
        
        Sighting sighting = new Sighting();
        sighting.setCharacter(character);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setCharacter(character2);
        sighting2.setLocation(location2);
        sighting2.setDate(LocalDate.now());
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setCharacter(character);
        sighting3.setLocation(location3);
        sighting3.setDate(LocalDate.now());
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Location> locations = locationDao.getLocationsForCharacter(character);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertFalse(locations.contains(location2));
        assertTrue(locations.contains(location3));
    }
    
}
