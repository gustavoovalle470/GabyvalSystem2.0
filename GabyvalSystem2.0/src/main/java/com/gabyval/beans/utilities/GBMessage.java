/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.utilities;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.AdError;
import com.gabyval.core.exception.GB_Exception;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author gusta
 */
public class GBMessage {
    
    public static void putMessage(AdError error, String replace){
        FacesMessage message = new FacesMessage(getSeverity(error.getGbErrorLevel()), error.getGbErrorTitle(), GBEnvironment.getInstance().replaceMessage(error.getGbErrorDesc(), replace));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void putException(GB_Exception ex){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se ha presentado un problema", ex.getMessage());
        putIntoContext(message);
    }
    
    public static void putFatalMessage(String IncomingMsg){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", IncomingMsg);
        putIntoContext(message);
    }
    
    public static void putErrorMessage(String IncomingMsg){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", IncomingMsg);
        putIntoContext(message);
    }
    
    public static void putInfoMessage(String IncomingMsg){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", IncomingMsg);
        putIntoContext(message);
    }
    
    public static void putWarnMessage(String IncomingMsg){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", IncomingMsg);
        putIntoContext(message);
    }
    
    public static void putCustomMessage(FacesMessage.Severity severity, String title, String IncomingMsg){
        FacesMessage message = new FacesMessage(severity, title, IncomingMsg);
        putIntoContext(message);
    }

    private static void putIntoContext(FacesMessage message){
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    private static FacesMessage.Severity getSeverity(String gbErrorLevel) {
        switch(gbErrorLevel){
            case GB_CommonStrConstants.FATAL:
                return FacesMessage.SEVERITY_FATAL;
            case GB_CommonStrConstants.ERROR:
                return FacesMessage.SEVERITY_ERROR;
            case GB_CommonStrConstants.WARN:
                return FacesMessage.SEVERITY_WARN;
            case GB_CommonStrConstants.INFO:
                return FacesMessage.SEVERITY_INFO;
            default:
                return FacesMessage.SEVERITY_WARN;
        }
    }
}
