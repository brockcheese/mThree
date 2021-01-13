/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.controller;

import com.bp.superherosightings.dao.CharacterDao;
import com.bp.superherosightings.dao.LocationDao;
import com.bp.superherosightings.dao.OrganizationDao;
import com.bp.superherosightings.dao.SightingDao;
import com.bp.superherosightings.dao.SuperpowerDao;
import com.bp.superherosightings.entities.Organization;
import com.bp.superherosightings.entities.SuperCharacter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author brockpace
 */
@Controller
public class OrganizationController {
    
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
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<SuperCharacter> characters = characterDao.getAllCharacters();
        model.addAttribute("organizations", organizations);
        model.addAttribute("characters", characters);
        return "organizations";
    }
    
    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("editCourse")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<SuperCharacter> characters = characterDao.getAllCharacters();
        model.addAttribute("organization", organization);
        model.addAttribute("characters", characters);
        return "editCourse";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String[] characterIds = request.getParameterValues("characterId");
        
        List<SuperCharacter> characters = new ArrayList<>();
        for(String characterId : characterIds) {
            characters.add(characterDao.getCharacterById(Integer.parseInt(characterId)));
        }
        organization.setCharacters(characters);
        organizationDao.addOrganization(organization);
        
        return "redirect:/organizations";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(Organization organization, HttpServletRequest request) {
        String[] characterIds = request.getParameterValues("characterId");
        
        List<SuperCharacter> characters = new ArrayList<>();
        for(String characterId : characterIds) {
            characters.add(characterDao.getCharacterById(Integer.parseInt(characterId)));
        }
        organization.setCharacters(characters);
        organizationDao.updateOrganization(organization);
        
        return "redirect:/courses";
    }
}
