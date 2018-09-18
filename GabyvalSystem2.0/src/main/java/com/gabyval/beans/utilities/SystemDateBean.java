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
 * |   1.0   |  26/08/2017  |      GAOQ      | Creacion del manejador de autenticacion.                                                                |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.utilities;

import com.gabyval.controller.commons.CatalogController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.time.SystemDateController;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * This class control the access to application.
 * @author GAOQ.
 * @version 1.0
 * @since 26/08/2017
 */
@Named(value = "SystemDateBean")
@RequestScoped
public class SystemDateBean implements Serializable{

    private static final GB_Logger LOG = GB_Logger.getLogger(SystemDateBean.class);
    private String paused;
    private HashMap<String, Integer> systemState;
    private String app_version;

    public SystemDateBean() {
        app_version = "1.6";
        /**systemState = GBEnvironment.getInstance().getCatalog("SYSTEM_PAUSED").getAllCatalog();
        if(GBEnvironment.getInstance().isSystemPaused()){
            paused = ""+CatalogController.getInstance().decode(systemState, "PAUSADO");
        }else{
            paused = ""+CatalogController.getInstance().decode(systemState, "CORRIENDO");
        }*/
    }
        
    public String getDateFormat() {
        try {
            return (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.PROPERTY_DATE_FORMAT);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putException(ex);
            return GB_CommonStrConstants.DEFAULT_DATE_FORMAT;
        }
    }
    
    public String getDateTimeFormat() {
        try {
            return (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.PROPERTY_DATETIME_FORMAT);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return GB_CommonStrConstants.DEFAULT_DATETIME_FORMAT;
        }
    }
    
    public String getDateTimeFormatFull() {
        try {
            return (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.PROPERTY_DATETIME_FORMAT_FULL);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return GB_CommonStrConstants.DEFAULT_DATETIME_FORMAT;
        }
    }

    public String getPaused() {
        return paused;
    }

    public void setPaused(String paused) {
        this.paused = paused;
    }

    public HashMap<String, Integer> getSystemState() {
        return systemState;
    }

    public void setSystemState(HashMap<String, Integer> systemState) {
        this.systemState = systemState;
    }

    public void refreshGlobalDate(){
        getSystemDate();
    }
    
    public String getSystemDate() {
        try {
            return GBEnvironment.getInstance().getDateFormated(
                    SystemDateController.getInstance().getSystemDate(),
                    getDateTimeFormatFull());
        } catch (GB_Exception ex) {
            Logger.getLogger(SystemDateBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Fecha y hora no disponible.";
    }
    
    public String getSystemDateConfiguration() {
        try {
            return GBEnvironment.getInstance().getDateFormated(
                    SystemDateController.getInstance().getSystemDate(),
                    getDateTimeFormat());
        } catch (GB_Exception ex) {
            Logger.getLogger(SystemDateBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return "";
        }
    }
    public String getServerDate() {
        try {
            return GBEnvironment.getInstance().getDateFormated(
                    SystemDateController.getInstance().getServerDate(),
                    getDateTimeFormat());
        } catch (GB_Exception ex) {
            Logger.getLogger(SystemDateBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return "";
        }
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
