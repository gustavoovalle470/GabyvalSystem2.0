/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.controller.user;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.AdUserPersonalData;
import java.sql.Blob;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author gusta
 */
public class PersonalDataController {
    private static final GB_Logger LOG = GB_Logger.getLogger(PersonalDataController.class);
    private static PersonalDataController instance;
    
    public static PersonalDataController getInstance(){
        if(instance == null){
            instance = new PersonalDataController();
        }
        return instance;
    }
    
    public boolean existRegistry(String username) throws GB_Exception{
        if((AdUserPersonalData) PersistenceManager.getInstance().load(AdUserPersonalData.class, username) != null){
            return true;
        }
        return false;
    }

    public void update(String username, String staff_name, String staff_surname, int id_type, String id_number, Blob imagePic, String phone_number, String mobile_number, String email, Date birthdate, int gender, Date birthdate0) throws GB_Exception {
        AdUserPersonalData personal = (AdUserPersonalData) PersistenceManager.getInstance().load(AdUserPersonalData.class, username);
        personal.setGbBirthdate(birthdate);
        personal.setGbEmail(email);
        personal.setGbGender(gender);
        personal.setGbIdNumber(id_number);
        personal.setGbIdType(id_type);
        personal.setGbMobileNumber(mobile_number);
        personal.setGbPhoneNumber(phone_number);
        personal.setGbPhoto(imagePic);
        personal.setGbStaffName(staff_name);
        personal.setGbStaffSurname(staff_surname);
        personal.setRowversion(personal.getRowversion()+1);
        save(personal);
    }
    
    public void create(String username, String staff_name, String staff_surname, int id_type, String id_number, Blob imagePic, String phone_number, String mobile_number, String email, Date birthdate, int gender) throws GB_Exception {
        AdUserPersonalData personal = new AdUserPersonalData(username, staff_name, staff_surname, id_type, id_number, imagePic, phone_number, mobile_number, email, birthdate, gender, GBEnvironment.getInstance().serverDate(), 0);
        save(personal);
    }
    
    private void save(AdUserPersonalData personal) throws GB_Exception{
        PersistenceManager.getInstance().save(personal);
        PersistenceManager.getInstance().update(personal);
    }
    
    public AdUserPersonalData getPersonalData(String username) throws GB_Exception{
        return (AdUserPersonalData) PersistenceManager.getInstance().load(AdUserPersonalData.class, username);
    }
}
