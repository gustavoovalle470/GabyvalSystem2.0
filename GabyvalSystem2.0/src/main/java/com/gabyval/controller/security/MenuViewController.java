/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.security;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.security.AdSecMenulinks;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author gusta
 */
public class MenuViewController {
    private static final GB_Logger LOG = GB_Logger.getLogger(MenuViewController.class);
    private static MenuViewController instance;
    
    public static MenuViewController getInstance(){
        if(instance == null){
            instance = new MenuViewController();
        }
        return instance;
    }
    
    public HashMap<String, Integer> getModules() throws GB_Exception{
        int i = 1;
        HashMap<String, Integer> modules = new HashMap();
        List<AdSecMenulinks> menus = PersistenceManager.getInstance().runCriteria("FROM AdSecMenulinks a WHERE a.gbPageView IS NULL");
        for(AdSecMenulinks menu: menus){
            modules.put(menu.getGbMenuName(), i);
            i++;
        }
        return modules;
    }
    
    public List<AdSecMenulinks> getSubModules(String module) throws GB_Exception{
        List<AdSecMenulinks> submodules = PersistenceManager.getInstance().runCriteria("FROM AdSecMenulinks a WHERE a.gbMenuParId = '"+getMenuId(module)+"'");
        if(submodules.size() == 0){
            throw new GB_Exception("No se encontraron submodulos en el sistema.");
        }
        return submodules;
    }

    public String getMenuId(String module) throws GB_Exception {
        List<AdSecMenulinks> menus = PersistenceManager.getInstance().runCriteria("FROM AdSecMenulinks a WHERE a.gbMenuName = '"+module+"'");
        if(menus.size() != 1){
            throw new GB_Exception("El menu "+module+" se encuentra repetido.");
        }
        return menus.get(0).getGbMenuId();
    }

    public void savePage(AdSecMenulinks editPage) throws GB_Exception {
        PersistenceManager.getInstance().save(editPage);
        PersistenceManager.getInstance().update(editPage);
    }
}
