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
 * |   1.0   |  23/04/2017  |      GAOQ      | Creacion del controlador de controles de fecha del sistema                                              |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  29/05/2017  |      GAOQ      | Se modifica la accesibilidad de los metodos, se crea la instancia unica de este controlador.            |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  13/06/2017  |      GAOQ      | Se modifica la forma como se comportara el Scheduler dependiendo de la informacion de los parametros de |
 * |         |              |                | tiempo del sistema.                                                                                     |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.3   |  19/06/2017  |      GAOQ      | Se elimina los rasgos del Scheduler de esta clase. Se crea un metodo que permita la recarga del control.|
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.4   |  07/09/2017  |      GAOQ      | Adicion de metodo que permite retornar una fecha cualquiera formateada segun la mascara del sistema.    |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.5   |  13/11/2017  |      GAOQ      | Adicion de modulo de configuracion para parametria de formatos de fecha.                                |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.time;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.IO.GB_IOController;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class control all fields status of system date.
 * @author GAOQ
 * @version 1.4
 * @since 23/04/2017
 */
public final class SystemDateController {
    
    private static SystemDateController instance; //Instance of system controler.
    private static AdSystemControl control; //Instance of system date entity.
    private static final GB_Logger LOG = GB_Logger.getLogger(SystemDateController.class); //Log central of this class.
    private static Date systemDate;
    
