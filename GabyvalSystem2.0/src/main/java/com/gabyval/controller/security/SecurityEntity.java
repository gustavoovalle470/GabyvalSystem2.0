/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.security;

import com.gabyval.persistence.user.security.AdSecMenulinks;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gusta
 */
public class SecurityEntity {
    private AdSecMenulinks menu;
    private List<SecurityEntity> subEntities = new ArrayList<>();
    
    public SecurityEntity(AdSecMenulinks menu){
        this.menu = menu;
    }
    
    public SecurityEntity(AdSecMenulinks menu, List<SecurityEntity> subEntities){
        this.menu = menu;
        this.subEntities = subEntities;
    }
    
    public void putSubEntity(SecurityEntity subEntity){
        subEntities.add(subEntity);
    }
    
    public List<SecurityEntity> getAllSubEntities(){
        return subEntities;
    }
    
    public AdSecMenulinks getMenu(){
        return menu;
    }
    
    public boolean isAllowedMenu(){
        return !subEntities.isEmpty();
    }
}
