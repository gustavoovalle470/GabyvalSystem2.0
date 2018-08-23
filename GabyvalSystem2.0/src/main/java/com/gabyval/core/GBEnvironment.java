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
 * |   1.0   |  05/07/2017  |      GAOQ      | Creacion de la clase que realiza todas las operacion de core y provee el servicio a otros modulos.      |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  13/11/2017  |      GAOQ      | Adicion de busqueda de configuraciones por nombre, adicion metodo de busqueda de errores de sistema.    |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  30/06/2018  |      GAOQ      | Se modifica la forma como el sistema retorna un error no encontrado o si no hay conexion a la base de   |
 * |         |              |                | datos.                                                                                                  |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
*/
package com.gabyval.core;

import com.gabyval.core.commons.controllers.IdControl;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.AdCatalogs;
import com.gabyval.core.constants.CatalogEntity;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.AdError;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.module.configuration.AdModuleConfiguration;
import com.gabyval.core.time.NoWorkingDayController;
import com.gabyval.core.time.SystemDateController;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Level;

/**
 * This class define de Class environment for core utilities.
 * @author GAOQ
 * @version 1.0
 * @since  05/07/2017
 */
public class GBEnvironment {
    private final GB_Logger LOG = GB_Logger.getLogger(GBEnvironment.class);
    private static GBEnvironment instance;//Unique instance of this environment.
    
    
    /**
     * Return the instance of GABYVAL Environment core.
     * @return GBEnvironment this instance.
     */
    public static GBEnvironment getInstance(){
        if(instance == null){
            instance = new GBEnvironment();
        }
        return instance;
    }
    
    /**
     * Return the next table id for a table.
     * @param table_name String the table name.
     * @return int the next id.
     * @throws GB_Exception if;
     * <ol><li>Any exception obtain the next id</li></ol>
     */
    public int getNextTableId(String table_name) throws GB_Exception{
        LOG.debug("The tables ID was synchronized, getting the next id...");
        return IdControl.getNextId(table_name);
    }
    
    /**
     * This method return a object for module configuration value saved.
     * @param module_configuracion_id Integer, the module configuration id.
     * @return Object the module configuration value.
     * @throws GB_Exception if: SI
     */
    public Object getModuleConfiguration(int module_configuracion_id) throws GB_Exception{
        AdModuleConfiguration mc = (AdModuleConfiguration) PersistenceManager.getInstance().load(AdModuleConfiguration.class, 
                                                                                            module_configuracion_id);
        if(mc != null){
            switch(mc.getGbModuleConfigType()){
                case 1:
                    return mc.getGbModuleConfigValue();
                case 2:
                    if(mc.getGbModuleConfigValue().toUpperCase().equals("Y") || 
                       mc.getGbModuleConfigValue().toUpperCase().equals("TRUE")){
                        return true;
                    }else if(mc.getGbModuleConfigValue().toUpperCase().equals("N") || 
                       mc.getGbModuleConfigValue().toUpperCase().equals("FALSE")){
                        return false;
                    }else{
                        LOG.error("The value "+mc.getGbModuleConfigValue()+" cant recognized like a boolean expresion.");
                        throw new GB_Exception("The value "+mc.getGbModuleConfigValue()+" cant recognized like a boolean expresion.");
                    }
                case 3:
                    try{
                        return new Long(mc.getGbModuleConfigValue());
                    }catch(NumberFormatException ex){
                        LOG.error(ex);
                        throw new GB_Exception(ex);
                    }
                case 4:
                    try{
                        return new Double(mc.getGbModuleConfigValue());
                    }catch(NumberFormatException ex){
                        LOG.error(ex);
                        throw new GB_Exception(ex);
                    }
                case 5:
                    try{
                        return Integer.parseInt(mc.getGbModuleConfigValue());
                    }catch(NumberFormatException ex){
                        LOG.error(ex);
                        throw new GB_Exception(ex);
                    }
                default:
                    LOG.error("The module configuration type \""+mc.getGbModuleConfigType()+"\" cant recognized.");
                    throw new GB_Exception("The module configuration type \""+mc.getGbModuleConfigType()+"\" cant recognized.");
            }
        }else{
            throw new GB_Exception("The module configuration id "+module_configuracion_id+" is not registred in data base.");
        }
    }
    
