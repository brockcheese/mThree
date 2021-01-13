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
public class SightingDaoDBTest {
    
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
    
    public SightingDaoDBTest() {
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
     * Test of getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSighting() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
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
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
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
        
        Sighting sighting2 = new Sighting();
        sighting2.setCharacter(character);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.now());
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
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
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        
        assertEquals(sighting, fromDao);

        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setLatitude(new BigDecimal(5.854843));
        location2.setLongitude(new BigDecimal(108.923884));
        location2 = locationDao.addLocation(location2);
        
        Superpower superpower2 = new Superpower();
        superpower2.setSuperpower("Test Superpower 2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Character Name 2");
        character2.setDescription("Test Character Description 2");
        character2.setSuperpower(superpower2);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);
        
        sighting.setLocation(location2);
        sighting.setCharacter(character2);
        
        sightingDao.updateSighting(sighting);
        
        assertNotEquals(sighting, fromDao);
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
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
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsForDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForDate() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
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
        sighting.setDate(LocalDate.now().withYear(2020).withMonth(12).
                withDayOfMonth(30));
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setCharacter(character);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.now());
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setCharacter(character);
        sighting3.setLocation(location);
        sighting3.setDate(LocalDate.now().withYear(2020).withMonth(12).
                withDayOfMonth(30));
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightings = sightingDao.getSightingsForDate(
                LocalDate.now().withYear(2020).withMonth(12).
                withDayOfMonth(30));
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting2));
        assertTrue(sightings.contains(sighting3));
    }
    
}
