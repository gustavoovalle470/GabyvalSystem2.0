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
import com.gabyval.persistence.user.security.AdUserProfiling;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gusta
 */
public class SecurityMan {
    private static final GB_Logger LOG = GB_Logger.getLogger(SecurityMan.class);
    private static List<SecurityEntity> menus;
    
    public static List<SecurityEntity> getSecurityTree(String username){
        LOG.debug("GABYVAL is obtaing the security tree.");
        menus  = new ArrayList<>();
        LOG.debug("GABYVAL is creating dependens.");
        putNodes(username);
        LOG.debug("GABYVAL finished the load the security tree.");
        return menus;
    }
    
    private static List<AdUserProfiling> getProfile(String username) {
        try {
            return PersistenceManager.getInstance().
                    runCriteria("FROM AdUserProfiling a WHERE a.id.gbUsername='"+username+"' AND a.id.gbProfileName IN (SELECT B.gbProfileName FROM AdUserProfile B WHERE B.gbProfileStatus = 1)");
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return null;
        }
        
    }

    private static void putNodes(String username) {
        try {
            List<AdSecMenulinks> nodes = PersistenceManager.getInstance()
                    .runCriteria("FROM AdSecMenulinks a WHERE a.gbMenuParId IS NULL AND a.gbPageView IS NULL AND a.gbMenuStatus = 1");
            for(AdSecMenulinks node : nodes){
                SecurityEntity entidad = new SecurityEntity(node, getAllMenusAllowed(node.getGbMenuId(), username));
                if(entidad.isAllowedMenu()){
                    menus.add(entidad);
                }
            }
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
        }
    }

    private static List<SecurityEntity> getAllMenusAllowed(String gbMenuId, String username) {
        List<SecurityEntity> menusAllowed = new ArrayList<>();
        try {
            for(AdUserProfiling profile: getProfile(username)){
                for(AdSecMenulinks menu: (List<AdSecMenulinks>)PersistenceManager.getInstance()
                    .runCriteria("FROM AdSecMenulinks A where A.gbMenuId IN "
                              + "(SELECT B.id.gbMenuId FROM AdSecMenuProfiling B WHERE "
                              + "B.id.gbProfileName = '"+profile.getId().getGbProfileName()+"') "
                              + "AND A.gbMenuStatus = 1 AND A.gbMenuParId ='"+gbMenuId+"'")){
                    if(notRepeatMenu(menusAllowed, menu)){
                        menusAllowed.add(new SecurityEntity(menu));
                    }
                } 
            }
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        return menusAllowed;
    }

    private static boolean notRepeatMenu(List<SecurityEntity> menusAllowed, AdSecMenulinks menu) {
        for(SecurityEntity e : menusAllowed){
            if(e.getMenu().getGbMenuId().equals(menu.getGbMenuId())){
                return false;
            }
        }
        return true;
    }
}
