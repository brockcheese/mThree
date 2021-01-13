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
import com.bp.superherosightings.entities.SuperCharacter;
import com.bp.superherosightings.entities.Superpower;
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
public class CharacterController {
    
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
    
    @GetMapping("characters")
    public String displayCharacters(Model model) {
        List<SuperCharacter> characters = characterDao.getAllCharacters();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("characters", characters);
        model.addAttribute("superpowers", superpowers);
        return "characters";
    }
    
    @GetMapping("characterDetail")
    public String characterDetail(Integer id, Model model) {
        SuperCharacter character = characterDao.getCharacterById(id);
        model.addAttribute("character", character);
        return "characterDetail";
    }
    
    @GetMapping("deleteCharacter")
    public String deleteCharacter(Integer id) {
        characterDao.deleteCharacterById(id);
        return "redirect:/characters";
    }
    
    @GetMapping("editCharacter")
    public String editCharacter(Integer id, Model model) {
        SuperCharacter character = characterDao.getCharacterById(id);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("character", character);
        model.addAttribute("superpowers", superpowers);
        return "editCharacter";
    }
    
    @PostMapping("addCharacter")
    public String addCharacter(SuperCharacter character, HttpServletRequest request) {
        String superpowerId = request.getParameter("superpowerId");
        
        character.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        characterDao.addCharacter(character);
        
        return "redirect:/characters";
    }
    
    @PostMapping("editCharacter")
    public String performEditCharacter(SuperCharacter character, HttpServletRequest request) {
        String superpowerId = request.getParameter("superpowerId");
        
        character.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        characterDao.updateCharacter(character);
        
        return "redirect:/characters";
    }
}
