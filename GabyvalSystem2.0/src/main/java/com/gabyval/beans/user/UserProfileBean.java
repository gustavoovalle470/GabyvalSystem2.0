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
 * |   1.0   |  13/11/2017  |      GAOQ      | Creacion del bean de perfil de serguridad de usuarios.                                                  |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.user;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.user.ProfileControler;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.time.SystemDateController;
import com.gabyval.persistence.user.security.AdUserProfile;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 * This class contol the security profile.
 * @author GAOQ
 * @version 1.0
 * @since 13/11/2017
 */
@ManagedBean(name = "userProfileBean")
@RequestScoped
public class UserProfileBean implements Serializable{
    
    private static final GB_Logger LOG = GB_Logger.getLogger(UserProfileBean.class); //The log for this class.
    private String username; //The username.
    private String profilename; //Security profile name.
    private String description;//Security profile description.
    private boolean status;//Security profile status.
    private String statusCmb;//Security profile status description.
    private HashMap<String, Integer> possibleStatus;//All Security profile status allowed.
    
    /**
     * Create a new instance for this controller.
     */
    public UserProfileBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            possibleStatus = GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_STATUS).getAllCatalog();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(34), null);
        }
    }

    /**
     * Return the Security profile name.
     * @return String the name.
     */
    public String getProfilename() {
        return profilename;
    }

    /**
     * Modify the Security profile name.
     * @param profilename String the new name.
     */
    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    /**
     * Return the Security profile description.
     * @return String the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modify the Security profile description.
     * @param description String the new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the status for creation of this Security profile
     * @return boolean true if the profile will be create open, false otherwise.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Modify the status for creation of this Security profile
     * @param status boolean the new status.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Return the all allowed status for Security profile.
     * @return HashMap the all allowed status.
     */
    public HashMap<String, Integer> getPossibleStatus() {
        return possibleStatus;
    }

    /**
     * Change the all allowed status for Security profile.
     * @param possibleStatus HashMap the new allowed status.
     */
    public void setPossibleStatus(HashMap<String, Integer> possibleStatus) {
        this.possibleStatus = possibleStatus;
    }
   
    /**
     * Return all Security profile.
     * @return List all Security profile
     */
    public List<AdUserProfile> getProfiles() {
        return ProfileControler.getInstance().getProfiles();
    }

    /**
     * Modify all Security profile
     * @param profiles the new Security profiles
     */
    public void setProfiles(List<AdUserProfile> profiles) {
        ProfileControler.getInstance().setProfiles(profiles);
    }

    /**
     * Return the status description.
     * @return String the status description.
     */
    public String getStatusCmb() {
        return statusCmb;
    }

    /**
     * Change the status description.
     * @param statusCmb String the new status description.
     */
    public void setStatusCmb(String statusCmb) {
        this.statusCmb = statusCmb;
    }

    /**
     * Return the formated date.
     * @param date Date to format.
     * @return String the date formated.
     */    
    public String getDateString(Date date){
        try {
            return SystemDateController.getInstance().getDateFormated(date);
        } catch (GB_Exception ex) {
            Logger.getLogger(UserProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return "";
        }
    }
    
    /**
     * Action for update the Security profile with data from view.
     * @param evt RowEditEvent, the event of edit.
     */
    public void onRowEdit(RowEditEvent evt){
        AdUserProfile editProfile = (AdUserProfile)evt.getObject();
        editProfile.setGbLastUpDt(GBEnvironment.getInstance().serverDate());
        editProfile.setGbLastUserUp(username);
        try {
            ProfileControler.getInstance().saveProfile(editProfile);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(35), null);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(36), null);
        }
    }
    
    /**
     * Save the Security profile
     */
    public void saveProfile(){
        try {
            ProfileControler.getInstance().createProfile(profilename.toUpperCase(), description, (status?1:3), 
                            SessionController.getInstance()
                            .getUser((HttpSession)FacesContext
                            .getCurrentInstance().getExternalContext().getSession(false)));
            GBMessage.putMessage(GBEnvironment.getInstance().getError(37), null);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(38), null);
        }
    }
    
    /**
     * Decode the data, for status.
     * @param code code for description.
     * @return String description.
     */
    public String decode(int code){
        return GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_STATUS).decodeValue(code);
    }
}
