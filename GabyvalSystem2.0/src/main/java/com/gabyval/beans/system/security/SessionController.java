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
 * |   1.0   |  13/11/2017  |      GAOQ      | Creacion del manejador de sesiones.                                                                     |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.system.security;

import com.gabyval.core.exception.GB_Exception;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

/**
 * This class control the user sessions.
 * @author GAOQ.
 * @version 1.0
 * @since 13/11/2017
 */
public class SessionController {
    private static SessionController instance; //Actual instance of this controller.
    private HashMap<String, String>userSession;//HashMap with all user sessions.
    
    /**
     * Create a new instance of this controller.
     */
    public SessionController(){
        userSession = new HashMap<>();
    }
    
    /**
     * Return the instance of this controller.
     * @return SessionController this instance.
     */
    public static SessionController getInstance(){
        if(instance == null){
            instance = new SessionController();
        }
        return instance;
    }
    
    /**
     * This method add a new session to this controller.
     * @param session HttpSession a new http session.
     * @param username String a username for this session.
     * @throws GB_Exception if:
     * <ol><li>The session exist</li></ol>
     */
    public void addSession(HttpSession session, String username) throws GB_Exception{
        if(isValidSession(session.getId())){
            throw new GB_Exception("La session de usuario ya se encuentra registrada.");
        }
        userSession.put(session.getId(), username.toUpperCase());
    }
    
    /**
     * Remove the user session for this controller.
     * @param session HttpSession the user session.
     * @throws GB_Exception if:
     * <ol><li>The session is invalid.</li></ol>
     */
    public void removeSession(HttpSession session) throws GB_Exception{
        if(!isValidSession(session.getId())){
            throw new GB_Exception("La session de usuario no es valida.");
        }
        userSession.remove(session.getId());
    }
    
    /**
     * Return the user for any session.
     * @param session the user session.
     * @return String the username.
     * @throws GB_Exception if:
     * <ol><li>The session is invalid.</li></ol>
     */
    public String getUser(HttpSession session) throws GB_Exception{
        if(!isValidSession(session.getId())){
            throw new GB_Exception("La session de usuario no es valida.");
        }
        return userSession.get(session.getId());
    }
    
    /**
     * Return if any session is valid or not.
     * @param httpSessionId the user serssion.
     * @return boolean, true if the session is valid, false otherwise.
     */
    public boolean isValidSession(String httpSessionId){
        return userSession.containsKey(httpSessionId);
    }
}
