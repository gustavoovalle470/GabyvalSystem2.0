/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.utilities;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.scheduler.GB_Scheduler;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author gusta
 */
public class GabyvalSystemEnvBean implements Servlet{
    
    private final GB_Logger LOG = GB_Logger.getLogger(GabyvalSystemEnvBean.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("GABYVAL is stating, charging the Enviroment...");
        GBEnvironment.getInstance();
        LOG.info("GABYVAL finish the charge for Enviroment.");
        try {
            LOG.info("GABYVAL is stating the connection pool.");
            PersistenceManager.getInstance();
            LOG.info("GABYVAL is connected to database");
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
        }
        LOG.info("GABYVAL now try Start the Scheduler services..");
        GB_Scheduler.getInstance();
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "This servlet start all GABYVAL Services.";
    }

    @Override
    public void destroy() {
        LOG.info("GABYVAL now try shutdown the scheduler service...");
        try {
            GB_Scheduler.getInstance().shutdownService();
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
        }
        LOG.info("GABYVAL complete the scheduler service shutdown.");
    }
}