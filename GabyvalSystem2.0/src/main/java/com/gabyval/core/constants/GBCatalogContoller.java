/* *****************************************************************************************************************************************************
 * *****************************************************************************************************************************************************
 * *****************************************************************************************************************************************************
 * *******************************                                                                              ****************************************
 * *******************************  ********** ********** ******    **      ** **      ** ********** **         ****************************************
 * *******************************  ********** **********  ******   **      ** **      ** ********** **         ****************************************
 * *******************************  **         **      **  **  **   **      ** **      ** **      ** **         ****************************************
 * *******************************  **         **      **  **  **   **      ** **      ** **      ** **         ****************************************
 * *******************************  **         **      **  ******    **    **  **      ** **      ** **         ****************************************
 * *******************************  **    **** **********  *******    **  **   **      ** ********** **         ****************************************
 * *******************************  **    **** **********  ********    ****    **      ** ********** **         ****************************************
 * *******************************  **      ** **      **  **    **     **     **      ** **      ** **         ****************************************
 * *******************************  **      ** **      **  **    **     **      **    **  **      ** **         ****************************************
 * *******************************  **      ** **      **  **   **      **       **  **   **      ** **         ****************************************
 * *******************************  ********** **      **  **  **       **        ****    **      ** ********** ****************************************
 * *******************************  ********** **      ** ******        **         **     **      ** ********** ****************************************
 * *****************************************************************************************************************************************************
 * *****************************************************************************************************************************************************
 * *****************************************************************************************************************************************************
 * |---------------------------------------------------------------------------------------------------------------------------------------------------|
 * |                                                        Control de versiones                                                                       |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * | Version |    Fecha     |  Responsable   |                                                  Comentarios                                            |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.0   |  09/07/2017  |      GAOQ      | Creacion de la clase controladora de los catalogos.                                                     |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.constants;

import com.gabyval.core.commons.controllers.IdControl;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class controlled the access to catalogs.
 * @author GAOQ
 * @version 1.0
 * @since 09/07/2017
 */
public class GBCatalogContoller {
    private static final GB_Logger LOG = GB_Logger.getLogger(GBCatalogContoller.class);//Central log.
    private static GBCatalogContoller instance;//Instance for this controller.
    
    /**
     * Return the instance for this controller.
     * @return GBCatalog the instance.
     */
    public GBCatalogContoller getInstance(){
        if(instance == null){
            instance = new GBCatalogContoller();
        }
        return instance;
    }
    
    /**
     * This method charge all items for a catalog.
     * @param catalog_name String catalog name.
     * @return List whit all item for catalog.
     */
    private List loadCatalog(String catalog_name){
        try{
            return PersistenceManager.getInstance().runCriteria("SELECT * FROM AdCatalogs a WHERE a.gbCatalogName = '"+catalog_name+"'");
        }catch(GB_Exception ex){
            LOG.fatal(ex);
            return null;
        }
    }
    
    /**
     * Assemble the catalog for return.
     * @param catalog_name String catalog name to assemble.
     * @return GBCatalog the catalog.
     * @throws GB_Exception if:
     * <ol><li>Any exception into data base transaction</li></ol>
     */
    public GBCatalog getCatalog(String catalog_name) throws GB_Exception{
        GBCatalog catalog = new GBCatalog(catalog_name, null);
        ArrayList<GBCatalogItem> items = new ArrayList<>();
        LOG.debug("Starting to charge catalog: "+catalog_name);
        LOG.debug("Loading database catalog");
        List item_db = loadCatalog(catalog_name);
        if(item_db == null){
            LOG.fatal("The catalog "+catalog_name+" cant loaded from data base.");
            throw new GB_Exception("The catalog cant charge.");
        }
        LOG.debug("The catalog container is assembling");
        for(Object c: item_db){
            AdCatalogs cata_db = (AdCatalogs)c;
            LOG.debug("Put a item into catalog:"+catalog_name+", item id: "+cata_db.getGbCatagogItemId()+", the catalog item: "+cata_db.getGbCatalogItem());
            GBCatalogItem item = new GBCatalogItem(cata_db.getGbCatagogItemId(), cata_db.getGbCatalogItem(), cata_db.getGbCatalogDescription());
            items.add(item);
        }
        if(items.isEmpty()){
            LOG.fatal("The catalog hasn´t items.");
            throw new GB_Exception("The catalog is empty.");
        }
        catalog.setItems(items);
        return catalog;
    }
    
    /**
     * Save a catalog (or update the same).
     * @param catalog GBCatalog the catalog to save.
     * @throws GB_Exception if:
     * <ol><li>Any exception into data base transaction</li></ol>
     */
    public void saveCatalog(GBCatalog catalog) throws GB_Exception{
        for(GBCatalogItem item : catalog.getItems()){
            AdCatalogs cata = new AdCatalogs(IdControl.getNextId("GABYVAL.AD_CATALOGS"), catalog.getCatalog_name(), 
                                             item.getItemId(), item.getItem(), item.getDescription(), 
                                             Calendar.getInstance().getTime(), 0);
            PersistenceManager.getInstance().save(cata);
        }
    }
}