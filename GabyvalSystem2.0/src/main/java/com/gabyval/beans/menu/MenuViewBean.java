/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.menu;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.security.MenuViewController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.security.AdSecMenulinks;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author gusta
 */
@Named(value = "MenuViewBean")
@SessionScoped
public class MenuViewBean implements Serializable{
    
    private static final GB_Logger LOG = GB_Logger.getLogger(MenuViewBean.class);
    private String username;
    private String module;
    private HashMap<String, Integer> modules;
    private ArrayList<AdSecMenulinks> submenus;
    private HashMap<String, Integer> menuStatus;
    private String menuStat;
    private String pageId;
    private String pageName;
    private String pageView;
    private boolean openPage;
    
    public MenuViewBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            modules = chargeModules();
            module = ""+1;
            onModuleChange();
            menuStatus = GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_STATUS).getAllCatalog();
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public HashMap<String, Integer> getModules() {
        modules = chargeModules();
        return modules;
    }

    public void setModules(HashMap<String, Integer> modules) {
        modules = chargeModules();
        this.modules = modules;
    }

    public ArrayList<AdSecMenulinks> getSubmenus() {
        onModuleChange();
        return submenus;
    }

    public void setSubmenus(ArrayList<AdSecMenulinks> submenus) {
        onModuleChange();
        this.submenus = submenus;
    }

    public HashMap<String, Integer> getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(HashMap<String, Integer> menuStatus) {
        this.menuStatus = menuStatus;
    }

    public String getMenuStat() {
        return menuStat;
    }

    public void setMenuStat(String menuStat) {
        this.menuStat = menuStat;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageView() {
        return pageView;
    }

    public void setPageView(String pageView) {
        this.pageView = pageView;
    }

    public boolean isOpenPage() {
        return openPage;
    }

    public void setOpenPage(boolean openPage) {
        this.openPage = openPage;
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

    public void onModuleChange(){
        try {
            submenus = (ArrayList<AdSecMenulinks>) MenuViewController.getInstance().getSubModules(getModuleName());
        } catch (GB_Exception ex) {
            LOG.info(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(21), getModuleName());
            submenus = new ArrayList<>();
        }
    }

    private String getModuleName() {
        if(module != null){
            for(String key: modules.keySet()){
                String value = ""+modules.get(key);
                if(value.equals(module)){
                    return key;
                }
            }
        }
        return "ES MODULO";
    }
    
    /**
     * Decode the data, for status.
     * @param code code for description.
     * @return String description.
     */
    public String decode(int code){
        return GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_STATUS).decodeValue(code);
    }
    
    /**
     * Action for update the Security profile with data from view.
     * @param evt RowEditEvent, the event of edit.
     */
    public void onPageEdit(RowEditEvent evt){
        AdSecMenulinks editPage = (AdSecMenulinks)evt.getObject();
        editPage.setGbLastDateChg(GBEnvironment.getInstance().serverDate());
        editPage.setGbLastUserChg(username);
        try {
            MenuViewController.getInstance().savePage(editPage);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(22), null);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(23), null);
        }
    }
    
    public void savePage(){
        try {
            AdSecMenulinks menu = new AdSecMenulinks(pageId, pageName, 
                                      (getModuleName().equalsIgnoreCase("ES MODULO")?null:MenuViewController.getInstance().getMenuId(getModuleName())), 
                                      pageView, (openPage?1:2), GBEnvironment.getInstance().serverDate(), 0, 
                                      username, GBEnvironment.getInstance().serverDate());
            MenuViewController.getInstance().savePage(menu);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(44), pageName);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(24), null);
        }
    }
}
