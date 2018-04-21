/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.user;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.user.PersonalDataController;
import com.gabyval.controller.user.UserController;
import com.gabyval.controller.user.UserDataContainer;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.AdUsers;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import oracle.jdbc.rowset.OracleSerialBlob;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author gusta
 */
@Named(value = "NewUserBean")
@SessionScoped
public class NewUserBean implements Serializable{
    
    private static final GB_Logger LOG = GB_Logger.getLogger(NewUserBean.class);
    private boolean skip;
    private UserDataContainer personalData;
    private HashMap<String, Integer> id_types; //All ID types allowed.
    private HashMap<String, Integer> genders; //All ID types allowed.
    private HashMap<String, Integer> sessionStatus; //All ID types allowed.
    private HashMap<String, Integer> operativeStatus; //All ID types allowed.
    private String lStatus;
    private String oStatus;
    private List<AdUsers> users;
    private Blob imagePic; //Profile´s pic for this username in data base.
    private String username;
    private boolean needChangePwd;
    private final String personalPhase = "PERSONAL";
    private final String contactPhase = "CONTACT";
    private final String photoPhase = "PHOTO";
    private final String confirmPhase = "CONFIRM";
    
    public NewUserBean(){
        try {
            personalData = new UserDataContainer();
            skip = false;
            needChangePwd = false;
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            genders = GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_GENDER).getAllCatalog();
            id_types= GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_IDTYPE).getAllCatalog();
            sessionStatus= GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_LSTATUS).getAllCatalog();
            operativeStatus= GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_OSTATUS).getAllCatalog();
            users = UserController.getInstance().getAllUsers();
            users.remove(UserController.getInstance().getUser(username));
            lStatus = ""+1;
            oStatus = ""+1;
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(28), null);
        }
    }
    
    /**
     * Return all ID type allowed.
     * @return HashMap the ID Type allowed.
     */
    public HashMap<String, Integer> getId_types() {
        return id_types;
    }

    /**
     * Modify the ID types allowed.
     * @param id_types HashMap  the new ID types allowed.
     */
    public void setId_types(HashMap<String, Integer> id_types) {
        this.id_types = id_types;
    }

    public HashMap<String, Integer> getGenders() {
        return genders;
    }

    public void setGenders(HashMap<String, Integer> genders) {
        this.genders = genders;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public UserDataContainer getPersonalData() {
        return personalData;
    }

    public void setPersonalData(UserDataContainer personalData) {
        this.personalData = personalData;
    }

    public List<AdUsers> getUsers(){
        users = UserController.getInstance().getAllUsers();
        try {
            users.remove(UserController.getInstance().getUser(username));
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        return users;
    }
    
    public void setUsers(List<AdUsers> thisUsers){
        try {
            users = UserController.getInstance().getAllUsers();
            users.remove(UserController.getInstance().getUser(username));
            UserController.getInstance().setAllUsers(users);
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }

    public HashMap<String, Integer> getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(HashMap<String, Integer> sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public HashMap<String, Integer> getOperativeStatus() {
        return operativeStatus;
    }

    public void setOperativeStatus(HashMap<String, Integer> operativeStatus) {
        this.operativeStatus = operativeStatus;
    }

    public String getlStatus() {
        return lStatus;
    }

    public void setlStatus(String lStatus) {
        this.lStatus = lStatus;
    }

    public String getoStatus() {
        return oStatus;
    }

    public void setoStatus(String oStatus) {
        this.oStatus = oStatus;
    }

    public boolean isNeedChangePwd() {
        return needChangePwd;
    }

    public void setNeedChangePwd(boolean needChangePwd) {
        this.needChangePwd = needChangePwd;
    }
    
    public String onFlowProcess(FlowEvent event) {
        switch(event.getOldStep().toUpperCase()){
            case personalPhase:
                createUserName();
                break;
            case contactPhase:
                break;
            case photoPhase:
                break;
            case confirmPhase:
                return personalPhase.toLowerCase();
            default:
                break;
        }
        return event.getNewStep();
    }
    
    public void createUser(){
        Calendar lastXgePwd = Calendar.getInstance();
        lastXgePwd.add(Calendar.YEAR, -100);
        try {
            PersonalDataController.getInstance().create(personalData.getUsername(), personalData.getStaffName(), personalData.getStaffLastName(), Integer.parseInt(personalData.getId_type()), personalData.getId_number(), imagePic, personalData.getPhone(), personalData.getMovil(), personalData.getEmail(), personalData.getBirthday(), Integer.parseInt(personalData.getGender()));
            AdUsers user = new AdUsers(personalData.getUsername(), GBEnvironment.getInstance().criptPwd(personalData.getId_number()), 0, 0, lastXgePwd.getTime(), GBEnvironment.getInstance().serverDate(), GBEnvironment.getInstance().serverDate(), username, 0, GBEnvironment.getInstance().serverDate(), 0);
            UserController.getInstance().saveUser(user);
            personalData = new UserDataContainer();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(29), null);
        }
        personalData = new UserDataContainer();
        RequestContext r = RequestContext.getCurrentInstance();
        r.reset("CreationUserForm");
    }

    private boolean validateUserNameAllow(String username, int tryNumber){
        try {
            UserController.getInstance().getUser(username);
            String complement = "";
            if(personalData.getStaffName().contains(" ")){
                String nombres[] =personalData.getStaffName().split(" ");
                complement = nombres[1].substring(tryNumber, tryNumber+1);
            }else{
                complement = ""+(tryNumber+1);
            }
            personalData.setUsername(username+complement);
            validateUserNameAllow(personalData.getUsername(), tryNumber+1);
        } catch (GB_Exception ex) {
            return true;
        }
        return true;
    }
    
    public void refresh(){
            personalData = new UserDataContainer();
    }
    
    
    /**
     * Upload a pic into aplication and database.
     * @param event the event for upload file.
     */
    public void upload(FileUploadEvent event){
        UploadedFile file = event.getFile();
        if(file != null){
            try {
                imagePic = new OracleSerialBlob(file.getContents());
                Long size = imagePic.length();
                personalData.setPhoto_profile(new ByteArrayContent(imagePic.getBytes(1, size.intValue())));
            } catch (SQLException ex) {
                LOG.error(ex);
                GBMessage.putMessage(GBEnvironment.getInstance().getError(30), null);
            }
            GBMessage.putMessage(GBEnvironment.getInstance().getError(31), null);
        }
    }
    
    /**
     * Action for update the Security profile with data from view.
     * @param evt RowEditEvent, the event of edit.
     */
    public void onRowEdit(RowEditEvent evt){
        AdUsers editUser = (AdUsers)evt.getObject();
        editUser.setGbLoginStatus(Integer.parseInt(lStatus));
        editUser.setGbOprativeStatus(Integer.parseInt(oStatus));
        Calendar c = Calendar.getInstance();
        if(needChangePwd){
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH)-1, c.get(Calendar.DAY_OF_MONTH));
        }else{
            c.add(1, Calendar.MONTH);
        }
        editUser.setGbLastPwdXgeDt(c.getTime());
        editUser.setGbLastUserXge(username);
        editUser.setGbLastXgeDt(GBEnvironment.getInstance().serverDate());
        editUser.setRowversion(editUser.getRowversion()+1);
        try {
            UserController.getInstance().saveUser(editUser);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(42), null);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(43), null);
        }
    }

    public String getLoginStatus(int status){
        for(String key: sessionStatus.keySet()){
            if(sessionStatus.get(key).equals(status)){
                lStatus = ""+sessionStatus.get(key);
                return key;
            }
        }
        return "";
    }
    
    public String getOperativeStatus(int status){
        for(String key: operativeStatus.keySet()){
            if(operativeStatus.get(key).equals(status)){
                oStatus = ""+operativeStatus.get(key);
                return key;
            }
        }
        return "";
    }
    
    public String getChangePassword(String thisUsername){
        try {
            needChangePwd = UserController.getInstance().isNeedChangePasswor(thisUsername);
        } catch (GB_Exception ex) {
            LOG.error(ex);
            needChangePwd = false;
        }
        if(needChangePwd){
            return "SI";
        }else{
            return "NO";
        }
    }

    private void createUserName() {
        String newUserName = "";
        if (personalData.getUsername() == null){
            String nombres[] =personalData.getStaffName().split(" ");
            String apellidos[] =personalData.getStaffLastName().split(" ");
            if(nombres.length > 1){
                newUserName=apellidos[0]+nombres[0].substring(0, 1)+nombres[1].substring(0, 1);
            }else{
                newUserName=apellidos[0]+nombres[0].substring(0, 1);
            }
            personalData.setUsername(newUserName.toUpperCase());
            validateUserNameAllow(personalData.getUsername(), 0);
        }
    }
}
