/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.commons;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.AdError;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.util.List;

/**
 *
 * @author gusta
 */
public class ErrorController {
    private static final GB_Logger LOG = GB_Logger.getLogger(ErrorController.class);
    private static ErrorController instance;
    
    public static ErrorController getInstance(){
        if(instance == null){
            instance = new ErrorController();
        }
        return instance;
    }
    
    public List<AdError> getAllErrors() throws GB_Exception{
        try {
            return PersistenceManager.getInstance().runCriteria("FROM AdError A ORDER BY A.gbErrorId");
        } catch (GB_Exception ex) {
            LOG.error(ex);
            throw ex;
        }
    }

    public void save(AdError error) throws GB_Exception {
        PersistenceManager.getInstance().save(error);
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
}
