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

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.time.SystemDateController;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This class control the access to application.
 * @author GAOQ.
 * @version 1.0
 * @since 26/08/2017
 */
@Named(value = "SystemInfoBean")
@SessionScoped
public class SystemInfoBean implements Serializable{

    private static final GB_Logger LOG = GB_Logger.getLogger(SystemInfoBean.class);
    private String app_version;
    private final String dateTimeFormatFull;
    private final String theme;

    public SystemInfoBean() {
        app_version = getAppVersion();
        dateTimeFormatFull = getDateTimeFormatFull();
        theme = getApplicationTheme();
    }
    
    public String getDateTimeFormatFull() {
        try {
            return (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.PROPERTY_DATETIME_FORMAT_FULL);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return GB_CommonStrConstants.DEFAULT_DATETIME_FORMAT;
        }
    }
    
    public String getAppVersion(){
        try {
            return (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.GB_APP_VERSION);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return "1.0 BETA";
        }
    }

    public String getTheme() {
        return theme;
    }

    public String getSystemDate() {
        try {
            return GBEnvironment.getInstance().getDateFormated(
                    SystemDateController.getInstance().getSystemDate(),
                    dateTimeFormatFull);
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        return "Fecha y hora no disponible.";
    }
    
    public String getApp_version() {
        return app_version;
    }

    private String getApplicationTheme() {
        try {
            return (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.APP_THEME);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return "layout-orange-indigo.css";
        }
    }
}
