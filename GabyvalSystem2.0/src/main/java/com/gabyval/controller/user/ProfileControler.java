/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.user;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.persistence.user.security.AdUserProfile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author gusta
 */
public class ProfileControler {
    
    private static ProfileControler instance;
    private List<AdUserProfile> profiles;
    
    public ProfileControler(){
        try {
            profiles = PersistenceManager.getInstance().getAll(AdUserProfile.class);
            for(AdUserProfile prof : profiles){
                PersistenceManager.getInstance().refresh(prof);
            }
        } catch (GB_Exception ex) {
            
        }
        if(profiles == null){
            profiles = new ArrayList<>();
        }
    }
    
    public static ProfileControler getInstance(){
        if(instance == null){
            instance =  new ProfileControler();
        }
        return instance;
    }
    
    public void createProfile(String profilename, String description, int status, String username) throws GB_Exception{
        if(PersistenceManager.getInstance().load(AdUserProfile.class, profilename) != null){
            throw new GB_Exception("El perfil ya existe.");
        }
        AdUserProfile prof = new AdUserProfile(profilename, description, status, username, username, GBEnvironment.getInstance().serverDate(), GBEnvironment.getInstance().serverDate(), 0);
        try {
            PersistenceManager.getInstance().save(prof);
            PersistenceManager.getInstance().refresh(prof);
            profiles.add(prof);
        } catch (GB_Exception ex) {
            throw ex;
        }
    }

    public List<AdUserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<AdUserProfile> profiles) {
        this.profiles = profiles;
    }
    
    public void setProfile(AdUserProfile profile){
        profiles.set(findProfileIndex(profile), profile);
    }

    private int findProfileIndex(AdUserProfile profile) {
        for(AdUserProfile p: profiles){
            if(p.getGbProfileName().equals(profile.getGbProfileName())){
                return profiles.indexOf(p);
            }
        }
        return 0;
    }
    
    public void saveProfile(AdUserProfile profile) throws GB_Exception{
        PersistenceManager.getInstance().update(profile);
        PersistenceManager.getInstance().refresh(profile);
    }
}
