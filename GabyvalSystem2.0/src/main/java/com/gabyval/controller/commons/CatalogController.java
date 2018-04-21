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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gusta
 */
public class CatalogController {
    private static final GB_Logger LOG = GB_Logger.getLogger(CatalogController.class);
    private static CatalogController instance;
    
    public static CatalogController getInstance(){
        if(instance == null){
            instance = new CatalogController();
        }
        return instance;
    }
    
    public String encode(HashMap<String, Integer> catalog, int value){
        for(String id: catalog.keySet()){
            if(catalog.get(id).equals(value)){
                return id;
            }
        }
        return "";
    }
    
    public int decode(HashMap<String, Integer> catalog, String id){
        return catalog.get(id);
    }
    
    public List<AdCatalogs> getAllCatalogs() throws GB_Exception{
        try {
            return PersistenceManager.getInstance().runCriteria("FROM AdCatalogs A ORDER BY A.gbCatalogName, A.gbCatagogItemId");
        } catch (GB_Exception ex) {
            LOG.error(ex);
            throw ex;
        }
    }

    public void save(AdCatalogs catalogEdit) throws GB_Exception {
        PersistenceManager.getInstance().save(catalogEdit);
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
