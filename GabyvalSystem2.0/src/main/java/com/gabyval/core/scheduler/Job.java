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
 * |   1.0   |  23/04/2017  |      GAOQ      | Creacion de la interfaz de Jobs.                                                                        |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  19/06/2017  |      GAOQ      | Se modifica la forma como se calcula el tiempo de ejecucion y se guarda en la base de datos el log.     |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.scheduler;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import java.util.Calendar;
import java.util.Date;
import org.quartz.JobExecutionContext;

/**
 * This is the Job interface, that allowed implement new Jobs.
 * @author GAOQ
 * @version 1.1
 * @since 23/04/2017
 */
public abstract class Job implements org.quartz.Job{
    
    protected Date startDate;//Date of start job.
    
    /**
     * Insert the job resuming
     * @param error String the error of the Job. Can be null.
     * @param jd GB_JobDetail Job detail in execution.
     * @param context JobExecutionContext Execution context.
     * @throws GB_Exception if:
     * <ol><li> Any error saved the job log.</li></ol>
     */
    public void insert_execute_log(String error, GB_JobDetail jd, JobExecutionContext context) throws GB_Exception{
        int state = 0;
        AdExecuteJob exe;
        exe = getJobLog(jd.getJobId());
        if(error != null){
            state = 3;
        }
        if(exe == null){
            exe = new AdExecuteJob(jd.getJobId(), state, Calendar.getInstance().getTime(), error, (int)getExecutionTime(context), Calendar.getInstance().getTime(), 0);
            PersistenceManager.getInstance().save(exe);
        }else{
            Calendar c = Calendar.getInstance();
            exe.setGbLastExec(startDate);
            exe.setGbExecTime((int)getExecutionTime(context));
            exe.setRowversion(exe.getRowversion()+1);
            exe.setGbRunState(state);
            exe.setGbMsgError(error);
            PersistenceManager.getInstance().update(exe);
        }
    }

    /**
     * Return a log if exist.
     * @param job_id job id.
     * @return The log.
     * @throws GB_Exception:
     */
    private AdExecuteJob getJobLog(int job_id) throws GB_Exception {
        return (AdExecuteJob)PersistenceManager.getInstance().load(AdExecuteJob.class, job_id);
    }
    
    /**
     * Return a execution time.
     * @param jec Execution context.
     * @return int execution time in milliseconds 
     */
    public long getExecutionTime(JobExecutionContext jec){
        return (Calendar.getInstance().getTimeInMillis()-jec.getFireTime().getTime());
    }
}