    /**
     * This method find a module configuration by name.
     * @param module_configuracion_name String configuration´s name.
     * @return Object a value of configuration.
     * @throws GB_Exception if:
     * <ol>
     *     <li>The configuration have more than one item</li>
     *     <li>The configuration doesnt exist in database</li>
     * </ol>
     */
    public Object getModuleConfiguration(String module_configuracion_name) throws GB_Exception{
        List configurations= PersistenceManager.getInstance().runCriteria("FROM AdModuleConfiguration a WHERE a.gbModuleConfigName = '"+module_configuracion_name+"'");
        if(configurations.size() > 1){
            throw new GB_Exception("The configuration "+module_configuracion_name+" has more that one item.");
        }
        if(configurations.isEmpty()){
            throw new GB_Exception("The configuration "+module_configuracion_name+" dont exist into data base.");
        }
        AdModuleConfiguration mc = (AdModuleConfiguration) configurations.get(0);
        return getModuleConfiguration(mc.getGbModuleConfigId());
    }
    
    /**
     * Return the System date for app.
     * @return Date the actually System date.
     */
    public Date systemDate() throws GB_Exception{
        return SystemDateController.getInstance().getSystemDate();
    }
    
    /**
     * Return the System date for app.
     * @return Date the actually System date.
     */
    public Date serverDate(){
        return Calendar.getInstance().getTime();
    }
    
    /**
     * Return the last close date.
     * @return Date the last close date.
     */
    public Date getLastCloseDate() throws GB_Exception{
        return SystemDateController.getInstance().getLastClose();
    }
    
    /**
     * Return the next close date.
     * @return Date the next close date.
     */
    public Date getNextCloseDate() throws GB_Exception{
        return SystemDateController.getInstance().getNextClose();
    }
    
    /**
     * Return the next working date.
     * @return Date the next working date.
     * @throws GB_Exception if:
     * <ol><li>Any exception obtaining the next working date.</li></ol>
     */
    public Date getNextWorkingDate() throws GB_Exception{
        return SystemDateController.getInstance().getNextWorkingDate();
    }
    
    /**
     * Return if the date is working day.
     * @param day Date date to find.
     * @return boolean, true if the date is a working day, false otherwise.
     * @throws GB_Exception if:
     * <ol><li>Any exception obtaining the next no working date.</li></ol>
     */
    public boolean isAWorkingDay(Date day) throws GB_Exception{
        return !NoWorkingDayController.isNoWorkingDay(day);
    }
    
    /**
     * Return a catalog from data base.
     * @param catalog_name String the catalog name.
     * @return CatalogEntity a catalog entity.
     */
    public CatalogEntity getCatalog(String catalog_name){
        CatalogEntity catalog = new CatalogEntity();
        try {
            List objects = PersistenceManager.getInstance().runCriteria("FROM AdCatalogs c WHERE c.gbCatalogName = '"+catalog_name+"'");
            for(Object o : objects){
                AdCatalogs c = (AdCatalogs) o;
                catalog.putValues(c.getGbCatagogItemId(), c.getGbCatalogItem());
            }
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
        }
        return catalog;
    }
    
    
    /**
     * This method find and replacing the message error.
     * @param IdError int id for message into database.
     * @return String the string whit error message.
     */
    public AdError getError(int IdError) {
        AdError error = null;
        try {
            error = (AdError) PersistenceManager.getInstance().load(AdError.class, IdError);
            PersistenceManager.getInstance().refresh(error);
        } catch (GB_Exception ex) {
            LOG.error(ex);
        } finally{
            if (error == null){
                error = new AdError(0, "ERROR INESPERADO. Se ha producido un error inesperado en la aplicación y el sistema no se pudo recuperar. Si el problema persiste comuniquese con su administrador.", Calendar.getInstance().getTime(), 0);
                error.setGbErrorLevel("FATAL");
                LOG.error("El sistema no pudo encontrar el error con identificador "+IdError+" o no se pudo establecer la conexión a la base de datos para obtenerlo.");
            }
            return error;
        }
    }
    
