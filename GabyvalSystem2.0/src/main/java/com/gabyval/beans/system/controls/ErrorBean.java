/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.system.controls;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.commons.CatalogController;
import com.gabyval.controller.commons.ErrorController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.AdCatalogs;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.AdError;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author gusta
 */
@ManagedBean(name = "ErrorBean")
@SessionScoped
public class ErrorBean implements Serializable{
    private static final GB_Logger LOG = GB_Logger.getLogger(ErrorBean.class); //Log for this class.
    private ArrayList<AdError> errors;
    private HashMap<String, Integer> errorLevels; //All ID types allowed.
    private String Errtitle;
    private String ErrDesc;
    private String errLevel;
    private String username;

    public ErrorBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            errorLevels = GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_LEVEL_ERROR).getAllCatalog();
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
    }

    public HashMap<String, Integer> getErrorLevels() {
        return errorLevels;
    }

    public void setErrorLevels(HashMap<String, Integer> errorLevels) {
        this.errorLevels = errorLevels;
    }
    
    public ArrayList<AdError> getErrors() {
        errors = new ArrayList<>();
        try {
            errors= (ArrayList<AdError>) ErrorController.getInstance().getAllErrors();
        } catch (GB_Exception ex) {
            LOG.error(ex);
        }
        return errors;
    }

    public void setErrors(ArrayList<AdError> errors) {
        this.errors = errors;
    }

    public String getErrtitle() {
        return Errtitle;
    }

    public void setErrtitle(String Errtitle) {
        this.Errtitle = Errtitle;
    }

    public String getErrDesc() {
        return ErrDesc;
    }

    public void setErrDesc(String ErrDesc) {
        this.ErrDesc = ErrDesc;
    }

    public String getErrLevel() {
        return errLevel;
    }

    public void setErrLevel(String errLevel) {
        this.errLevel = errLevel;
    }
    
    public void onPageEdit(RowEditEvent evt){
        AdError errorEdit = (AdError)evt.getObject();
        errorEdit.setGbErrorLevel(CatalogController.getInstance().encode(errorLevels, Integer.parseInt(errLevel)));
        errorEdit.setRowversion(errorEdit.getRowversion()+1);
        try {
            ErrorController.getInstance().save(errorEdit);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(58), ""+errorEdit.getGbErrorId());
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(59), ""+errorEdit.getGbErrorId());
        }
    }
    
    public void saveError(){
        try {
            AdError error = new AdError(GBEnvironment.getInstance().getNextTableId("AdError"), ErrDesc, getLevelValue(), GBEnvironment.getInstance().serverDate(), 0, Errtitle, username, username, GBEnvironment.getInstance().serverDate(), 1);
            ErrorController.getInstance().save(error);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(56), ""+error.getGbErrorId());
        } catch (GB_Exception ex) {
            LOG.error(ex);
            GBMessage.putMessage(GBEnvironment.getInstance().getError(57), null);
        }
    }
    
    public String getLevelValue(){
        return GBEnvironment.getInstance().getCatalog(GB_CommonStrConstants.CT_LEVEL_ERROR).decodeValue(Integer.parseInt(errLevel));
    }
}
