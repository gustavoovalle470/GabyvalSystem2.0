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
 * |   1.0   |  24/02/2018  |      GAOQ      | Creacion del bean que controla la adicion o modificacion de catalogos.                                  |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.system.controls;

import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.commons.CatalogController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.AdCatalogs;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.RowEditEvent;

/**
 * This class controlled the add or modify system catalogs.
 * @author GAOQ
 * @version 1.0
 * @since 24/02/2018
 */
@ManagedBean(name = "CatalogBean")
@SessionScoped
public class CatalogBean implements Serializable{
    
    private static final GB_Logger LOG = GB_Logger.getLogger(CatalogBean.class); //Log for this class.
    private ArrayList<AdCatalogs> catalogs; //Catalog list.
    private String catalog_name; //Catalog name
    private String catalog_value; //Catalog item.
    private String catalog_value_desc; // Catalog item description.
    private String catalog_name_st; //Catalog name status.
    private String catalog_item_st; //Catalog item status.

    /**
     * Obtain all catalog for the database.
     * @return ArrayList All catalogs from database.
     */
    public ArrayList<AdCatalogs> getCatalogs() {
        try {
            catalogs = (ArrayList<AdCatalogs>) CatalogController.getInstance().getAllCatalogs();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            catalogs=new ArrayList<>();
        }
        return catalogs;
    }

    public void setCatalogs(ArrayList<AdCatalogs> catalogs) {
        this.catalogs = catalogs;
    }

    public String getCatalog_name() {
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public String getCatalog_value() {
        return catalog_value;
    }

    public void setCatalog_value(String catalog_value) {
        this.catalog_value = catalog_value;
    }

    public String getCatalog_value_desc() {
        return catalog_value_desc;
    }

    public void setCatalog_value_desc(String catalog_value_desc) {
        this.catalog_value_desc = catalog_value_desc;
    }

    public String getCatalog_name_st() {
        return catalog_name_st;
    }

    public void setCatalog_name_st(String catalog_name_st) {
        this.catalog_name_st = catalog_name_st;
    }

    public String getCatalog_item_st() {
        return catalog_item_st;
    }

    public void setCatalog_item_st(String catalog_item_st) {
        this.catalog_item_st = catalog_item_st;
    }
    
    public void onPageEdit(RowEditEvent evt){
        AdCatalogs catalogEdit = (AdCatalogs)evt.getObject();
        catalogEdit.setRowversion(catalogEdit.getRowversion()+1);
        try {
            CatalogController.getInstance().save(catalogEdit);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(54), null);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(55), null);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(53), null);
        }
    }
    
    public void saveCatalog(){
        try {
            AdCatalogs catalog= new AdCatalogs(GBEnvironment.getInstance().getNextTableId("AdCatalogs"), catalog_name.toUpperCase(), CatalogController.getInstance().getNextCatalogItemId(catalog_name.toUpperCase()), catalog_value.toUpperCase(), catalog_value_desc, GBEnvironment.getInstance().serverDate(), 0);
            CatalogController.getInstance().save(catalog);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(52), catalog_name.toUpperCase()+","+catalog_value.toUpperCase());
            clearView();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(51), catalog_name.toUpperCase()+","+catalog_value.toUpperCase());
        }
    }
    
    public void validateCatalog(){
        if(catalog_name.toUpperCase().contains(" ")){
            setCatalog_name_st("Nombre no válido.");
        }
        else if(GBEnvironment.getInstance().getCatalog(catalog_name.toUpperCase()).catalogExist()){
            setCatalog_name_st("Edición de catalogo");
        }else{
            setCatalog_name_st("Creación de catalogo");
        }
    }
    
    public void validateCatalogItem(){
        if(GBEnvironment.getInstance().getCatalog(catalog_name.toUpperCase()).getAllCatalog().containsKey(catalog_value.toUpperCase())){
            setCatalog_item_st("El item ya existe");
        }else{
            setCatalog_item_st("Item correcto");
        }
    }

    public void clearView() {
        setCatalog_item_st("");
        setCatalog_name_st("");
        setCatalog_name("");
        setCatalog_value("");
        setCatalog_value_desc("");
    }
}