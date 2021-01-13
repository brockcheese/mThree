/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.dao;

import com.bp.superherosightings.entities.SuperCharacter;
import com.bp.superherosightings.entities.Location;
import com.bp.superherosightings.entities.Organization;
import java.util.List;

/**
 *
 * @author brockpace
 */
public interface CharacterDao {
    SuperCharacter getCharacterById(int id);
    List<SuperCharacter> getAllCharacters();
    SuperCharacter addCharacter(SuperCharacter character);
    void updateCharacter(SuperCharacter character);
    void deleteCharacterById(int id);
    
    List<SuperCharacter> getCharactersForLocation(Location location);
    List<SuperCharacter> getHerosForLocation(Location location);
    List<SuperCharacter> getVillainsForLocation(Location location);
    List<SuperCharacter> getCharactersForOrganization(Organization organization);
}
