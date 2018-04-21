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
 * |   2.5   |  23/06/2016  |      GAOQ      | Controlador del log central de la aplicacion.                                                           |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.logger;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggerRepository;


/**
 * This class controlled and print all message in the log file.
 * @author Gustavo Ovalle
 * @version 2.5
 * @since June 23th, 2016
 */
public class GB_Logger extends Logger {
    
    static LoggerRepository rep = new Hierarchy(Logger.getRootLogger());
    private static final GB_LoggerFactory MY_FACTORY = new GB_LoggerFactory();

    /**
     * Create a new instance of PS_Logger whit params.
     * @param name String class name that invoke the log.
     */
    public GB_Logger(String name) {
        super(name);
    }
  
    /**
     * This method change the logger level.
     * @param level Level the new level for logger, it can be:
     * <ol>
     *  <li>Level.DEBUG</li>
     *  <li>Level.INFO</li>
     *  <li>Level.ERROR</li>
     *  <li>Level.FATAL</li>
     * </ol>
     */
    public static void GB_setLevel(Level level){
        System.out.println(level.toString());
        Logger.getRootLogger().setLevel(level);
    }
    
    /**
     * This method return a instance of logger for a class define that invoke.
     * @param name the class name.
     * @return GB_Logger the logger for class.
     */
    public static GB_Logger getLogger(Class name) {
        return (GB_Logger) MY_FACTORY.makeNewLoggerInstance(name.getCanonicalName());
    }
    
    /**
     * Print a exception into the file log when log level is DEBUG
     * @param ex Exception the exception to print
     */
    public void debug(Exception ex){
        debug(getCompMessage(ex));
    }
    
    /**
     * Print a exception into the file log when log level is INFO
     * @param ex Exception the exception to print
     */
    public void info(Exception ex){
        info(getCompMessage(ex));
    }

    /**
     * Print a exception into the file log when log level is ERROR
     * @param ex Exception the exception to print
     */
    public void error(Exception ex){
        error(getCompMessage(ex));
    }
  
    /**
     * Print a exception into the file log when log level is FATAL
     * @param ex Exception the exception to print.
     */
    public void fatal(Exception ex){
        fatal(getCompMessage(ex));
    }
  
    /**
     * This method create the message to print when the log print a Exception.
     * @param ex Exception the exception to print.
     * @return Object the message object to print.
     */
    private Object getCompMessage(Exception ex) {
        String compMessage = ex.getClass().getCanonicalName()+": "+ex.getLocalizedMessage();
        for(StackTraceElement element: ex.getStackTrace()){
            compMessage = compMessage+"\n\t"+element.toString();
        }
        return compMessage;
    }
  
    /**
     * Print the message define by user when logger level is DEBUG
     * @param message Object the message to print
     */
    @Override
    public void debug(Object message){
        if(Level.DEBUG.isGreaterOrEqual(Logger.getRootLogger().getEffectiveLevel())){
            rep.getLogger(getName()).debug(message);
        }
    }
  
    /**
     * Print the message define by user when logger level is INFO
     * @param message Object the message to print
     */
    @Override
    public void info(Object message){
        if(Level.INFO.isGreaterOrEqual(Logger.getRootLogger().getEffectiveLevel())){
            rep.getLogger(getName()).info(message);
        }
    }
  
    /**
     * Print the message define by user when logger level is ERROR
     * @param message Object the message to print
     */
    @Override
    public void error(Object message){
        if(Level.ERROR.isGreaterOrEqual(Logger.getRootLogger().getEffectiveLevel())){
            rep.getLogger(getName()).error(message);
        }
    }
  
    /**
     * Print the message define by user when logger level is FATAL
     * @param message Object the message to print
     */
    @Override
    public void fatal(Object message){
        if(Level.FATAL.isGreaterOrEqual(Logger.getRootLogger().getEffectiveLevel())){
            rep.getLogger(getName()).fatal(message);
        }
    }
}