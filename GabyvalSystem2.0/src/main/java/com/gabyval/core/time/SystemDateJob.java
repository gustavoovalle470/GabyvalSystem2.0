/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.gabyval.core.time;

import com.gabyval.core.scheduler.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author gusta
 */
public class SystemDateJob extends Job{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("Ejecutando...");
    }
    
}
