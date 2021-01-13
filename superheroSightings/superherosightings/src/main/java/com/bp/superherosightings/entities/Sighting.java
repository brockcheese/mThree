/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.entities;

import java.time.LocalDate;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author brockpace
 */
public class Sighting {
    private int id;
    private SuperCharacter character;
    private Location location;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SuperCharacter getCharacter() {
        return character;
    }

    public void setCharacter(SuperCharacter character) {
        this.character = character;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.character);
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.character, other.character)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }
    
    
}
