/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.security;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.CatalogEntity;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.security.AdSecMenulinks;
import com.gabyval.persistence.user.security.AdUserProfiling;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author gusta
 */
public class SecurityMan {
    private static SecurityMan instance;
    private final GB_Logger LOG = GB_Logger.getLogger(SecurityMan.class);
    private HashMap<String, AdSecMenulinks> menus;
    private ArrayList<String> principals;
    
    public SecurityMan(String username){
        getSecurityTree(username);
    }
    
    public static SecurityMan getInstance(String username){
        if(instance == null){
            instance = new SecurityMan(username);
        }
        return instance;
    }
    
    private HashMap<String, AdSecMenulinks> getSecurityTree(String username){
        LOG.debug("GABYVAL is obtaing the security tree.");
        menus  = new HashMap<>();
        principals = new ArrayList<String>();
        LOG.debug("GABYVAL is creating dependens.");
        getAllMenusAllowed(username);
        LOG.debug("GABYVAL finished the load the security tree.");
        return menus;
    }
    
    private List<AdUserProfiling> getProfile(String username) {
        try {
            return PersistenceManager.getInstance().
                    runCriteria("FROM AdUserProfiling a WHERE a.id.gbUsername='"+username+"' AND a.gbProfileStatus = 1");
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return null;
        }
        
    }

    private void getAllMenusAllowed(String username) {
        boolean include_principal = false;
        try {
            CatalogEntity principalMenus = GBEnvironment.getInstance().getCatalog("PRINCIPAL_MENUS");
            if(principalMenus != null && getProfile(username) != null){
                System.out.println("Se encontraron catalogos disponibles y perfiles de usuario activos.");
                for(String gbPrincipalMenu: principalMenus.getAllCatalog().keySet()){
                    System.out.println("Cargando funcionalidades para el menu principal: "+gbPrincipalMenu);
                    for(AdUserProfiling profile: getProfile(username)){
                        System.out.println("Cargando funcionalidades para el perfil: "+profile);
                        for(AdSecMenulinks menu: (List<AdSecMenulinks>)PersistenceManager.getInstance()
                            .runCriteria("FROM AdSecMenulinks A where A.gbMenuId IN "
                                         + "(SELECT B.id.gbMenuId FROM AdSecMenuProfiling B WHERE "
                                         + "B.id.gbProfileName = '"+profile.getId().getGbProfileName()+"') "
                                         + "AND A.gbMenuStatus = 1 AND A.gbMenuParId ='"+gbPrincipalMenu+"'")){
                            include_principal = true;
                            System.out.println("Incluir menu principal? "+include_principal);
                            if(menus.get(menu.getGbMenuId()) == null){
                                System.out.println("Agregar menu: "+menu.getGbMenuId());
                                menus.put(menu.getGbMenuId(), menu);
                            }
                        }
                    }
                    if(include_principal){
                        principals.add(gbPrincipalMenu);
                    }
                }
            }
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }
    
    public ArrayList<String> getPrincipals(){
        return principals;
    }
    
    public HashMap<String, AdSecMenulinks> getFunctionsAllowed(){
        return menus;
    }
}