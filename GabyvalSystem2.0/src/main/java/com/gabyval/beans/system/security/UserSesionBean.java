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
 */
package com.gabyval.beans.system.security;

import com.gabyval.beans.system.security.SessionController;
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
 * @version 1.0
 * @since 13/11/2017
 */
@Named(value = "UserSesionBean")
@SessionScoped
public class UserSesionBean implements Serializable {
    
    private static final GB_Logger LOG = GB_Logger.getLogger(UserSesionBean.class); //The log for this class.
    private String username;   //Username.
    private String changePass1;// For change, the first password.
    private String changePass2; //For change, the second password. Confirnation.
    private String actualPass;
    private AdUsers user;       //User entity.
    private String home_page = "app_master.xhtml";
    private String navRule;
    
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
        }finally{
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

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }
    
    /**
     * This method evaluate and change de password.
     * @return String the next view to navigate.
     */
    /**
     * This method evaluate and change de password.
     * @return String the next view to navigate.
     */
    public String changePassword(){
        System.out.println("com.gabyval.beans.system.security.UserSesionBean.changePassword()");
        boolean flag = false;
        try{
            if(GBEnvironment.getInstance().criptPwd(actualPass).equals(user.getGbPassword())){
                if(actualPass.equals(changePass1)){
                    GBMessage.putMessage(GBEnvironment.getInstance().getError(35), null);
                } else if(changePass1.equals(changePass2)){
                    UserController.getInstance().changePassword(username, GBEnvironment.getInstance().criptPwd(changePass1));
                    GBMessage.putMessage(GBEnvironment.getInstance().getError(31), null);
                    flag = true;
                } else {
                    GBMessage.putMessage(GBEnvironment.getInstance().getError(32), null);
                }
            } else {
                GBMessage.putMessage(GBEnvironment.getInstance().getError(33), null);
            }
            if (flag){
                return logout();
            } else {
                actualPass = null;
                changePass1 = null;
                changePass2 = null;
                return null;
            }
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(34), null);
            return null;
        }
    }
    
    /**
     * This method terminate the user session.
     * @return String the next view to navigate.
     */
    public String logout(){
        System.out.println("Cerrar sesion.");
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
        navRule = "logout";
        return "logout";
    }
    
    public boolean isValidSession(){
        System.out.println("Validando sesion.");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        boolean validSession = SessionController.getInstance().isValidSession(session.getId());
        if(validSession && username == null){
            putUserName();
        }
        System.out.println("La sesion es valida? "+SessionController.getInstance().isValidSession(session.getId()));
        
        return SessionController.getInstance().isValidSession(session.getId());
    }
    
    public void validatePassword(){
        if(changePass1 != null && changePass2!= null){
            if(!changePass1.equals(changePass2)){
                GBMessage.putMessage(GBEnvironment.getInstance().getError(41), null);
            }
            GBMessage.putMessage(GBEnvironment.getInstance().getError(40), null);
        }
    }
    
    public String home_page(){
        return home_page;
    }

    public String getActualPass() {
        return actualPass;
    }

    public void setActualPass(String actualPass) {
        this.actualPass = actualPass;
    }
    
    public void listener(){
        System.out.println("Click");
    }

    public String getNavRule() {
        return navRule;
    }

    public void setNavRule(String navRule) {
        this.navRule = navRule;
    }

    private void putUserName() {
        try{
            username = SessionController.getInstance().getUser((HttpSession) FacesContext
                                                                    .getCurrentInstance()
                                                                    .getExternalContext()
                                                                    .getSession(false));
            user =UserController.getInstance().getUser(username);
        }catch(GB_Exception ex){
            
        }
    }
}
