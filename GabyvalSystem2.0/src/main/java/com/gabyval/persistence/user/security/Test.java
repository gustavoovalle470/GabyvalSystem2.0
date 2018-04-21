/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.persistence.user.security;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;

/**
 *
 * @author gusta
 */
public class Test {
    public static void main(String args[]) throws GB_Exception{
        AdUserProfiling ad= (AdUserProfiling)PersistenceManager.getInstance().load(AdUserProfiling.class, new AdUserProfilingId("ADMINISTRATOR", "admin"));
    }
}
