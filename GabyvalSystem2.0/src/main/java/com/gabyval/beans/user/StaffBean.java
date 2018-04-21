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
 * |   1.0   |  13/11/2017  |      GAOQ      | Creacion del bean de perfil de usuario.                                                                 |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.user; 

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.user.PersonalDataController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.persistence.user.AdUserPersonalData;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.mchange.io.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import oracle.jdbc.rowset.OracleSerialBlob;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * This class is the web container for AdUserPersonalData, management the personal
 * data for any user.
 * @author GAOQ
 * @version 1.0
 * @since 13/11/2017
 */
@Named(value = "StaffBean")
@SessionScoped
public class StaffBean implements Serializable{
    
    private final GB_Logger LOG = GB_Logger.getLogger(StaffBean.class); //The log for this bean.
    private String username; //Actually username.
    private String staff_name; //Name for this username.
    private String staff_surname; //Surname for this username.
    private String id_type; //ID type for this username.
    private String id_number; //ID number for this username.
    private String phone_number; //Phone's home for this username.
    private String mobile_number; //Mobile for this username.
    private String email; //E-mail for this username.
    private Date birthdate; //Birthday for this username.
    private String gender; //Gender for this username.
    private StreamedContent photo_profile; //Profile´s pic for this username
    private Blob imagePic; //Profile´s pic for this username in data base.
    private HashMap<String, Integer> genders; //All genders allowed.
    private HashMap<String, Integer> id_types; //All ID types allowed.
    
