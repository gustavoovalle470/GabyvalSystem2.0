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
 */
package com.gabyval.core.scheduler;

import com.gabyval.core.GBEnvironment;
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
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
    private static GB_Scheduler instance;//Instance of this controller.
    
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
    public GB_Scheduler() throws SchedulerException, GB_Exception{
        try {
            SCH = StdSchedulerFactory.getDefaultScheduler();
            configureControl();
        } catch (SchedulerException | GB_Exception ex) {
            LOG.fatal(ex);
            throw ex;
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
            GB_JobDetail jd = new GB_JobDetail(new JobKey(job.getGbJobName()), //JobKey is the Job name in data base.
                                                   (Class<? extends Job>) Class.forName(job.getGbJobClass()), //Class that difine the job.
                                                    new CronTriggerImpl(job.getGbJobDesc(), GB_CommonStrConstants.SCH_GROUP, job.getGbExpCron()), //Difine the trigger. 
                                                    job.getGbJobDesc(), //Job description.
                                                    job.getGbJobId()); //Database Job Id.
            if(!SCH.checkExists(jd.getKey())){
                SCH.scheduleJob(jd, jd.getJobTrigger());            
            }
        } catch (ClassNotFoundException | ParseException | SchedulerException | GB_Exception ex) {
            LOG.fatal(ex);
        }
    }
    
    /**
     * Restart the central scheduler 
     */
    public void restart_sch(){
        stop();
        JobProperty.putJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_CHARGE_FIELD, 0);
        resume();
    }
    
    /**
     * Start the scheduler service.
     */
    public void start(){
        try {
            if(!isSchedulerRunning()){
                SCH.start();
            }
        } catch (SchedulerException ex) {
            LOG.fatal(ex);
        }
    }

    /**
     * Stop the scheduler service.
     */
    @PreDestroy
    public void stop() {
        try {
            SCH.pauseAll();
            SCH.resumeJob(new JobKey(GB_CommonStrConstants.SCH_CONTROL));
        } catch (SchedulerException ex) {
            LOG.error(ex);
        }
    }
    
    /**
     * Stop the scheduler service.
     * @throws com.gabyval.core.exception.GB_Exception if:
     * <ol><li>The scheduler cant turn off.</li></ol>
     */
    public void shutdownService() throws GB_Exception {
        try {
            SCH.pauseAll();
            SCH.deleteJob(new JobKey(GB_CommonStrConstants.SCH_CONTROL));
            GBEnvironment.getInstance().SystemPause(true);
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
            GB_JobDetail jd = new GB_JobDetail(new JobKey(GB_CommonStrConstants.SCH_CONTROL), //JobKey is the Job name in data base.
                                               SchedulerControl.class, //Class that difine the job.
                                               new CronTriggerImpl(GB_CommonStrConstants.SCH_CONTROL, Scheduler.DEFAULT_GROUP, "0/1 * * * * ?"), //Difine the trigger. 
                                               "This is a central job out system controls.", //Job description.
                                               0); //Database Job Id.
            SCH.scheduleJob(jd, jd.getJobTrigger());
            SCH.start();
            JobProperty.putJob(new JobKey(GB_CommonStrConstants.SCH_CONTROL), jd);
            JobProperty.putJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_CHARGE_FIELD, 0);
            JobProperty.putJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_DT_FIELD, 0);
        } catch (SchedulerException | ParseException ex) {
            LOG.fatal("The scheduler can't start, the process finish with the next error:");
            LOG.fatal(ex);
            throw new GB_Exception(ex);
        }
    }
    
    /**
     * Restart all jobs.
     */
    public void resume(){
        try {
            SCH.resumeAll();
        } catch (SchedulerException ex) {
            LOG.fatal(ex);
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
}
