/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.entities.*;
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
public class OrganizationDaoDBTest {
    
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
    
    public OrganizationDaoDBTest() {
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
     * Test of getOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganization() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Character Name");
        character.setDescription("Test Character Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        List<SuperCharacter> characters = new ArrayList<>();
        characters.add(character);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setVillain(true);
        organization.setCharacters(characters);
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(
                organization.getId());
        assertEquals(organization, fromDao);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Character Name");
        character.setDescription("Test Character Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        List<SuperCharacter> characters = new ArrayList<>();
        characters.add(character);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setVillain(true);
        organization.setCharacters(characters);
        organization = organizationDao.addOrganization(organization);
        
        Organization organization2 = new Organization();
        organization2.setName("Test Name 2");
        organization2.setDescription("Test Description 2");
        organization2.setAddress("Test Address 2");
        organization2.setContact("Test Contact 2");
        organization2.setVillain(true);
        organization2.setCharacters(characters);
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Character Name");
        character.setDescription("Test Character Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        List<SuperCharacter> characters = new ArrayList<>();
        characters.add(character);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setVillain(true);
        organization.setCharacters(characters);
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(
                organization.getId());
        assertEquals(organization, fromDao);
        
        Superpower superpower2 = new Superpower();
        superpower2.setSuperpower("Test Superpower 2");
        superpower2 = superpowerDao.addSuperpower(superpower2);
        
        organization.setName("New Test Name");
        SuperCharacter character2 = new SuperCharacter();
        character2.setName("Test Character Name 2");
        character2.setDescription("Test Character Description 2");
        character2.setSuperpower(superpower2);
        character2.setVillain(false);
        character2 = characterDao.addCharacter(character2);
        characters.add(character2);
        organization.setCharacters(characters);
        
        organizationDao.updateOrganization(organization);
        
        assertNotEquals(organization, fromDao);
        
        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
        Superpower superpower = new Superpower();
        superpower.setSuperpower("Test Superpower");
        superpower = superpowerDao.addSuperpower(superpower);
        
        SuperCharacter character = new SuperCharacter();
        character.setName("Test Character Name");
        character.setDescription("Test Character Description");
        character.setSuperpower(superpower);
        character.setVillain(true);
        character = characterDao.addCharacter(character);
        
        List<SuperCharacter> characters = new ArrayList<>();
        characters.add(character);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setVillain(true);
        organization.setCharacters(characters);
        organization = organizationDao.addOrganization(organization);
        
        Organization fromDao = organizationDao.getOrganizationById(
                organization.getId());
        assertEquals(organization, fromDao);
        
        organizationDao.deleteOrganizationById(organization.getId());
        
        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getOrganizationsForCharacter method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrganizationsForCharacter() {
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
        
        List<SuperCharacter> characters = new ArrayList<>();
        characters.add(character);
        
        List<SuperCharacter> characters2 = new ArrayList<>();
        characters2.add(character);
        characters2.add(character2);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("Test Address");
        organization.setContact("Test Contact");
        organization.setVillain(true);
        organization.setCharacters(characters2);
        organization = organizationDao.addOrganization(organization);
        
        Organization organization2 = new Organization();
        organization2.setName("Test Name 2");
        organization2.setDescription("Test Description 2");
        organization2.setAddress("Test Address 2");
        organization2.setContact("Test Contact 2");
        organization2.setVillain(false);
        organization2.setCharacters(characters);
        organization2 = organizationDao.addOrganization(organization2);
        
        Organization organization3 = new Organization();
        organization3.setName("Test Name 3");
        organization3.setDescription("Test Description 3");
        organization3.setAddress("Test Address 3");
        organization3.setContact("Test Contact 3");
        organization3.setVillain(true);
        organization3.setCharacters(characters2);
        organization3 = organizationDao.addOrganization(organization3);
        
        List<Organization> organizations = 
                organizationDao.getOrganizationsForCharacter(character2);
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertFalse(organizations.contains(organization2));
        assertTrue(organizations.contains(organization3));
    }
    
}
