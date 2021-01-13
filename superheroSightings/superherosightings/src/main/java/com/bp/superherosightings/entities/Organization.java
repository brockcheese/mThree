/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.superherosightings.entities;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author brockpace
 */
public class Organization {
    private int id;
    private String name;
    private String description;
    private String address;
    private String contact;
    private boolean villain;
    private List<SuperCharacter> characters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isVillain() {
        return villain;
    }

    public void setVillain(boolean villain) {
        this.villain = villain;
    }

    public List<SuperCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<SuperCharacter> characters) {
        this.characters = characters;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + Objects.hashCode(this.contact);
        hash = 67 * hash + (this.villain ? 1 : 0);
        hash = 67 * hash + Objects.hashCode(this.characters);
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
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.villain != other.villain) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.characters, other.characters)) {
            return false;
        }
        return true;
    }
}
