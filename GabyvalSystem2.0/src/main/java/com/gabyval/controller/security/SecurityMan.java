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
import java.util.List;

/**
 *
 * @author gusta
 */
public class SecurityMan {
    private static final GB_Logger LOG = GB_Logger.getLogger(SecurityMan.class);
    private static final String GET_ALLOWED_MENUS="FROM AdSecMenulinks A WHERE A.gbMenuParId='MENUID' AND "
                                                +"(A.gbMenuId IN "
                                                +"      (SELECT B.id.gbMenuId FROM AdSecMenuProfiling B "
                                                +"      WHERE B.id.gbProfileName IN ('PROFILEID')) "
                                                +"OR A.gbIsNode = 1)"
            + "                   AND A.gbMenuStatus = 1";
    private static final String GET_NODES="FROM AdSecMenulinks A WHERE A.gbMenuParId IS NULL AND A.gbMenuStatus = 1";
    
    private static String getProfile(String username) throws GB_Exception {
        String profiles="";
        for ( AdUserProfiling prof : (List<AdUserProfiling>) PersistenceManager.getInstance().
              runCriteria("FROM AdUserProfiling a WHERE a.id.gbUsername='"+username+"' AND a.gbProfileStatus = 1")){
            profiles = profiles+prof.getId().getGbProfileName()+",";
        }
        if(profiles.equals("")){
            throw new GB_Exception("No se encontraron perfiles");
        }
        return profiles.substring(0, profiles.length()-1);
    }
    
    public static List<AdSecMenulinks> getAllNodesMenus() throws GB_Exception{
        return PersistenceManager.getInstance().runCriteria(GET_NODES);
    }
    
    public static List<AdSecMenulinks> getAllowedMenus(AdSecMenulinks menu, String username) throws GB_Exception{
        String preparedQuery=GET_ALLOWED_MENUS;
        preparedQuery = preparedQuery.replace("MENUID", menu.getGbMenuId());
        preparedQuery = preparedQuery.replace("PROFILEID", getProfile(username));
        return PersistenceManager.getInstance().runCriteria(preparedQuery);
    }
}