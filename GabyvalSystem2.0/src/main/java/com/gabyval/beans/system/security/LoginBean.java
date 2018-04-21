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
 * |   1.1   |  06/11/2017  |      GAOQ      | Se adiciona logica de cerrado de sesion logico al momento de presentarse algun error no controlado.     |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  13/11/2017  |      GAOQ      | Adicion de puntos de debug del sistema para el log central.                                             |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.system.security;

import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.user.UserController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * This class control the access to application.
 * @author GAOQ.
 * @version 1.0
 * @since 26/08/2017
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean implements Serializable{

    private static final GB_Logger LOG = GB_Logger.getLogger(LoginBean.class); // The log for this class
    private String user;//Property of view INDEX. Username.
    private String pass;//Property of view INDEX. Password.

    /**
     * Return the value save into user.
     * @return String value of user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Change the value into user.
     * @param user String the new user.
     */
    public void setUser(String user) {
        this.user = user.toUpperCase();
    }

    /**
     * Return the password in by user.
     * @return String the password.
     */
    public String getPass() {
        return pass;
    }

    /**
     * Change the value of the password.
     * @param pass String the new password.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    /**
     * This method validate the user credential, if this are valid inscribe the
     * username to a HTTPSESSION, and navigate to the App_master pageView.
     * @return String accessGranted if the user was login correctly, 
     * denied otherwise.
     */
    public String login(){
        LOG.debug("GABYVAL is login for user: "+user);
        if(user != null && !user.trim().equals("") &&
           pass != null && !pass.trim().equals("")){
            try{
                if(UserController.getInstance().login(user, GBEnvironment.getInstance().criptPwd(pass))){
                    SessionController.getInstance().addSession((HttpSession) FacesContext
                                                                    .getCurrentInstance()
                                                                    .getExternalContext()
                                                                    .getSession(false), 
                                                                user);
                    GBMessage.putMessage(GBEnvironment.getInstance().getError(18), user);
                    LOG.debug("GABYVAL finish, the login for user: "+user+" is correctly and complete the operation.");
                    return "accessGranted";
                }else{
                    UserController.getInstance().logout(user);
                }
            }catch(GB_Exception ex){
                LOG.fatal(ex);
                GBMessage.putException(ex);
            }
        } else{
            LOG.debug("GABYVAL requiere the password to established the connection, try again.");
            GBMessage.putMessage(GBEnvironment.getInstance().getError(13), null);
        }
        return "denied";
    }
}
