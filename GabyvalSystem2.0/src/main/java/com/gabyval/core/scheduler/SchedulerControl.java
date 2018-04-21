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
 * |   1.0   |  19/06/2017  |      GAOQ      | Creacion del controlador central de scheduler.                                                          |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  19/06/2017  |      GAOQ      | Colocacion de la informacion de encabezado.                                                             |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
*/
package com.gabyval.core.scheduler;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.time.Controls;
import com.gabyval.core.time.SystemDateController;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * This class describe the control job, manager all jobs.
 * @author GAOQ
 * @version 1.1
 * @since 19/06/2017.
 */
public class SchedulerControl extends Job{
    
    private final GB_Logger LOG = GB_Logger.getLogger(SchedulerControl.class); //Log central.
    private Date SysDate; // Server date.

    /**
     * The method to execute into the Scheduler.
     * @param jec the execution context.
     * @throws JobExecutionException :
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        SystemDateController.getInstance().refreshControl();
        if(JobProperty.getIntJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_CHARGE_FIELD) == 0){
            chargeJobs();
        }
        if(SystemDateController.getInstance().isSystemPaused()){
            LOG.debug("The system is pause, stoppin all Jobs in execution...");
            GB_Scheduler.getInstance().stop();
            try {
                JobProperty.putJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_DT_FIELD, 0);
                SystemDateController.getInstance().pauseUnPauseSys(true);
            } catch (GB_Exception ex) {
                LOG.fatal(ex);
            }
        }else{
            LOG.debug("The system is not pause, starting all Jobs to execution...");
            JobProperty.putJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_DT_FIELD, 1);
            systemDateSch();
            GB_Scheduler.getInstance().resume();
        }
    }
    
    /**
     * Charge the jobs into Scheduler.
     */
    private void chargeJobs() {
        LOG.debug("Charging all jobs.");
        int totalJobs;
        try {
            List l = PersistenceManager.getInstance().runCriteria("SELECT NVL(MAX(gbJobId), 0) AS MAX_ID FROM AdJob");
            if(l.isEmpty()){
                totalJobs = 0;
                LOG.debug("Not exist any job to load.");
            }else{
                totalJobs = Integer.parseInt(l.get(0).toString());
                LOG.debug("Starting whit job charging... "+totalJobs+" to load.");
                for(int i = 1; i<=totalJobs; i++){
                    GB_Scheduler.getInstance().run_job(i);
                }
            }
        } catch (Exception ex) {
            LOG.fatal(ex);
        }
        JobProperty.putJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_CHARGE_FIELD, 1);
        GB_Scheduler.getInstance().start();
    }
    
    /**
     * Date service.
     */
    public void systemDateSch(){
        LOG.debug("Starting the time system service.");
        Calendar sysUpdate = Calendar.getInstance();
        try {
            if(JobProperty.getIntJobField(new JobKey(GB_CommonStrConstants.SCH_CONTROL), GB_CommonStrConstants.SCH_CHARGE_FIELD) == 0){
                SystemDateController.getInstance().pauseUnPauseSys(false);
            }
            SysDate = SystemDateController.getInstance().getSystemDate();
            sysUpdate.setTime(SysDate);
            sysUpdate.add(Calendar.SECOND, 1);
            SystemDateController.getInstance().changeDate(sysUpdate.getTime(), Controls.SYSTEM_DATE);
        } catch (GB_Exception ex) {
                LOG.fatal(ex);
        }
    }
}
