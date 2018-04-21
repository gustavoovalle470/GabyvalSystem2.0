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
 * |   1.0   |  23/04/2017  |      GAOQ      | Creacion del controlador de controles de fecha del sistema                                              |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  19/06/2017  |      GAOQ      | Se modifica la forma como se guarda informacion de los Jobs, para poder modificar/guardar informacion   |
 * |         |              |                | en tiempo de ejecucion.                                                                                 |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
*/
package com.gabyval.core.scheduler;

import java.util.HashMap;
import org.quartz.JobKey;

/**
 * This class manage the basic information for the Job running
 * @author GAOQ
 * @version 1.1
 * @since 13/06/2017
 */
public class JobProperty {
    private static HashMap<JobKey, GB_JobDetail> jobs = new HashMap<>(); //The hash map whit the properties.
    
    /**
     * Put job into properties.
     * @param jobKey the key into Scheduler.
     * @param detail the Job detail.
     */
    public static void putJob(JobKey jobKey, GB_JobDetail detail){
        jobs.put(jobKey, detail);
    }
    
    /**
     * Put a integer field.
     * @param jobKey the key into Scheduler.
     * @param key the key for this field.
     * @param value the integer value.
     */
    public static void putJobField(JobKey jobKey, String key, int value){
        jobs.get(jobKey).getJobDataMap().put(key, value);
    }
    
    /**
     * Put a String field.
     * @param jobKey the key into Scheduler.
     * @param key the key for this field.
     * @param value the String value.
     */
    public static void putJobField(JobKey jobKey, String key, String value){
        jobs.get(jobKey).getJobDataMap().put(key, value);
    }
    
    /**
     * Put a double field.
     * @param jobKey the key into Scheduler.
     * @param key the key for this field.
     * @param value the double value.
     */
    public static void putJobField(JobKey jobKey, String key, Double value){
        jobs.get(jobKey).getJobDataMap().put(key, value);
    }
    
    /**
     * Put a long field.
     * @param jobKey the key into Scheduler.
     * @param key the key for this field.
     * @param value the long value.
     */
    public static void putJobField(JobKey jobKey, String key, long value){
        jobs.get(jobKey).getJobDataMap().put(key, value);
    }
    
    /**
     * Put a boolean field.
     * @param jobKey the key into Scheduler.
     * @param key the key for this field.
     * @param value the boolean value.
     */
    public static void putJobField(JobKey jobKey, String key, boolean value){
        jobs.get(jobKey).getJobDataMap().put(key, value);
    }
    
    /**
     * Return the integer value into field.
     * @param jobKey the key into scheduler.
     * @param key the key for this field.
     * @return the integer value.
     */
    public static int getIntJobField(JobKey jobKey, String key){
        return (int) jobs.get(jobKey).getJobDataMap().get(key);
    }
    
    /**
     * Return the String value into field.
     * @param jobKey the key into scheduler.
     * @param key the key for this field.
     * @return the String value.
     */
    public static String getStringJobField(JobKey jobKey, String key){
        return (String) jobs.get(jobKey).getJobDataMap().get(key);
    }
    
    /**
     * Return the double value into field.
     * @param jobKey the key into scheduler.
     * @param key the key for this field.
     * @return the double value.
     */
    public static Double getDoubleJobField(JobKey jobKey, String key){
        return (Double) jobs.get(jobKey).getJobDataMap().get(key);
    }
    
    /**
     * Return the long value into field.
     * @param jobKey the key into scheduler.
     * @param key the key for this field.
     * @return the long value.
     */
    public static long getLongJobField(JobKey jobKey, String key){
        return (long) jobs.get(jobKey).getJobDataMap().get(key);
    }
    
    /**
     * Return the boolean value into field.
     * @param jobKey the key into scheduler.
     * @param key the key for this field.
     * @return the boolean value.
     */
    public static boolean getBooleanJobField(JobKey jobKey, String key){
        return (boolean) jobs.get(jobKey).getJobDataMap().get(key);
    }
}
