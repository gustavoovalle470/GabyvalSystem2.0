/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.menu;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.security.MenuViewController;
import com.gabyval.controller.security.SecurityAsignController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

/**
 *
 * @author gusta
 */
@Named(value = "SecurityAdminBean")
@SessionScoped
public class SecurityAdminBean implements Serializable{
    private static final GB_Logger LOG = GB_Logger.getLogger(MenuBean.class); //Log for this class.
    private String username;
    private String profile;
    private HashMap<String, Integer> profiles;
    private DualListModel<String> menus;
    private String module;
    private HashMap<String, Integer> modules;
    private List<String> menusGranted;
    private List<String> menusAllowedNoGran;
    
    public SecurityAdminBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            profiles = SecurityAsignController.getInstance().getAllProfiles();
            modules = chargeModules();
            module = ""+1;
            profile=""+1;
            putMenus();
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }

    public HashMap<String, Integer> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<String, Integer> profiles) {
        this.profiles = profiles;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    public void onFilterChange(){
        putMenus();
    }

    public DualListModel<String> getMenus() {
        return menus;
    }

    public void setMenus(DualListModel<String> menus) {
        this.menus = menus;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public HashMap<String, Integer> getModules() {
        return modules;
    }

    public void setModules(HashMap<String, Integer> modules) {
        this.modules = modules;
    }

    private void putMenus() {
        try {
            menusAllowedNoGran = SecurityAsignController.getInstance().getAllMenus(getModuleName());
            menusGranted = SecurityAsignController.getInstance().getAllMenusGranted(getProfileName(), getModuleName());
            for(String menuGranted: menusGranted){
                menusAllowedNoGran.remove(menuGranted);
            }
            menus = new DualListModel<String>(menusAllowedNoGran, menusGranted);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(-1), null);
        }
    }
    
    private HashMap<String, Integer> chargeModules() {
        try {
            return MenuViewController.getInstance().getModules();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(20), null);
            return null;
        }
    }
    
    private String getModuleName() {
        for(String key: modules.keySet()){
            String value = ""+modules.get(key);
            if(value.equals(module)){
                return key;
            }
        }
        return "";
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
    
    public void saveSecurity(){
        try {
            
            if(SecurityAsignController.getInstance().saveSecurity(getProfileName(), getModuleName(), menus.getTarget())){
                GBMessage.putMessage(GBEnvironment.getInstance().getError(25), null);
                GBMessage.putMessage(GBEnvironment.getInstance().getError(26), getProfileName());
            }
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(27), null);
        }
    }
}
