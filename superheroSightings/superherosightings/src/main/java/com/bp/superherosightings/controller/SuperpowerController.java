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
import com.bp.superherosightings.entities.Superpower;
import java.util.List;
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
public class SuperpowerController {
    
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
    
    @GetMapping("superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }
    
    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(Integer id) {
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }
    
    @GetMapping("editSuperpower")
    public String editSuperpower(Integer id, Model model) {
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(String superpower) {
        Superpower superpowerObj = new Superpower();
        superpowerObj.setSuperpower(superpower);
        superpowerDao.addSuperpower(superpowerObj);
        
        return "redirect:/superpowers";
    }
    
    @PostMapping("editSuperpower")
    public String performEditSuperpower(Superpower superpower) {
        superpowerDao.updateSuperpower(superpower);
        
        return "redirect:/superpowers";
    }
}
