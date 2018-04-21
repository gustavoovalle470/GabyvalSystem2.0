/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.security;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.security.AdSecMenuProfiling;
import com.gabyval.persistence.user.security.AdSecMenuProfilingId;
import com.gabyval.persistence.user.security.AdSecMenulinks;
import com.gabyval.persistence.user.security.AdUserProfile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author gusta
 */
public class SecurityAsignController {
    private static final GB_Logger LOG = GB_Logger.getLogger(SecurityAsignController.class);
    private static SecurityAsignController instance;    
    
    public static SecurityAsignController getInstance(){
        if(instance == null){
            instance = new SecurityAsignController();
        }
        return instance;
    }
    
    public HashMap<String, Integer> getAllProfiles() throws GB_Exception{
        int i = 1;
        HashMap<String, Integer> profiles = new HashMap();
        List<AdUserProfile> data = PersistenceManager.getInstance().runCriteria("FROM AdUserProfile a WHERE a.gbProfileStatus = 1");
        for(AdUserProfile prof: data){
            profiles.put(prof.getGbProfileName(), i);
            i++;
        }
        return profiles;
    }
    
    public List<String> getAllMenus(String module) throws GB_Exception{
        List<String> menuNames = new ArrayList<>();
        List<AdSecMenulinks> menus = PersistenceManager.getInstance().runCriteria("FROM AdSecMenulinks a "
                        + "            WHERE a.gbPageView IS NOT NULL"
                        + "            AND a.gbMenuParId = '"+MenuViewController.getInstance().getMenuId(module)+"'");
        for(AdSecMenulinks m : menus){
            menuNames.add(m.getGbMenuName());
        }
        return menuNames;
    }
    
    public List<String> getAllMenusGranted(String profile, String module) throws GB_Exception{
        List<String> menuNames = new ArrayList<>();
        List<AdSecMenulinks> menus = PersistenceManager.getInstance().runCriteria("FROM AdSecMenulinks a "
                        + " WHERE a.gbMenuId IN (SELECT b.id.gbMenuId FROM AdSecMenuProfiling b "
                        + "                                 WHERE b.id.gbProfileName = '"+profile+"')"
                        + "            AND a.gbPageView IS NOT NULL"
                        + "            AND a.gbMenuParId = '"+MenuViewController.getInstance().getMenuId(module)+"'");
        for(AdSecMenulinks m : menus){
            menuNames.add(m.getGbMenuName());
        }
        return menuNames;
    }
    
    
    public boolean saveSecurity(String profile, String module, List<String> granted) throws GB_Exception{
        List<String> actuallyMenusGranted = getAllMenusGranted(profile, module);
        List<String> toRemove = new ArrayList<>();
        for(String menu: actuallyMenusGranted){
            if(!granted.contains(menu)){
                removeMenu(MenuViewController.getInstance().getMenuId(menu), profile);
                toRemove.add(menu);
            }
        }
        for(String s: toRemove){
            actuallyMenusGranted.remove(s);
        }
        if(granted.size() != actuallyMenusGranted.size()){
            for(String menu: granted){
               if(!actuallyMenusGranted.contains(menu)){
                   addMenu(MenuViewController.getInstance().getMenuId(menu), profile);
                    actuallyMenusGranted.add(menu);
                }
            }
        }
        return (granted.size() == actuallyMenusGranted.size());
    }
    
    private void removeMenu(String menu, String profile) throws GB_Exception{
        AdSecMenuProfilingId id = new AdSecMenuProfilingId(menu, profile);
        AdSecMenuProfiling profiling = (AdSecMenuProfiling) PersistenceManager.getInstance().load(AdSecMenuProfiling.class, id);
        PersistenceManager.getInstance().delete(profiling);
    }
    
    private void addMenu(String menu, String profile) throws GB_Exception{
        AdSecMenuProfilingId id = new AdSecMenuProfilingId(menu, profile);        
        AdSecMenuProfiling profiling = new AdSecMenuProfiling(id, GBEnvironment.getInstance().serverDate(), 0);
        PersistenceManager.getInstance().save(profiling);
    }
}
