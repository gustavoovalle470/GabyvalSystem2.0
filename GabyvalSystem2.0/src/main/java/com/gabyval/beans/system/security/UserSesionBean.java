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
 * |   1.0   |  13/11/2017  |      GAOQ      | Creacion del bean de manejo de sesion de usuario.                                                       |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  22/08/2018  |      GAOQ      | Se modifica la forma como se cambia la contraseña bajo demanda. Correccion de la navegacion.            |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  28/10/2018  |      GAOQ      | Se agrega marcador para cierre de los dialogos.                                                         |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.system.security;

import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.user.UserController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.time.SystemDateController;
import com.gabyval.persistence.user.AdUsers;
import javax.inject.*;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


/**
 * This class contol the user session into the aplication.
 * @author GAOQ
 * @version 1.2
 * @since 13/11/2017
 */
@Named(value = "UserSesionBean")
@SessionScoped
public class UserSesionBean implements Serializable {
    
    private static final GB_Logger LOG = GB_Logger.getLogger(UserSesionBean.class); //The log for this class.
    private String username;   //Username.
    private String changePass1;// For change, the first password.
    private String changePass2; //For change, the second password. Confirnation.
    private String actualPass;  // Actual password for change by demand.
    private AdUsers user;       //User entity.
    private boolean ChgExpPwdSucess = false;
    
    /**
     * Creates a new instance of UserSesionBean
     */
    public UserSesionBean() {
        putUserName();
    }

    /**
     * Return the date formated.
     * @param date Date to format.
     * @return String date formated.
     */
    public String getFormatDate(Date date){
        try {
            return SystemDateController.getInstance().getDateFormated(date);
        } catch (GB_Exception ex) {
            Logger.getLogger(UserSesionBean.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    /**
     * Return the user entity.
     * @return AdUsers the user entity.
     */
    public AdUsers getUser() {
        return user;
    }

    /**
     * Change the user entity.
     * @param user AdUsers the new user entity.
     */
    public void setUser(AdUsers user) {
        this.user = user;
    }
 
    /**
     * For change, return the first entry password.
     * @return String the first entry password.
     */
    public String getChangePass1() {
        return changePass1;
    }

    /**
     * This method return the actual pass.
     * @return String actual pass.
     */
    public String getActualPass() {
        return actualPass;
    }

    /**
     * This method allow change the actual pass.
     * @param actualPass String the new actual pass.
     */
    public void setActualPass(String actualPass) {
        this.actualPass = actualPass;
    }
    
    /**
     * Modify the first entry password.
     * @param ChangePass1 String the first entry password.
     */
    public void setChangePass1(String ChangePass1) {
        this.changePass1 = ChangePass1;
    }

        /**
     * For change, return the second entry password.
     * @return String the second entry password.
     */
    public String getChangePass2() {
        return changePass2;
    }

    /**
     * Modify the second entry password.
     * @param ChangePass2 String the second entry password.
     */
    public void setChangePass2(String ChangePass2) {
        this.changePass2 = ChangePass2;
    }
    
    /**
     * This method calculate the next date to expired password.
     * @param date the start date for calculate.
     * @return String next expired password.
     */
    public String getNextExpirePwd(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(user.getGbLastPwdXgeDt());
        c.add(Calendar.MONTH, 1);
        return getFormatDate(c.getTime());
    }
    
    /**
     * This method evaluate if the password should be change.
     * @return true if the password is required for change, false otherwise.
     */
    public boolean isNeedChangePassword(){
        try {
            return UserController.getInstance().isNeedChangePasswor(username);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            return false;
        }
    }

    /**
     * Return the username.
     * @return String the user name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Change the username.
     * @param username String the new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isChgExpPwdSucess() {
        return ChgExpPwdSucess;
    }

    public void setChgExpPwdSucess(boolean ChgExpPwdSucess) {
        this.ChgExpPwdSucess = ChgExpPwdSucess;
    }
    
    /**
     * This method evaluate and change de password.
     * @return String the next view to navigate.
     */
    public String changePassword(){
        ChgExpPwdSucess = false;
        try{
            if(GBEnvironment.getInstance().criptPwd(actualPass).equals(user.getGbPassword())){
                if(isValidatePassword()){
                    UserController.getInstance().changePassword(username, GBEnvironment.getInstance().criptPwd(changePass1));
                    GBMessage.putMessage(GBEnvironment.getInstance().getError(31), null);
                    ChgExpPwdSucess = true;
                    return logout();
                }
            } else {
                GBMessage.putMessage(GBEnvironment.getInstance().getError(32), null);
            }
        } catch (GB_Exception ex) {
            cleanPwdFields();
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(33), null);
        }
        cleanPwdFields();
        return null;
    }
    
    /**
     * This method evaluate and change de password when this expire.
     * @return String the next view to navigate.
     */
    public String changePasswordExpire(){
        ChgExpPwdSucess = false;
        try{
            if(isValidatePassword()){
                UserController.getInstance().changePassword(username, GBEnvironment.getInstance().criptPwd(changePass1));
                GBMessage.putMessage(GBEnvironment.getInstance().getError(31), null);
                ChgExpPwdSucess = true;
                return logout();
            }
        } catch (GB_Exception ex) {
            cleanPwdFields();
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(33), null);
        }
        cleanPwdFields();
        return null;
    }
    
    /**
     * Return if a new password is correctly
     * @return boolean true if the new password is correctly, false otherwise.
     * @throws GB_Exception if:
     * <ol><li>Any error finding the password policies.</li></ol>
     */
    private boolean isValidatePassword() throws GB_Exception{
        if(user.getGbPassword().equals(GBEnvironment.getInstance().criptPwd(changePass1)) ||
           changePass1 == null ||
           changePass2 == null ||
           !changePass1.equals(changePass2) || 
           !GBEnvironment.getInstance().isValidPassword(changePass1, username)){
            GBMessage.putMessage(GBEnvironment.getInstance().getError(34), null);
            return false;
        }
        return true;
    }
    
    /**
     * This method terminate the user session.
     * @return String the next view to navigate.
     */
    public String logout(){
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if(session != null){
                username = SessionController.getInstance().getUser(session);
                SessionController.getInstance().removeSession(session);
                UserController.getInstance().logout(username);
                session.invalidate();
                GBMessage.putMessage(GBEnvironment.getInstance().getError(19), null);  
            }
        } catch (GB_Exception ex) { 
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(15), null);
        }
        return "index";
    }
    
    /**
     * This method valid any user session, if this return false the system navigate to index page.
     * @return true if the user session is valid, false otherwise.
     */
    public boolean isValidSession(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        boolean validSession = SessionController.getInstance().isValidSession(session.getId());
        if(validSession && username == null){
            putUserName();
        }
        return validSession;
    }
    
    /**
     * This method put the user name for a change page.
     */
    private void putUserName() {
        try{
            username = SessionController.getInstance().getUser((HttpSession) FacesContext
                                                                    .getCurrentInstance()
                                                                    .getExternalContext()
                                                                    .getSession(false));
            user =UserController.getInstance().getUser(username);
        }catch(GB_Exception ex){
            LOG.fatal(ex);
        }
    }
    
    /**
     * This method clean the change password fields.
     */
    private void cleanPwdFields(){
        actualPass = null;
        changePass1 = null;
        changePass2 = null;
    }
}