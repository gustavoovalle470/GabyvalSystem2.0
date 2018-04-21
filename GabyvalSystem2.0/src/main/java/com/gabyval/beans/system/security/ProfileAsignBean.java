/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.system.security;

import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.security.SecurityAsignController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

/**
 *
 * @author gusta
 */
@ManagedBean(name = "ProfileAsignBean")
@SessionScoped
public class ProfileAsignBean implements Serializable{
    private static final GB_Logger LOG = GB_Logger.getLogger(ProfileAsignBean.class);
    private String username;
    private String profile;
    private HashMap<String, Integer> profiles;
    private DualListModel<String> users;
    private List<String> usersInProf;
    private List<String> usersOutProf;

    public ProfileAsignBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession) FacesContext.
                             getCurrentInstance().getExternalContext().getSession(false));
            profiles = SecurityAsignController.getInstance().getAllProfiles();
            profile=""+1;
            putUsers();
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }
    
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public HashMap<String, Integer> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<String, Integer> profiles) {
        this.profiles = profiles;
    }

    public DualListModel<String> getUsers() {
        return users;
    }

    public void setUsers(DualListModel<String> users) {
        this.users = users;
    }

    public List<String> getUsersInProf() {
        return usersInProf;
    }

    public void setUsersInProf(List<String> usersInProf) {
        this.usersInProf = usersInProf;
    }

    public List<String> getUsersOutProf() {
        return usersOutProf;
    }

    public void setUsersOutProf(List<String> usersOutProf) {
        this.usersOutProf = usersOutProf;
    }
    
    public void onFilterChange(){
        putUsers();
    }
    
    private void putUsers() {
        try {
            usersInProf = ProfileAsignController.getAllUsersInProf(getProfileName());
            usersOutProf = ProfileAsignController.getAllUsers();
            for(String userIn : usersInProf){
                usersOutProf.remove(userIn);
            }
            users = new DualListModel<>(usersOutProf, usersInProf);
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }
    
    private String getProfileName() {
        for(String key: profiles.keySet()){
            String value = ""+profiles.get(key);
            if(value.equals(profile)){
                return key;
            }
        }
        return "";
    }
    
    
    public void saveUserAsign(){
        try {
            
            ProfileAsignController.saveUsersAsign(users.getTarget(), getProfileName(), username);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(45), null);
        } catch (GB_Exception ex) {
            GBMessage.putMessage(GBEnvironment.getInstance().getError(46), null);
            LOG.error(ex);
        }
    }
}