    public SystemDateController(){
        try {
            control = (AdSystemControl)PersistenceManager.getInstance().load(AdSystemControl.class, 1);
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        systemDate = ChargeSystemDate();
    }
    
    /**
     * Return the instance of this controller.
     * @return SystemDateController this instance.
     */
    public static SystemDateController getInstance(){
        if(instance == null){
            instance = new  SystemDateController();
        }
        return instance;
    }
    
    /**
     * Indicate if system is paused or not.
     * @return boolean true if system is paused, false otherwise.
     */
    public boolean isSystemPaused(){
        LOG.debug("Verify if the system is paused.");
        if(control.getGbSystemPause().equals("Y")){
            LOG.debug("The system is pasued actually.");
            return true;
        }
        LOG.debug("The system is not paused actually.");
        return false;
    }
    
    /**
     * Return the next date to close.
     * @return java.util.Date the next date close.
     */
    public Date getNextClose(){
        LOG.debug("Getting the next close date");
        return control.getGbNextCloseDt();
    }
    
    /**
     * Return the last date close.
     * @return java.util.Date the last close date.
     */
    public Date getLastClose(){
        LOG.debug("Getting the last close date");
        return control.getGbLastCloseDt();
    }
    
    /**
     * Return the system date.
     * @return java.util.Date the system date.
     */
    public Date getSystemDate(){
        LOG.debug("Getting the system date");
        return systemDate;
    }
    
    /**
     * This method synchronizes the system date.
     * @throws com.gabyval.core.exception.GB_Exception if:
     * <ol><li>The persistence failed in the save operation</li></ol>
     */
    public void synchronizedDate() throws GB_Exception{
        LOG.debug("The system is establishing the new System date. Syncing the system date with the server date.");
        systemDate = Calendar.getInstance().getTime();
        control.setGbSystemPause("N");
        LOG.debug("The system is saving the new System date.");
        PersistenceManager.getInstance().save(control);
    }
    
    /**
     * Changes the system date to customized date.
     * @param newSystemDate Date the customized date.
     * @param date String indicate that date will be change.
     * @throws com.gabyval.core.exception.GB_Exception if:
     * <ol><li>The string to set is not found</li></ol>
     */
    public void changeDate(Date newSystemDate, String date) throws GB_Exception{
        LOG.debug("The system is establishing the new System date. Customized date is setting...");
        switch(date){
            case Controls.SYSTEM_DATE:
                systemDate = newSystemDate;
                break;
            case Controls.LAST_CLOSE:
                control.setGbLastCloseDt(newSystemDate);
                PersistenceManager.getInstance().update(control);
                break;
            case Controls.NEXT_CLOSE:
                control.setGbNextCloseDt(newSystemDate);
                PersistenceManager.getInstance().update(control);
                break;
            default:
                throw new GB_Exception(5, date);
        }
        LOG.debug("The system was setting whit Customized date");
    }
    
    
    
    /**
     * Pause or despauses the system.
     * @param pause String flag, pause or despausese.
     * @throws com.gabyval.core.exception.GB_Exception if:
     * <ol><li>The string to set is not found</li></ol>
     */
    public void pauseUnPauseSys(boolean pause) throws GB_Exception{
        LOG.debug("The system is changed to...");
        if(pause){
            LOG.debug("Paused");
            control.setGbSystemPause("Y");
            GB_IOController.wirteConf(GB_CommonStrConstants.SYS_PARAMS, getSysDate(), "=");
        }else{
            LOG.debug("Despause");
            control.setGbSystemPause("N");
            systemDate = ChargeSystemDate();
        }
        PersistenceManager.getInstance().update(control);
    }
    
    /**
     * Calculate the next working date
     * @return Date the next working date
     * @throws com.gabyval.core.exception.GB_Exception if:
     * <ol><li>The string to set is not found</li></ol>
     */
    public Date getNextWorkingDate() throws GB_Exception{
        Calendar c = Calendar.getInstance();
        c.setTime(control.getGbLastCloseDt());
        while(!NoWorkingDayController.isNoWorkingDay(c.getTime())){
            c.add(Calendar.DAY_OF_YEAR, 1);
        }
        return c.getTime();
    }

    /**
     * Charge the system date from file of properties.
     * @return Date the actual system date save into the file properties.
     */
    private Date ChargeSystemDate() {
        int cont = 0;
        int dateParams[] = new int[6];
        for(String i : GB_IOController.getConfiguration(GB_CommonStrConstants.SYS_PARAMS, "=")
                .get(GB_CommonStrConstants.SYS_DATE_KEY).split("/")){
            dateParams[cont] = Integer.parseInt(i.trim());
            cont++;
        }
        Calendar aux = Calendar.getInstance();
        aux.set(dateParams[0], dateParams[1]-1, dateParams[2], dateParams[3], dateParams[4], dateParams[5]);
        return aux.getTime();
    }

    /**
     * This method return the string that will be save into property file.
     * @return String of date.
     */
    private HashMap<String, String> getSysDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(systemDate);
        int dateParams[] = new int[6];
        dateParams[0] = c.get(Calendar.YEAR);
        dateParams[1] = c.get(Calendar.MONTH);
        dateParams[2] = c.get(Calendar.DAY_OF_MONTH);
        dateParams[3] = c.get(Calendar.HOUR_OF_DAY);
        dateParams[4] = c.get(Calendar.MINUTE);
        dateParams[5] = c.get(Calendar.SECOND);
        HashMap<String, String> map = new HashMap<>();
        map.put(GB_CommonStrConstants.SYS_DATE_KEY, ""+dateParams[0]+"/"+dateParams[1]+"/"+dateParams[2]+"/"+dateParams[3]+"/"+dateParams[4]+"/"+dateParams[5]);
        return map;
    }
    
    /**
     * This method update the control from data base.
     */
    public void refreshControl(){
        try {
            PersistenceManager.getInstance().refresh(control);
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
        }
    }
    
    /**
     * Return a formated date into string.
     * @param date the date to format.
     * @return String the date formated.
     */
    public String getDateFormated(Date date){
        String format;
        try{
            format = (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.PROPERTY_DATE_FORMAT);
        }catch(GB_Exception ex){
            LOG.error(ex);
            format=GB_CommonStrConstants.DEFAULT_DATE_FORMAT;
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }
    
    /**
     * Return a formated date time into string.
     * @param date the date to format.
     * @return String the date time formated.
     */
    public String getDatetimeFormated(Date date){
        String format;
        try{
            format = (String) GBEnvironment.getInstance().getModuleConfiguration(GB_CommonStrConstants.PROPERTY_DATETIME_FORMAT);
        }catch(GB_Exception ex){
            LOG.error(ex);
            format=GB_CommonStrConstants.DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }
    
    public Date getServerDate(){
        return Calendar.getInstance().getTime();
    }
}