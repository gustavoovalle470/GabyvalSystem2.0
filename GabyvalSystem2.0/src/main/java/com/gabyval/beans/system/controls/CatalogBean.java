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
import java.util.HashMap;
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
    private HashMap<String, Integer> catalogsList; //All genders allowed.
    private String catalog_name_select;
    private AdCatalogs selectedCatalog;
    private String catalog_name; //Catalog name
    private String catalog_value; //Catalog item.
    private String catalog_value_desc; // Catalog item description.
    private boolean isNewCatalog;
    
    public CatalogBean(){
        getCatalogsList();
    }

    public String getCatalog_name_select() {
        return catalog_name_select;
    }

    public void setCatalog_name_select(String catalog_name_select) {
        this.catalog_name_select = catalog_name_select;
    }

    public HashMap<String, Integer> getCatalogsList() {
        try {
            catalogsList = CatalogController.getInstance().getCatalogsList();
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
            GBMessage.putException(ex);
        }
        return catalogsList;
    }

    public void setCatalogsList(HashMap<String, Integer> catalogsList) {
        this.catalogsList = catalogsList;
    }
    
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

    public AdCatalogs getSelectedCatalog() {
        return selectedCatalog;
    }

    public void setSelectedCatalog(AdCatalogs selectedCatalog) {
        this.selectedCatalog = selectedCatalog;
    }

    public boolean isIsNewCatalog() {
        return isNewCatalog;
    }

    public void setIsNewCatalog(boolean isNewCatalog) {
        this.isNewCatalog = isNewCatalog;
    }
     
    public void onPageEdit(RowEditEvent evt){
        AdCatalogs catalogEdit = (AdCatalogs)evt.getObject();
        catalogEdit.setRowversion(catalogEdit.getRowversion()+1);
        try {
            CatalogController.getInstance().save(catalogEdit);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(35), null);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(36), null);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(37), null);
        }
    }
    
    public void saveCatalog(){
        try {
            String catalog_dest ="";
            if(isNewCatalog){
                catalog_dest = getCatalogName();
            }else{
                catalog_dest = catalog_name.toUpperCase();
            }
            AdCatalogs catalog= new AdCatalogs(GBEnvironment.getInstance().getNextTableId("AdCatalogs"), catalog_dest, CatalogController.getInstance().getNextCatalogItemId(catalog_dest), catalog_value, catalog_value_desc, GBEnvironment.getInstance().serverDate(), 0);
            CatalogController.getInstance().save(catalog);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(38), catalog_dest+","+catalog_value);
            clearView();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(39), catalog_name.toUpperCase()+","+catalog_value);
        } catch(Exception ex){
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(39), null);
        }
    }
    
    public void clearView() {
        setCatalog_name("");
        setCatalog_value("");
        setCatalog_value_desc("");
    }

    private String getCatalogName() throws GB_Exception {
        for(String catalogName : catalogsList.keySet()){
            System.out.println("Comparando "+catalogsList.get(catalogName).toString()+" vs. "+catalog_name_select+" son inguales? "+catalogsList.get(catalogName).toString().equals(catalog_name_select));
            if(catalogsList.get(catalogName).toString().equals(catalog_name_select)){
                return catalogName;
            }
        }
        throw new GB_Exception(41);
    }
}