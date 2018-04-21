/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.system.controls;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.commons.CatalogController;
import com.gabyval.controller.commons.ConfigurationController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.module.configuration.AdModuleConfiguration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author gusta
 */
@RequestScoped
@Named("ConfigurationBean")
public class ConfigurationBean implements Serializable{
    private static final GB_Logger LOG = GB_Logger.getLogger(ConfigurationBean.class); //Log for this class.
    private ArrayList<AdModuleConfiguration> configurations;
    private String confType;
    private HashMap<String, Integer> confTypes; //All ID types allowed.
    private String username;
    private String MCName;
    private String MCValue;
    private String MCDesc;

    public ConfigurationBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            confTypes = GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_TYPE_CONF).getAllCatalog();
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }

    public ArrayList<AdModuleConfiguration> getConfigurations() {
        configurations = new ArrayList<>();
        try {
            configurations = (ArrayList<AdModuleConfiguration>) ConfigurationController.getInstance().getAllCatalogs();
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        return configurations;
    }

    public void setConfigurations(ArrayList<AdModuleConfiguration> configurations) {
        this.configurations = configurations;
    }

    public String getConfType() {
        return confType;
    }

    public void setConfType(String confType) {
        this.confType = confType;
    }

    public HashMap<String, Integer> getConfTypes() {
        return confTypes;
    }

    public void setConfTypes(HashMap<String, Integer> confTypes) {
        this.confTypes = confTypes;
    }
    
    public String getMCType(int typeId){
        return CatalogController.getInstance().encode(confTypes, typeId);
    }

    public String getMCName() {
        return MCName;
    }

    public void setMCName(String MCName) {
        this.MCName = MCName;
    }

    public String getMCValue() {
        return MCValue;
    }

    public void setMCValue(String MCValue) {
        this.MCValue = MCValue;
    }

    public String getMCDesc() {
        return MCDesc;
    }

    public void setMCDesc(String MCDesc) {
        this.MCDesc = MCDesc;
    }
    
    public void onPageEdit(RowEditEvent evt){
        AdModuleConfiguration configEdit = (AdModuleConfiguration)evt.getObject();
        configEdit.setRowversion(configEdit.getRowversion()+1);
        try {
            ConfigurationController.getInstance().save(configEdit);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(61), null);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(61), configEdit.getGbModuleConfigName());
        }
    }
    
    public void save(){
        try {
            AdModuleConfiguration config = new AdModuleConfiguration(
                    GBEnvironment.getInstance().getNextTableId("AdModuleConfiguration"),
                    MCName, Integer.parseInt(confType), MCValue, MCDesc,
                    GBEnvironment.getInstance().getServerDate(), 0);
            ConfigurationController.getInstance().save(config);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(63), config.getGbModuleConfigName());
            clearBean();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(64), null);
        }
        
    }
    
    public void clearBean(){
        MCName = "";
        confType=""+1;
        MCValue="";
        MCDesc="";
    }
}