    public String replaceMessage(String original, String replace) {
        if(replace != null){
            String[] data = replace.split(",");
            for(String info: data){
                original = original.replace(GB_CommonStrConstants.ERROR_RPLCE, info);
            }
        }
        return original;
    }
    
    public String criptPwd(String pwd){
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(GB_CommonStrConstants.PUBLIC_APP_KEY.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = pwd.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
    
    public Date getServerDate() throws GB_Exception{
        return SystemDateController.getInstance().getServerDate();
    }
    
    public Date getSystemDate() throws GB_Exception{
        return SystemDateController.getInstance().getSystemDate();
    }
    
    public String getDateFormated(Date date, String format){
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String dateFormated = formater.format(date);
        return dateFormated.replace("$", "de");
    }
    
    public void SystemPause(boolean isPaused) throws GB_Exception{
        LOG.info("The system was changing the state to "+(isPaused? "paused":"unpaused"));
        try{
            SystemDateController.getInstance().pauseUnPauseSys(isPaused);
        }catch (Exception e){
            LOG.fatal(e);
            throw new GB_Exception("Error al tratar de pausar el sistema.");
        }
        LOG.info("The "+(isPaused? "paused":"unpaused")+" was finished.");
    }
    
    public void changeLogLevel(String logLevel){
        LOG.info("The log is changing its level... new level "+logLevel);
        System.out.println("LEVEL EN EL ENVIROMENT: "+logLevel);
        GB_Logger.GB_setLevel(Level.toLevel(logLevel));
        LOG.info("The log was changing to "+logLevel);
    }
    
    public boolean isSystemPaused() throws GB_Exception{
        return SystemDateController.getInstance().isSystemPaused();
    }

    public boolean isValidPassword(String password, String username) throws GB_Exception {
        LOG.debug("Start password validation.");
        if(isValidContent(password) && noUsePassword(password, username)){
            LOG.debug("The password accomplish all policies.");
            return true;
        }
        LOG.debug("The password doesn´t accomplish all policies.");
        return false;
    }

    private boolean isValidContent(String password) throws GB_Exception {
        LOG.debug("Loading the content policies...");
        String regex ="^";
        if((boolean)getModuleConfiguration(GB_CommonStrConstants.PASS_RQMYU)){
            LOG.debug("The password should contain at least a uppercase.");
            regex = regex+GB_CommonStrConstants.MAYUS_REG;
        }
        if((boolean)getModuleConfiguration(GB_CommonStrConstants.PASS_RQMIN)){
            LOG.debug("The password should contain at least a lowercase.");
            regex = regex+GB_CommonStrConstants.MINUS_REG;
        }
        if((boolean)getModuleConfiguration(GB_CommonStrConstants.PASS_RQNUM)){
            LOG.debug("The password should contain at least a digit.");
            regex = regex+GB_CommonStrConstants.DIGIT_REG;
        }
        if((boolean)getModuleConfiguration(GB_CommonStrConstants.PASS_RQSPE)){
            LOG.debug("The password should contain at least a special character.");
            regex = regex+GB_CommonStrConstants.SPCHR_REG;
        }
        regex = regex+(GB_CommonStrConstants.LNGTH_REG.replace("#", ""+getModuleConfiguration(GB_CommonStrConstants.PASS_LNGTH)));
        LOG.debug("The password should contain at least "+getModuleConfiguration(GB_CommonStrConstants.PASS_LNGTH)+" characters.");
        LOG.debug("The password passed the content validations? "+password.matches(regex));
        return password.matches(regex);
    }

    private boolean noUsePassword(String password, String username) throws GB_Exception {
        LOG.debug("Loading store policies...");
        LOG.debug("The password cannot repeat in "+getModuleConfiguration(GB_CommonStrConstants.PASS_LNGTH)+" changes.");
        LOG.debug("Searching if the password was used by user "+username);
        PersistenceManager.getInstance().runCriteria(username);
        return true;
    }
}
