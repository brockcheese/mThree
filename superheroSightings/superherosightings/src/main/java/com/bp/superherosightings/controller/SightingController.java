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
import com.bp.superherosightings.entities.Location;
import com.bp.superherosightings.entities.Sighting;
import com.bp.superherosightings.entities.SuperCharacter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author brockpace
 */
@Controller
public class SightingController {
    
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
    
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locations = locationDao.getAllLocations();
        List<SuperCharacter> characters = characterDao.getAllCharacters();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("characters", characters);
        return "sightings";
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<SuperCharacter> characters = characterDao.getAllCharacters();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("characters", characters);
        model.addAttribute("locations", locations);
        return "editSighting";
    }
        
    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {
        String characterId = request.getParameter("characterId");
        String locationId = request.getParameter("locationId");        
       
        sighting.setCharacter(characterDao.getCharacterById(Integer.parseInt(characterId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sightingDao.addSighting(sighting);
        
        return "redirect:/sightings";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, HttpServletRequest request) {
        String characterId = request.getParameter("characterId");
        String locationId = request.getParameter("locationId");
        
        sighting.setCharacter(characterDao.getCharacterById(Integer.parseInt(characterId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sightingDao.updateSighting(sighting);
        
        return "redirect:/sightings";
    }
}
