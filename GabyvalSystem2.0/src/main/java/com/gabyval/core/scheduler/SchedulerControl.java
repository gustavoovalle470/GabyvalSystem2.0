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
 * |   1.2   |  04/06/2018  |      GAOQ      | Sparacion del control de timer del sistema del scheduler central de inicio y pausa del scheduler del    |
 * |         |              |                | sistema                                                                                                 |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
*/
package com.gabyval.core.scheduler;

import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.core.time.SystemDateController;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 * This class describe the control job, manager all jobs.
 * @author GAOQ
 * @version 1.1
 * @since 19/06/2017.
 */
public class SchedulerControl extends Job{
    
    private final GB_Logger LOG = GB_Logger.getLogger(SchedulerControl.class); //Log central.

    /**
     * The method to execute into the Scheduler.
     * @param jec the execution context.
     * @throws JobExecutionException :
     */
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try{
            if(SystemDateController.getInstance().isSystemPaused()){
                LOG.debug("The system is pause, stoppin all Jobs in execution...");
                GB_Scheduler.getInstance().stopGBJobs();
            }else{
                LOG.debug("The system is not pause, starting all Jobs");
                GB_Scheduler.getInstance().startGBJobs();
            }
        }catch (GB_Exception | SchedulerException ex){
            LOG.error(ex);
            throw new JobExecutionException("El sistema no pudo iniciar la ejecucion del control central de Scheduler.");
        }
    }
}
