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
 * |   1.0   |  16/05/2017  |      GAOQ      | Creacion de la clase encargada de la logica de los ID para todas las tablas mapeadas.                   |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  10/07/2017  |      GAOQ      | Se modifica la clase para adaptarla a los metodos de persistencia, actualizacion correcta de la         |   
 * |         |              |                | informacion para la tabla que maneja los ID para las tablas.                                            |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.commons.controllers;

import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.util.Calendar;
import java.util.List;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

/**
 * This class control all features of ID for mapped tables.
 * @author GAOQ
 * @version 1.1
 * @since 16/05/2017
 */
public class IdControl {
    
    private static final GB_Logger LOG = GB_Logger.getLogger(IdControl.class);//This is the central log for this class.
    
    /**
     * Return the next id for the specified table.
     * @param table_name String the table name.
     * @return int the next id for this table.
     * @throws GB_Exception if:
     * <ol><li>The id cant convert to number</li></ol>
     */
    public static int getNextId(String table_name) throws GB_Exception{
        int nextid=0;
        synchroId(table_name);
        try{
            AdIdControl id = getResource(table_name);
            id.setGbNumCtrl(id.getGbNumInc());
            id.setGbNumInc(id.getGbNumInc()+1);
            id.setRowversion(id.getRowversion()+1);
            PersistenceManager.getInstance().update(id);
        }catch(GB_Exception ex){
            LOG.fatal(ex);
            throw ex;
        }
        nextid = getResource(table_name).getGbNumCtrl();
        return nextid;
    }
    
    /**
     * This method synchronize the id for the specified table.
     * @param table_name String the table name.
     */
    public static void synchroId(String table_name){
        try {
            AdIdControl id = getResource(table_name);
            id.setGbNumCtrl(getActuallyId(table_name));
            id.setGbNumInc(id.getGbNumCtrl()+1);
            id.setRowversion(id.getRowversion());
            PersistenceManager.getInstance().update(id);
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
        }
    }

    /**
     * This method create a new record into ID_CONTROL table.
     * @param table_name String table name.
     */
    private static void create_new_record(String table_name) throws GB_Exception {
        AdIdControl id = new AdIdControl(table_name, 0, 0, Calendar.getInstance().getTime(), 0);
        PersistenceManager.getInstance().save(id);
    }

    /**
     * This method return the ID_CONTROL.
     * @param table_name String the table name.
     * @return AdIdControl the ID_CONTROL.
     * @throws GB_Exception if:
     * <ol><li>The table is not mapped</li>
     * <li> The ID is not numeric</li></ol>
     */
    private static AdIdControl getResource(String table_name) throws GB_Exception {
        try{
            PersistenceManager.getInstance().runCriteria("SELECT NVL(MAX(A.id), 0) AS MAX_ID FROM "+table_name+" A");
        }catch(QuerySyntaxException ex){
            throw new GB_Exception(ex);
        }
        AdIdControl id = (AdIdControl) PersistenceManager.getInstance().load(AdIdControl.class, table_name);
        if(id == null){
            create_new_record(table_name);
            id = (AdIdControl) PersistenceManager.getInstance().load(AdIdControl.class, table_name);
        }
        return id;
    }

    /**
     * Return the actually ID for table.
     * @param table_name String the table name.
     * @return int the actually id.
     * @throws GB_Exception if:
     * <ol><li>The ID is not numeric</li></ol>
     */
    private static int getActuallyId(String table_name) throws GB_Exception {
        try{
            List l = PersistenceManager.getInstance().runCriteria("SELECT NVL(MAX(A.id), 0) AS MAX_ID FROM "+table_name+" A");
            return Integer.parseInt(l.get(0).toString());
        }catch(NumberFormatException ex){
            LOG.fatal(ex);
            throw new GB_Exception(ex);
        }
    }
}