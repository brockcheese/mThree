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
public class CharacterDaoDBTest {
    
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
    
    public CharacterDaoDBTest() {
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
     * Test of getCharacterById method, of class CharacterDaoDB.
     */
    @Test
    public void testAddAndGetCharacter() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter fromDao = characterDao.getCharacterById(character.getId());
        
        assertEquals(character, fromDao);
    }

    /**
     * Test of getAllCharacters method, of class CharacterDaoDB.
     */
    @Test
    public void testGetAllCharacters() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Name 2");
        character2.setDescription("Test Description 2");
        character2.setSuperpower(superpower);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);
        
        List<SuperCharacter> characters = characterDao.getAllCharacters();
        
        assertEquals(2, characters.size());
        assertTrue(characters.contains(character));
        assertTrue(characters.contains(character2));
    }

    /**
     * Test of updateCharacter method, of class CharacterDaoDB.
     */
    @Test
    public void testUpdateCharacter() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter fromDao = characterDao.getCharacterById(character.getId());
        assertEquals(character, fromDao);
        
        character.setName("New Test Name");
        Superpower superpower2 = new Superpower();
        superpower2.setSuperpower("Test Superpower 2");
        superpower2 = superpowerDao.addSuperpower(superpower);
        character.setSuperpower(superpower2);
        characterDao.updateCharacter(character);
        
        assertNotEquals(character, fromDao);
        
        fromDao = characterDao.getCharacterById(character.getId());
        assertEquals(character, fromDao);
    }

    /**
     * Test of deleteCharacterById method, of class CharacterDaoDB.
     */
    @Test
    public void testDeleteCharacterById() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
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
        
        SuperCharacter fromDao = characterDao.getCharacterById(character.getId());
        assertEquals(character, fromDao);
        
        characterDao.deleteCharacterById(character.getId());
        
        fromDao = characterDao.getCharacterById(character.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getCharactersForLocation method, of class CharacterDaoDB.
     */
    @Test
    public void testGetCharactersForLocation() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Name 2");
        character2.setDescription("Test Description 2");
        character2.setSuperpower(superpower);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);
        
        SuperCharacter character3 = new SuperCharacter();
        character3.setName("Test Name 3");
        character3.setDescription("Test Description 3");
        character3.setSuperpower(superpower);
        character3.setVillain(false);
        character3 = characterDao.addCharacter(character3);
        
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(new BigDecimal(5.68999));
        location.setLongitude(new BigDecimal(120.84939));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setLatitude(new BigDecimal(120.68999));
        location2.setLongitude(new BigDecimal(5.84939));
        location2 = locationDao.addLocation(location2);
        
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
        sighting3.setCharacter(character3);
        sighting3.setLocation(location);
        sighting3.setDate(LocalDate.now());
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<SuperCharacter> characters = characterDao.getCharactersForLocation(location);
        assertEquals(2, characters.size());
        assertTrue(characters.contains(character));
        assertFalse(characters.contains(character2));
        assertTrue(characters.contains(character3));
    }

    /**
     * Test of getHerosForLocation method, of class CharacterDaoDB.
     */
    @Test
    public void testGetHerosForLocation() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Name 2");
        character2.setDescription("Test Description 2");
        character2.setSuperpower(superpower);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);
        
        SuperCharacter character3 = new SuperCharacter();
        character3.setName("Test Name 3");
        character3.setDescription("Test Description 3");
        character3.setSuperpower(superpower);
        character3.setVillain(false);
        character3 = characterDao.addCharacter(character3);
        
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(new BigDecimal(5.68999));
        location.setLongitude(new BigDecimal(120.84939));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setLatitude(new BigDecimal(120.68999));
        location2.setLongitude(new BigDecimal(5.84939));
        location2 = locationDao.addLocation(location2);
        
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
        sighting3.setCharacter(character3);
        sighting3.setLocation(location);
        sighting3.setDate(LocalDate.now());
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<SuperCharacter> characters = characterDao.getHerosForLocation(location);
        assertEquals(1, characters.size());
        assertFalse(characters.contains(character));
        assertFalse(characters.contains(character2));
        assertTrue(characters.contains(character3));
    }

    /**
     * Test of getVillainsForLocation method, of class CharacterDaoDB.
     */
    @Test
    public void testGetVillainsForLocation() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Name 2");
        character2.setDescription("Test Description 2");
        character2.setSuperpower(superpower);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);
        
        SuperCharacter character3 = new SuperCharacter();
        character3.setName("Test Name 3");
        character3.setDescription("Test Description 3");
        character3.setSuperpower(superpower);
        character3.setVillain(false);
        character3 = characterDao.addCharacter(character3);
        
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("Test Location Address");
        location.setLatitude(new BigDecimal(5.68999));
        location.setLongitude(new BigDecimal(120.84939));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("Test Location Address 2");
        location2.setLatitude(new BigDecimal(120.68999));
        location2.setLongitude(new BigDecimal(5.84939));
        location2 = locationDao.addLocation(location2);
        
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
        sighting3.setCharacter(character3);
        sighting3.setLocation(location);
        sighting3.setDate(LocalDate.now());
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<SuperCharacter> characters = characterDao.getVillainsForLocation(location);
        assertEquals(1, characters.size());
        assertTrue(characters.contains(character));
        assertFalse(characters.contains(character2));
        assertFalse(characters.contains(character3));
    }

    /**
     * Test of getCharactersForOrganization method, of class CharacterDaoDB.
     */
    @Test
    public void testGetCharactersForOrganization() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Name");
        character.setDescription("Test Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Name 2");
        character2.setDescription("Test Description 2");
        character2.setSuperpower(superpower);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);
        
        SuperCharacter character3 = new SuperCharacter();
        character3.setName("Test Name 3");
        character3.setDescription("Test Description 3");
        character3.setSuperpower(superpower);
        character3.setVillain(false);
        character3 = characterDao.addCharacter(character3);
        
        List<SuperCharacter> characters = new ArrayList<>();
        characters.add(character);
        characters.add(character2);
        
        Organization organization = new Organization();
        organization.setName("Test Organization Name");
        organization.setDescription("Test Organization Description");
        organization.setAddress("Test Organization Address");
        organization.setContact("Test Organization Contact");
        organization.setVillain(true);
        organization.setCharacters(characters);
        organization = organizationDao.addOrganization(organization);
        
        List<SuperCharacter> characters2 = characterDao.getCharactersForOrganization(organization);
        assertEquals(2, characters2.size());
        assertTrue(characters2.contains(character));
        assertTrue(characters2.contains(character2));
        assertFalse(characters2.contains(character3));
    }
    
}
