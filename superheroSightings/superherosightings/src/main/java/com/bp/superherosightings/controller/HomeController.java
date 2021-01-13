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
import com.bp.superherosightings.entities.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author brockpace
 */
@Controller
public class HomeController {
    
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
    
    @GetMapping("/")
    public String displayHome(Model model) {
        List<Sighting> newsfeed = sightingDao.getSightingsForNewsfeed();
        model.addAttribute("sightings", newsfeed);
        return "index";
    }
}
