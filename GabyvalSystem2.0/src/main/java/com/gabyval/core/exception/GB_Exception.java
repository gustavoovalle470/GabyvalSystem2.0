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
 * |   1.0   |  09/04/2017  |      GAOQ      | Creacion de la version original del manejador de errores del sistema.                                   |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------| 
 * |   1.1   |  22/04/2017  |      GAOQ      | Adicion del lector de errores que se guardan en la base de datos.                                       |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  29/04/2017  |      GAOQ      | Se adiciona un metodo que le permite al usuario reemplazar comodin $ en los mensajes de error de la base|
 * |         |              |                | de datos                                                                                                |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------| 
 * |   1.3   |  17/05/2017  |      GAOQ      | Se modifica la forma de lectura, del lector especifico al lector generico                               |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.4   |  26/08/2017  |      GAOQ      | Se modifica la forma como se obtiene el error de la base de datos, para que utilice una interfaz.       |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.5   |  04/12/2017  |      GAOQ      | Se modifica la estructura del error incluyendo los metodos de replace en la entidad adecuada.           |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.6   |  12/12/2017  |      GAOQ      | Adicion de metodo unico para recuperar el UNEXPECTED error.                                             |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.exception;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.constants.GB_CommonStrConstants;
import java.util.Calendar;

/**
 * This class allow controlled all exceptions, saving the message and the trace error.
 * @author Gustavo Ovalle
 * @version 1.4
 * @since 09/04/2017
 */
public class GB_Exception extends Exception{
    private String customized_message;
    private Exception exception;
    private boolean isCustomizedMessage = false;
    
    /**
     * Create a instance of GB_Exception whit another exception.
     * @param exception Exception that launch this error.
     */
    public GB_Exception(Exception exception){
        this.exception = exception;
    }
    
    /**
     * Create a new Exception whit customized message 
     * @param customizedMessage String whit customized message error.
     */
    public GB_Exception(String customizedMessage){
        this.customized_message = customizedMessage;
        if(customized_message != null && customized_message.length() > 0){
            isCustomizedMessage = true;
        }
    }
    
    /**
     * Create a new Exception whit customized message 
     * @param exception Exception that launch this error
     * @param customizedMessage String whit customized message error.
     */
    public GB_Exception(Exception exception, String customizedMessage){
        this.exception = exception;
        this.customized_message = customizedMessage;
        if(customized_message != null && customized_message.length() > 0){
            isCustomizedMessage = true;
        }
    }
    
    /**
     * Create a new Exception whit a system number error.
     * @param errorId int number of error in database.
     */
    public GB_Exception(int errorId){
       AdError error= getError(errorId);
       if(error != null){
            this.customized_message= error.getGbErrorDesc();
       }else{
           this.customized_message = getError(errorId).getGbErrorDesc();
       }
       if(customized_message != null && customized_message.length() > 0){
            isCustomizedMessage = true;
        }
    }
    
    /**
     * Create a new Exception whit a system number error and string to replace.
     * @param errorId int number of error in database.
     * @param replace String to replace the wildcards in the error description. All string to replace should separate whit coma.
     */
    public GB_Exception(int errorId, String replace){
        AdError error= getError(errorId);
        if(error == null){
            error = getError(errorId);
        }
       this.customized_message= replaceMessage(error.getGbErrorDesc(), replace);
       if(customized_message != null && customized_message.length() > 0){
            isCustomizedMessage = true;
        }
    }
    
    public String replaceMessage(String original, String replace) {
        if(replace != null){
            String[] data = replace.split(",");
            for(String info: data){
                original = original.replace(GB_CommonStrConstants.ERROR_RPLCE, info);
            }
        }
        return original;
    }
    
    /**
     * Create a new Exception whit error message.
     * @param exception Exception that launch this error.
     * @param errorId int id of error message in database.
     */
    public GB_Exception(Exception exception, int errorId){
        AdError error= getError(errorId);
        if(error == null){
            error = getError(errorId);
        }
        this.exception = exception;
        this.customized_message= error.getGbErrorDesc();
        if(customized_message != null && customized_message.length() > 0){
            isCustomizedMessage = true;
        }
    }
    
    /**
     * Return the message and trace if exist.
     * @return String the message and trace.
     */
    @Override
    public String getMessage(){
        if(isCustomizedMessage){
            return customized_message;
        }else if(exception != null){
            return getTrace();
        }else{
            return "UNEXPECTED BEHAVIOR: This error is not configured into de aplication, and the exception is not controled.";
        }
    }

    /**
     * Interprets and unify the message and the trace if the last one exist.
     * @return String trace and message.
     */
    private String getTrace(){
        String traceMessage = exception.toString();
        StackTraceElement[] trace = exception.getStackTrace();
        if(trace != null && trace.length > 0){
            for(StackTraceElement e:trace){
                traceMessage = traceMessage+"\n\t\t"+e;
            }
        }
        return traceMessage;
    }
    
    private AdError getError(int errorId){
        AdError error;
        try {
            error= (AdError) PersistenceManager.getInstance().load(AdError.class, errorId);
            PersistenceManager.getInstance().refresh(error);
            
        } catch (GB_Exception ex) {
            error = getUnexpectedError(errorId);
        }
        return error;
    }
    
    private AdError getUnexpectedError(int errorId){
        return new AdError(0, "UNEXPECTED BEHAVIOR. The error is not controlled. ID Error: "+errorId, Calendar.getInstance().getTime(), 0);
    }
}