/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.commons;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.AdCatalogs;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.module.configuration.AdModuleConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gusta
 */
public class ConfigurationController {
    private static final GB_Logger LOG = GB_Logger.getLogger(ConfigurationController.class);
    private static ConfigurationController instance;
    
    public static ConfigurationController getInstance(){
        if(instance == null){
            instance = new ConfigurationController();
        }
        return instance;
    }
    
    public List<AdModuleConfiguration> getAllCatalogs() throws GB_Exception{
        try {
            return PersistenceManager.getInstance().runCriteria("FROM AdModuleConfiguration A ORDER BY A.gbModuleConfigId");
        } catch (GB_Exception ex) {
            LOG.error(ex);
            throw ex;
        }
    }

    public int getNextCatalogItemId(String catalog_name) {
        int nextCatalogItemId = 0;
        try {
            List l = PersistenceManager.getInstance().runCriteria("SELECT NVL(MAX(A.gbCatagogItemId), 0) AS MAX_ID FROM AdCatalogs A WHERE A.gbCatalogName = '"+catalog_name+"'");
            if(!l.isEmpty()){
                nextCatalogItemId = Integer.parseInt(l.get(0).toString());
                nextCatalogItemId++;
            }
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        return nextCatalogItemId;
    }
    
    public void save(AdModuleConfiguration conf) throws GB_Exception{
        try {
            PersistenceManager.getInstance().save(conf);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            throw new GB_Exception("");
        }
    }
}