    /**
     * Create a new instance for this bean.
     */
    public StaffBean() {
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            genders = GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_GENDER).getAllCatalog();
            id_types= GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_IDTYPE).getAllCatalog();
            charge_up();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(28), null);
        }
    }

    /**
     * Change the profile´s pic.
     * @param image StreamedContent the new profile´s pic.
     */
    public void setPhoto_profile(StreamedContent image){
        photo_profile = image;
    }
    
    /**
     * Return the profile´s pic.
     * @return StreamedContent the profile´s pic.
     */
    public StreamedContent getPhoto_profile(){
        return photo_profile;
    }
    
    /**
     * Return the name for this user.
     * @return String the name.
     */
    public String getStaff_name() {
        return staff_name;
    }

    /**
     * Modify the name for this user.
     * @param staff_name String the new name.
     */
    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    /**
     * Return the surname for this user.
     * @return String the surname.
     */
    public String getStaff_surname() {
        return staff_surname;
    }
    /**
     * Modify the surname for this user.
     * @param staff_surname String the new surname.
     */
    public void setStaff_surname(String staff_surname) {
        this.staff_surname = staff_surname;
    }

    /**
     * Return the ID Type for this user.
     * @return String String the ID Type.
     */
    public String getId_type() {
        return id_type;
    }
    /**
     * Modify the ID type for this user.
     * @param id_type String the new ID Type.
     */
    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    /**
     * Return the name for this user.
     * @return String String the new name.
     */
    public String getId_number() {
        return id_number;
    }
    
    /**
     * Modify the ID number fot this user.
     * @param id_number String the new ID Number.
     */
    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    /**
     * Return the Phone number for this user.
     * @return String the Phone number.
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * Modify the phone number for this user.
     * @param phone_number String the new phone number.
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * Return the mobile number for this user.
     * @return String the mobile number.
     */
    public String getMobile_number() {
        return mobile_number;
    }

    /**
     * Modify the mobile number for this user.
     * @param mobile_number String the new mobile number.
     */
    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    /**
     * Return the email for this user.
     * @return String the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modify the email for this user.
     * @param email String the new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the birthday for this user.
     * @return Date the birthday.
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Modify the birthday for this user.
     * @param birthdate Date the new brithday.
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Return the gender for this user.
     * @return String the gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Modify the gender for this user.
     * @param gender String the new gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Return the username in this controller.
     * @return String the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Modify the username in this controller.
     * @param username String the new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return all genders allowed.
     * @return HashMap all genders allowed.
     */
    public HashMap<String, Integer> getGenders() {
        return genders;
    }

    /**
     * Modify the genders allowed.
     * @param genders HashMap the new genders allowed.
     */
    public void setGenders(HashMap<String, Integer> genders) {
        this.genders = genders;
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
                photo_profile = new ByteArrayContent(imagePic.getBytes(1, size.intValue()));
            } catch (SQLException ex) {
                LOG.error(ex);
                GBMessage.putMessage(GBEnvironment.getInstance().getError(30), null);
            }
            GBMessage.putMessage(GBEnvironment.getInstance().getError(31), null);
        }
    }
    
    /**
     * Save a personal information for this user.
     */
    public void saveOrUpdateProfile(){
        try {
            
            if(PersonalDataController.getInstance().existRegistry(username)){
                PersonalDataController.getInstance().update(username, staff_name, staff_surname, 
                                                     Integer.parseInt(id_type), 
                                                     id_number, imagePic, phone_number, mobile_number, email, birthdate, 
                                                     Integer.parseInt(gender), birthdate);
            }else{
               PersonalDataController.getInstance().create(username, staff_name, staff_surname, 
                                                    Integer.parseInt(id_type), 
                                                    id_number, imagePic, phone_number, mobile_number, email, birthdate, 
                                                    Integer.parseInt(gender));
            }
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(32), null);
        }
        GBMessage.putMessage(GBEnvironment.getInstance().getError(33), null);
    }

    /**
     * Charge the info from database or default information.
     * @throws GB_Exception if:
     * <ol><li>Dont finish the charge complete.</li></ol>
     */
    private void charge_up() throws GB_Exception {
        try{
            if(PersonalDataController.getInstance().existRegistry(username)){
                putPersonalInformation();
            }else{
                putDefaultInformation();
            }
        } catch (SQLException | IOException ex) {
            LOG.error(ex);
            throw new GB_Exception("Error al carga informacion de usuario. Comuniquese con su administrador.");
        }
    }

    /**
     * Put all information for this user from database.
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private void putPersonalInformation() throws SQLException, FileNotFoundException, IOException {
        AdUserPersonalData personal;
        try {
            personal = PersonalDataController.getInstance().getPersonalData(username);
            staff_name = personal.getGbStaffName();
            staff_surname = personal.getGbStaffSurname();
            id_type = ""+personal.getGbIdType();
            id_number = personal.getGbIdNumber();
            putProfPic(personal.getGbPhoto());
            phone_number = personal.getGbPhoneNumber();
            mobile_number = personal.getGbMobileNumber();
            email = personal.getGbEmail();
            birthdate = personal.getGbBirthdate();
            gender =  ""+personal.getGbGender();
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putException(ex);
        }
    }
    
    /**
     * Put the default information if the user dont have registry into database.
     * @throws FileNotFoundException
     * @throws SQLException
     * @throws IOException 
     */
    private void putDefaultInformation() throws FileNotFoundException, SQLException, IOException {
        staff_name = "Sin informacion";
        staff_surname = "Sin informacion";
        id_type = "Sin informacion";
        id_number = "Sin informacion";
        putProfPic(null);
        phone_number = "Sin informacion";
        mobile_number = "Sin informacion";
        email =  "Sin informacion";
        birthdate = GBEnvironment.getInstance().serverDate();
        gender =  "Seleccione sexo";
    }

    /**
     * Put the pic in database or defatul pic.
     * @param gbPhoto
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private void putProfPic(Blob gbPhoto) throws SQLException, FileNotFoundException, IOException {
        if(gbPhoto == null){
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            File file = new File(externalContext.getRealPath("")+"\\resources\\images\\user_default.png");
            imagePic = new OracleSerialBlob(FileUtils.getBytes(file));
            Long size = imagePic.length();
            photo_profile = new ByteArrayContent(imagePic.getBytes(1, size.intValue()));
        }else{
            imagePic = gbPhoto;
            Long size = gbPhoto.length();
            photo_profile = new ByteArrayContent(gbPhoto.getBytes(1, size.intValue()));
        }
    }
}
