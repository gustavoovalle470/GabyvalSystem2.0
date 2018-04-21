/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.utilities;

import com.gabyval.controller.commons.CatalogController;
import com.gabyval.core.GBEnvironment;
import com.gabyval.core.logger.GB_Logger;
import java.util.HashMap;
import javax.ejb.Init;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author gusta
 */
@Named(value = "LogBean")
@RequestScoped
public class LogBean {
    private String logLevel;
    private HashMap<String, Integer> logLevels;
    private String log_path;
    private String log_patern;
    private String log_size;

    public LogBean(){
        logLevels = GBEnvironment.getInstance().getCatalog("LOG_LEVEL").getAllCatalog();
        logLevel = ""+CatalogController.getInstance().decode(logLevels, GB_Logger.getRootLogger().getEffectiveLevel().toString());
        log_patern = "%d %-5p %c - %m%n";
        log_path = "C:/GABYVAL/gblogs/";
        log_size = "100 Mega bites";        
    }
    
    public String getLogLevel() {
        return CatalogController.getInstance().encode(logLevels, Integer.parseInt(logLevel));
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public HashMap<String, Integer> getLogLevels() {
        return logLevels;
    }

    public void setLogLevels(HashMap<String, Integer> logLevels) {
        this.logLevels = logLevels;
    }

    public String getLog_path() {
        return log_path;
    }

    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }

    public String getLog_patern() {
        return log_patern;
    }

    public void setLog_patern(String log_patern) {
        this.log_patern = log_patern;
    }

    public String getLog_size() {
        return log_size;
    }

    public void setLog_size(String log_size) {
        this.log_size = log_size;
    }
    
    public void changeLogLevel(){
        GBEnvironment.getInstance().changeLogLevel(CatalogController.getInstance().encode(logLevels, Integer.parseInt(logLevel)));
        GBMessage.putMessage(GBEnvironment.getInstance().getError(65), CatalogController.getInstance().encode(logLevels, Integer.parseInt(logLevel)));
    }
}
