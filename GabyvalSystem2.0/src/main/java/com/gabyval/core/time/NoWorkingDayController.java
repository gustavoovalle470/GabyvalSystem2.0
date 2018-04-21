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
 * ******************************************************** Control de versiones ***********************************************************************
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * | Version |    Fecha     |  Responsable   |                                                  Comentarios                                            |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.0   |  23/04/2017  |      GAOQ      | Creacion del controlador de transaccionalidad                                                           |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  17/05/2017  |      GAOQ      | Modificacion del mecanismo de persistencia, por el controlador generico.                                |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.time;

import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class manage all logical operations whit No working days.
 * @author GAOQ
 * @version 1.0
 * @since 23/04/2017
 */
public class NoWorkingDayController {
    private static final GB_Logger LOG = GB_Logger.getLogger(NoWorkingDayController.class);
    private static List<AdNoWorkingDays> NOWORKINGDAYS;
    
    /**
     * Return if a date is a working day
     * @param dayFind Date will be compare with no working days.
     * @return boolean true if the day is no working day, false otherwise
     */
    public static boolean isNoWorkingDay(Date dayFind) throws GB_Exception{
        NOWORKINGDAYS = PersistenceManager.getInstance().getAll(AdNoWorkingDays.class);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateFind = format.format(dayFind);
        for(AdNoWorkingDays day: NOWORKINGDAYS){
            if(dateFind.equals(format.format(day.getGbDate()))){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return the next working day.
     * @param actualDate Date to start to find the next working day.
     * @return Date the next working day.
     */
    public static Date getNextWorkingDate(Date actualDate) throws GB_Exception{
        Date date = null;
        Calendar c = Calendar.getInstance();
        boolean isWorkingDay = false;
        for(int i = 1; !isWorkingDay; i++){
            c.setTime(actualDate);
            c.add(Calendar.DAY_OF_YEAR, i);
            isWorkingDay = !isNoWorkingDay(c.getTime());
            if(isWorkingDay){
                date = c.getTime();
            }
        }
        return date;
    }
    
    public static void putWeekendsAsNoWorking(int year) throws GB_Exception{
        String day, desc;
        int i = 1;
        Calendar c = Calendar.getInstance();
        c.set(year, 00, 01);
        while(c.get(Calendar.YEAR) == year){
            day = c.getTime().toString();
            day = day.toUpperCase();
            if(day.startsWith("SAT") || day.startsWith("SUN")){
                desc = (day.startsWith("SAT")? "Sábado": "Domingo");
                desc = desc+" "+c.get(Calendar.DAY_OF_MONTH)+" de "+getMonth(c.get(Calendar.MONTH)+1);
                AdNoWorkingDays item = new AdNoWorkingDays(i, c.getTime(), desc, Calendar.getInstance().getTime(), 0);
                PersistenceManager.getInstance().save(item);
                i = i +1;
            }
            c.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private static String getMonth(int monthNumber) {
        switch (monthNumber){
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
            default:
                return "";
        }
    }
}
