/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.user;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.AdUsers;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author gusta
 */
public class UserController {
    private final GB_Logger LOG = GB_Logger.getLogger(UserController.class);
    private static UserController instance;

    public static UserController getInstance(){
        if(instance == null){
            instance = new UserController();
        }
        return instance;
    }
    
    public boolean login(String username, String pass) throws GB_Exception{
        AdUsers user = getUser(username);
        LOG.debug("GABYVAL start the user validations.");
        int errorId=0;
        if(user == null){
            errorId=10;
        }else if(user.getGbLoginStatus() == 1){
            errorId=11;
        }else if(user.getGbOprativeStatus() != 1){
            switch(user.getGbOprativeStatus()){
                case 0: errorId=47;
                    break;
                case 2: errorId=48;
                    break;
                case 3: errorId=49;
                    break;
                case 4: errorId=12;
                    break;
                default: errorId=50;
                    break;
            }
        }else if(!user.getGbPassword().equals(pass)){
            errorId=13;
        }
        if(errorId != 0){
            GB_Exception ex = new GB_Exception(errorId, username);
            LOG.error(ex);
            throw ex;
        }
        LOG.debug("GABYVAL finished the user validations, change the user status for loggin.");
        user.setGbLoginStatus(1);
        LOG.debug("GABYVAL was change the user to loggin. Updating the last connection for user "+username);
        user.setGbLastLogginDt(GBEnvironment.getInstance().serverDate());
        LOG.debug("GABYVAL was finish the update.");
        try{
            LOG.debug("GABYVAL is updating object "+user);
            PersistenceManager.getInstance().update(user);
            LOG.debug("GABYVAL is reload the object "+user);
            PersistenceManager.getInstance().refresh(user);
            return true;
        }catch(GB_Exception ex){
            LOG.error(ex);
            throw new GB_Exception(14, username);
        }
    }

    public void logout(String username) throws GB_Exception {
        AdUsers user = getUser(username);
        user.setGbLoginStatus(0);
        try {
            PersistenceManager.getInstance().update(user);
            PersistenceManager.getInstance().refresh(user);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            throw new GB_Exception(15, username);
        }
    }

    public boolean isNeedChangePasswor(String username) throws GB_Exception {
        AdUsers user = getUser(username);
        Calendar c = Calendar.getInstance();
        c.setTime(user.getGbLastPwdXgeDt());
        c.add(Calendar.MONTH, 1);
        if(GBEnvironment.getInstance().serverDate().after(c.getTime())){
            return true;
        }
        return false;
    }
    
    public void changePassword(String username, String password) throws GB_Exception{
        AdUsers user = getUser(username);
        user.setGbPassword(password);
        user.setGbLastPwdXgeDt(GBEnvironment.getInstance().serverDate());
        try {
            PersistenceManager.getInstance().update(user);        
            PersistenceManager.getInstance().refresh(user);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            throw new GB_Exception(16);
        }
    }
    
    public AdUsers getUser(String username) throws GB_Exception{
        AdUsers user = (AdUsers)PersistenceManager.getInstance().load(AdUsers.class, username);
        if(user != null){
            PersistenceManager.getInstance().refresh(user);
            return user;
        }else{
            GB_Exception ex = new GB_Exception(17, username);
            LOG.error(ex);
            throw ex;
        }
    }
    
    public void saveUser(AdUsers user) throws GB_Exception{
        try {
            PersistenceManager.getInstance().save(user);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            throw new GB_Exception(ex);
        }
    }
    
    public List<AdUsers> getAllUsers(){
        try {
            return PersistenceManager.getInstance().getAll(AdUsers.class);
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        return new ArrayList();
    }
    
    public void setAllUsers(List<AdUsers> users){
        
    }
}
