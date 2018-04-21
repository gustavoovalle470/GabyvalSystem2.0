/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.system.security;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.persistence.user.AdUsers;
import com.gabyval.persistence.user.security.AdUserProfiling;
import com.gabyval.persistence.user.security.AdUserProfilingId;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gusta
 */
public class ProfileAsignController {
    public static List<String> getAllUsers() throws GB_Exception{
        List<String> users = new ArrayList<>();
        List<AdUsers> menus = PersistenceManager.getInstance().getAll(AdUsers.class);
        for(AdUsers user : menus){
            users.add(user.getGbUsername());
        }
        return users;
    }
    
    public static List<String> getAllUsersInProf(String profile) throws GB_Exception{
        List<String> usersInProf = new ArrayList<>();
        List<AdUserProfiling> menus = PersistenceManager.getInstance().runCriteria("FROM AdUserProfiling A WHERE A.id.gbProfileName= '"+profile+"'");
        for(AdUserProfiling userprof : menus){
            usersInProf.add(userprof.getId().getGbUsername());
        }
        return usersInProf;
    }
    
    public static void saveUsersAsign(List<String> usersInProf, String profilename, String username) throws GB_Exception{
        List<String> usersInPrev = getAllUsersInProf(profilename);
        if(usersInProf.size() > usersInPrev.size()){
            for(String user: cleanUsers(usersInProf, usersInPrev)){
                PersistenceManager.getInstance().save(new AdUserProfiling(new AdUserProfilingId(profilename, user), username, username, GBEnvironment.getInstance().serverDate(), GBEnvironment.getInstance().serverDate(), 0));
            }
        }else{
            for(String user: cleanUsers(usersInPrev, usersInProf)){
                AdUserProfilingId id = new AdUserProfilingId(profilename, user);
                PersistenceManager.getInstance().delete(PersistenceManager.getInstance().load(AdUserProfiling.class, id));
            }
        }
    }

    private static List<String> cleanUsers(List<String> usersInProf, List<String> usersInPrev) {
        for(String userPrev: usersInPrev){
            usersInProf.remove(userPrev);
        }
        return usersInProf;
    }
}
