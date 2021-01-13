/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.dao.CharacterDaoDB.CharacterMapper;
import com.bp.superherosightings.entities.SuperCharacter;
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
public class OrganizationDaoDB implements OrganizationDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * "
                    + "FROM `organization` WHERE id = ?";
            Organization organization = jdbc.queryForObject(
                    SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setCharacters(getCharactersForOrganization(id));
            return organization;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private List<SuperCharacter> getCharactersForOrganization(int id) {
        final String SELECT_CHARACTERS_FOR_ORGANIZATION = "SELECT c.* FROM "
                + "`character` c JOIN character_organization co ON "
                + "c.id = co.characterId WHERE co.organizationId = ?";
        List<SuperCharacter> characters = jdbc.query(SELECT_CHARACTERS_FOR_ORGANIZATION, 
                new CharacterMapper(), id);
        associateSuperpower(characters);
        return characters;
    }
    
    private Superpower getSuperpowerForCharacter(int id) {
        final String SELECT_SUPERPOWER_FOR_CHARACTER = "SELECT s.* "
                + "FROM superpower s JOIN `character` c ON s.id = c.superpowerId "
                + "WHERE c.id = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_CHARACTER, 
                new SuperpowerDaoDB.SuperpowerMapper(), id);
    }
    
    private void associateSuperpower(List<SuperCharacter> characters) {
        for (SuperCharacter character : characters) {
            character.setSuperpower(getSuperpowerForCharacter(character.getId()));            
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM `organization`";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateCharacters(organizations);
        return organizations;
    }
    
    private void associateCharacters(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setCharacters(
                    getCharactersForOrganization(organization.getId()));
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO `organization`(`name`, "
                + "`description`, address, contact, isVillain) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION, 
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.isVillain());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertCharacterOrganization(organization);
        return organization;
    }
    
    private void insertCharacterOrganization(Organization organization) {
        final String INSERT_CHARACTER_ORGANIZATION = "INSERT INTO "
                + "character_organization(characterId, organizationId) "
                + "VALUES(?,?)";
        for (SuperCharacter character : organization.getCharacters()) {
            jdbc.update(INSERT_CHARACTER_ORGANIZATION,
                    character.getId(),
                    organization.getId());
        }      
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE `organization` SET `name` = ?, "
                + "`description` = ?, address = ?, contact = ?, isVillain = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.isVillain(),
                organization.getId());
        
        final String DELETE_CHARACTER_ORGANIZATION = "DELETE FROM "
                + "character_organization WHERE organizationId = ?";
        jdbc.update(DELETE_CHARACTER_ORGANIZATION, organization.getId());
        insertCharacterOrganization(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_CHARACTER_ORGANIZATION = "DELETE FROM "
                + "character_organization WHERE organizationId = ?";
        jdbc.update(DELETE_CHARACTER_ORGANIZATION, id);
        
        final String DELETE_ORGANIZATION = "DELETE FROM `organization` "
                + "WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    public List<Organization> getOrganizationsForCharacter(SuperCharacter character) {
        final String SELECT_ORGANIZATIONS_FOR_CHARACTER = "SELECT o.* FROM "
                + "`organization` o JOIN character_organization co ON "
                + "o.id = co.organizationId WHERE co.characterId = ?";
        List<Organization> organizations =  jdbc.query(SELECT_ORGANIZATIONS_FOR_CHARACTER, 
                new OrganizationMapper(), character.getId());
        associateCharacters(organizations);
        return organizations;
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setContact(rs.getString("contact"));
            organization.setVillain(rs.getBoolean("isVillain"));
            return organization;
        }
        
        
    }
    
}
