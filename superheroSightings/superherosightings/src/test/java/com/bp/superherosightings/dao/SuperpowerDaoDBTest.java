/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.entities.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class SuperpowerDaoDBTest {
    
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
    
    public SuperpowerDaoDBTest() {
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
     * Test of getSuperpowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testAddAndGetSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);
    }

    /**
     * Test of getAllSuperpowers method, of class SuperpowerDaoDB.
     */
    @Test
    public void testGetAllSuperpowers() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower superpower2 = new Superpower();
        superpower2.setSuperpower("Test Superpower 2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        
        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(superpower));
        assertTrue(superpowers.contains(superpower2));
    }

    /**
     * Test of updateSuperpower method, of class SuperpowerDaoDB.
     */
    @Test
    public void testUpdateSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);
        
        superpower.setSuperpower("Test Superpower 2");
        superpowerDao.updateSuperpower(superpower);
        assertNotEquals(superpower, fromDao);
        
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);
    }

    /**
     * Test of deleteSuperpowerById method, of class SuperpowerDaoDB.
     */
    @Test
    public void testDeleteSuperpowerById() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Character Name");
        character.setDescription("Test Character Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
        organization.setVillain(true);
        
        List<SuperCharacter> characters = new ArrayList<>();
        characters.add(character);
        organization.setCharacters(characters);
        organization = organizationDao.addOrganization(organization);
        
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(new BigDecimal(5.68999));
        location.setLongitude(new BigDecimal(120.84939));
        location = locationDao.addLocation(location);
        
        Sighting sighting = new Sighting();
        sighting.setCharacter(character);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);
        
        superpowerDao.deleteSuperpowerById(superpower.getId());
        
        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertNull(fromDao);
    }
    
}
