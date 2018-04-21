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
 * |   1.0   |  19/06/2017  |      GAOQ      | Creacion del manejador de detalles de los JOB que permite instanciarlos correctamente.                  |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */

package com.gabyval.core.scheduler;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

/**
 * This class manage the job information for execute in scheduler.
 * @author GAOQ
 * @version 1.0
 * @since 19/06/2017
 */
public class GB_JobDetail implements JobDetail{

    private final JobKey key; //Id into scheduler for job.
    private final Class<? extends Job> job; //Class that implement the job.
    private final Trigger trigger; //Trigger of this job.
    private final JobDataMap map; //Data of this job.
    private final String description; //The job description.
    private final int jobId; //Data base job ID.
    
    /**
     * Create a new Job detail for GABYVAL APP.
     * @param key Id of Job.
     * @param job Job in executio
     * @param trigger the trigger for this job
     * @param description the description for the task
     * @param jobId id for this job.
     */
    public GB_JobDetail(JobKey key, Class<? extends Job> job, Trigger trigger, String description, int jobId){
        this.key = key;
        this.job = job;
        this.trigger = trigger;
        map = new JobDataMap();
        this.description = description;
        this.jobId = jobId;
    }
    
    /**
     * Clone this job.
     * @return This job.
     */
    @Override
    public Object clone(){
        return this;
    }
    
    /**
     * Return the Job Key for the scheduler.
     * @return JobKey the key.
     */
    @Override
    public JobKey getKey() {
        return key;
    }

    /**
     * Return the Job description.
     * @return String the description.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Return the class definition.
     * @return Class the class definition.
     */
    @Override
    public Class<? extends Job> getJobClass() {
        return job;
    }

    /**
     * Return the Data map.
     * @return JobDataMap the job data.
     */
    @Override
    public JobDataMap getJobDataMap() {        
        return map;
    }

    /**
     * Return if the job is durable.
     * @return true if the job is durable, false otherwise.
     */
    @Override
    public boolean isDurable() {
        return true;
    }

    /**
     * Return if the job is persistence.
     * @return true if is persistence. False otherwise.
     */
    @Override
    public boolean isPersistJobDataAfterExecution() {
        return false;
    }

    /**
     * Return if this job is concurrent.
     * @return true if this job is concurrent. False otherwise.
     */
    @Override
    public boolean isConcurrentExectionDisallowed() {
        return false;
    }

    /**
     * Return if this job request recovery.
     * @return true if this job request recovery, false otherwise.
     */
    @Override
    public boolean requestsRecovery() {
        return false;
    }

    /**
     * Return the job builder.
     * @return the job builder.
     */
    @Override
    public JobBuilder getJobBuilder() {
        return null;
    }
    
    /**
     * Return the job trigger.
     * @return Tirgger, the job trigger.
     */
    public Trigger getJobTrigger(){
        return trigger;
    }

    /**
     * Return the Job id into data base.
     * @return int job id in data base.
     */
    public int getJobId() {
        return jobId;
    }
}