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
 * |   1.0   |  23/04/2017  |      GAOQ      | Creacion del manejador de scheduler central.                                                            |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  19/06/2017  |      GAOQ      | Se elimina la pre carga de los Jobs, se actualiza la forma como se pausa o despausan los jobs.          |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  19/06/2017  |      GAOQ      | Se adiciona la informacion de las constantes.                                                           |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.3   |  04/06/2018  |      GAOQ      | Division de los scheduler por grupos de trabajo.                                                        |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
*/
package com.gabyval.core.scheduler;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * This class control the scheduler and jobs running.
 * @author GAOQ
 * @version 1.1
 * @since 13/06/2017
 */
public class GB_Scheduler{
    
    private final GB_Logger LOG = GB_Logger.getLogger(GB_Scheduler.class);//Central LOG.
    private Scheduler SCH; //Central Scheduler.
    private Scheduler ControlSCH; //Central Scheduler.
    private static GB_Scheduler instance;//Instance of this controller.
    private final Trigger GbCentraltrigger; //This is the trigger that execute each second, without system pause validation.
    private Trigger GbJobTrigger; //This is the trigger that configure each job to execute. Applied the systen pause validation.
    
    /**
     * Return the instance of this controller.
     * @return GB_Scheduler this instance.
     */
    public static GB_Scheduler getInstance() throws SchedulerException, GB_Exception{
        if(instance == null){
            instance = new GB_Scheduler();
        }
        return instance;
    }
    
    /**
     * Create a new instance of this controller.
     */
    public GB_Scheduler() throws GB_Exception{
        try {
            GbCentraltrigger = new CronTriggerImpl(GB_CommonStrConstants.GBCENTRALSCH, 
                                                   GB_CommonStrConstants.GBCENTRALSCH, 
                                                   GB_CommonStrConstants.GB_EACH_SEC_SCH);
            SCH = StdSchedulerFactory.getDefaultScheduler();
            ControlSCH = StdSchedulerFactory.getDefaultScheduler();
            start();
        } catch (SchedulerException | ParseException ex) {
            LOG.fatal(ex);
            throw new GB_Exception(ex);
        }
    }
    
    /**
     * Start the job whit ID.
     * @param job_id int Job id.
     */
    public void run_job(int job_id){
        AdJob job;
        try {
            job = (AdJob)PersistenceManager.getInstance().load(AdJob.class, job_id);
            JobDetail jd = new JobDetailImpl(job.getGbJobName(), 
                                             GB_CommonStrConstants.GB_SCH, 
                                             (Class<? extends Job>) Class.forName(job.getGbJobClass()));
            GbJobTrigger = new CronTriggerImpl(GB_CommonStrConstants.GB_SCH, GB_CommonStrConstants.GB_SCH, job.getGbExpCron());
            if(!SCH.checkExists(jd.getKey())){
                SCH.scheduleJob(jd, GbJobTrigger);
            }
        } catch (ClassNotFoundException | ParseException | SchedulerException | GB_Exception ex) {
            LOG.fatal(ex);
        }
    }
    
    /**
     * Restart the central scheduler 
     */
    public void restart_sch() throws GB_Exception{
        shutdownService();
        start();
    }
    
    /**
     * Start the scheduler service.
     */
    public void start(){
        try {
            if(!isSchedulerRunning()){
                configureControl();
                SCH.start();
            }
        } catch (Exception ex) {
            LOG.fatal(ex);
        }
    }
    
    /**
     * Stop the scheduler service.
     * @throws com.gabyval.core.exception.GB_Exception if:
     * <ol><li>The scheduler cant turn off.</li></ol>
     */
    public void shutdownService() throws GB_Exception {
        try {
            if(SCH.isStarted()){
                SCH.shutdown();
            }
        } catch (SchedulerException ex) {
            LOG.error(ex);
            throw new GB_Exception("Ha ocurrido un error al tratar de apagar el servicio de Scheduler...");
        }
    }
    
    /**
     * Stop a job identify whit job id.
     * @param job_id int job id.
     */
    public void stopJob(int job_id){
        AdJob job;
        try {
            job = (AdJob)PersistenceManager.getInstance().load(AdJob.class, job_id);
            SCH.unscheduleJob(((GB_JobDetail)SCH.getJobDetail(new JobKey(job.getGbJobName()))).getJobTrigger().getKey());
        } catch (SchedulerException | GB_Exception ex) {
            LOG.fatal(ex);
        }
    }

    /**
     * Configure the Job that control the pause and unpause jobs.
     */
    private void configureControl() throws GB_Exception {
        try {
            if (ControlSCH.getJobDetail(new JobKey(GB_CommonStrConstants.GBCENTRALSCH)) == null){
                JobDetail jd = new JobDetailImpl(GB_CommonStrConstants.GBCENTRALSCH, 
                                                 GB_CommonStrConstants.GBCENTRALSCH, 
                                                 SchedulerControl.class);
                ControlSCH.scheduleJob(jd, GbCentraltrigger);
            }
            putAllAutoStartJob();
        } catch (SchedulerException ex) {
            LOG.fatal("The scheduler can't start, the process finish with the next error:");
            LOG.fatal(ex);
            throw new GB_Exception(ex);
        }
    }
    
    public boolean isSchedulerRunning(){
        try {
            return SCH.isStarted();
        } catch (SchedulerException ex) {
            LOG.error(ex);
            return false;
        }
    }

    private void putAllAutoStartJob() {
        LOG.debug("Charging all jobs.");
        try {
            List<AdJob> l = PersistenceManager.getInstance().getAll(AdJob.class);
            if(!l.isEmpty()){
                LOG.debug("Starting whit job charging... "+l.size()+" to load.");
                for(AdJob job : l){
                    if(job.getGbAutoRun().endsWith("S")){
                        JobDetail jd = new JobDetailImpl(job.getGbJobName(), 
                                                         GB_CommonStrConstants.GB_SCH,
                                                         (Class<? extends Job>) Class.forName(job.getGbJobClass()));
                        GbJobTrigger = new CronTriggerImpl(GB_CommonStrConstants.GB_SCH, GB_CommonStrConstants.GB_SCH, job.getGbExpCron());
                        if(!SCH.checkExists(jd.getKey())){
                            SCH.scheduleJob(jd, GbJobTrigger);
                        }
                    }
                }
            }else{
                LOG.debug("Not exist any job to load.");
            }
        } catch (Exception ex) {
            LOG.fatal(ex);
        }
    }
    
    public void shutdownSchedulerModule(){
        try {
            SCH.shutdown(true);
        } catch (SchedulerException ex) {
            Logger.getLogger(GB_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method start all jobs for Gabyval System.
     */
    public void stopGBJobs(){
        try {
            SCH.pauseJobs(GroupMatcher.jobGroupEquals(GB_CommonStrConstants.GB_SCH));
        } catch (SchedulerException ex) {
            Logger.getLogger(GB_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method stop all jobs for Gabyval System.
     */
    public void startGBJobs(){
        try {
            SCH.resumeJobs(GroupMatcher.jobGroupEquals("GBJobTr"));
        } catch (SchedulerException ex) {
            Logger.getLogger(GB_Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
