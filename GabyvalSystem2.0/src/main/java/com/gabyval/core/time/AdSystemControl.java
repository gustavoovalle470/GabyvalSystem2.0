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
 * |   1.0   |  15/04/2017  |      GAOQ      | Creacion de la clase de entidad.                                                                        |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  13/05/2017  |      GAOQ      | Se elimina la columna de fecha actual del sistema.                                                      |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.time;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class control all properties for system dates
 * @author Gustavo Adolfo Ovalle Quintero
 * @version 1.0
 * @since 15/04/2017
 */
@Entity
@Table(name="AD_SYSTEM_CONTROL", schema="GABYVAL")
public class AdSystemControl  implements java.io.Serializable{


    private int gbSystemControlId; //Unique id for this control.
    private String gbSystemPause; //Indique if the system is pause or not.
    private String gbSystemWorkDay;//Indique if the system is a working day or not.
    private Date gbLastCloseDt;// Last close system.
    private Date gbNextCloseDt; //Next close system.
    private Date createDt; //Creation registry date.
    private int rowversion; // Registry version.

    /**
     * Create a new instance of System control without parameters.
     */ 
    public AdSystemControl() {
    }

    /**
     * Create a new instance of System control with following parameters:
     * @param gbSystemControlId unique id of control.
     * @param gbSystemPause flag system pause.
     * @param gbSystemWorkDay flag working day.
     * @param gbLastCloseDt date of last close system.
     * @param gbNextCloseDt date of next close system.
     * @param createDt date of create this entry.
     * @param rowversion entry version.
     */
    public AdSystemControl(int gbSystemControlId, String gbSystemPause, String gbSystemWorkDay, Date gbLastCloseDt, Date gbNextCloseDt, Date createDt, int rowversion) {
       this.gbSystemControlId = gbSystemControlId;
       this.gbSystemPause = gbSystemPause;
       this.gbSystemWorkDay = gbSystemWorkDay;
       this.gbLastCloseDt = gbLastCloseDt;
       this.gbNextCloseDt = gbNextCloseDt;
       this.createDt = createDt;
       this.rowversion = rowversion;
    }
   
    /**
     * Return the unique ID for this system control.
     * @return int the unique ID.
     */
    @Id
    @Column(name="GB_SYSTEM_CONTROL_ID", unique=true, nullable=false, precision=22, scale=0)
    public int getGbSystemControlId() {
        return this.gbSystemControlId;
    }
    
    /**
     * Change the unique ID for this system control.
     * @param gbSystemControlId int The new unique ID.
     */
    public void setGbSystemControlId(int gbSystemControlId) {
        this.gbSystemControlId = gbSystemControlId;
    }
    
    /**
     * Return if the system is a pause or not.
     * @return String Y for system pause or N otherwise.
     */
    @Column(name="GB_SYSTEM_PAUSE", nullable=false, length=1)
    public String getGbSystemPause() {
        return this.gbSystemPause;
    }
    
    /**
     * Change the flag to pause.
     * @param gbSystemPause the new flag.
     */
    public void setGbSystemPause(String gbSystemPause) {
        this.gbSystemPause = gbSystemPause;
    }

    /**
     * Flag that indicate if the system is in a working day.
     * @return String that indicate if the system is a working day.
     */
    @Column(name="GB_SYSTEM_WORK_DAY", nullable=false, length=1)
    public String getGbSystemWorkDay() {
        return this.gbSystemWorkDay;
    }
    
    /**
     * Change flag that indicate if the system is a working day.
     * @param gbSystemWorkDay String the new flag.
     */
    public void setGbSystemWorkDay(String gbSystemWorkDay) {
        this.gbSystemWorkDay = gbSystemWorkDay;
    }

    /**
     * Return the date of the last close system.
     * @return Date the last close system.
     */
    @Column(name="GB_LAST_CLOSE_DT", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getGbLastCloseDt() {
        return this.gbLastCloseDt;
    }
    
    /**
     * Change the date of last close system.
     * @param gbLastCloseDt Date the new last close system
     */
    public void setGbLastCloseDt(Date gbLastCloseDt) {
        this.gbLastCloseDt = gbLastCloseDt;
    }

    /**
     * Return the next close date system.
     * @return Date the next close date system.
     */
    @Column(name="GB_NEXT_CLOSE_DT", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getGbNextCloseDt() {
        return this.gbNextCloseDt;
    }
    
    /**
     * Change the next close date system.
     * @param gbNextCloseDt Date the new date to close system.
     */
    public void setGbNextCloseDt(Date gbNextCloseDt) {
        this.gbNextCloseDt = gbNextCloseDt;
    }

    /**
     * Date of create this registry.
     * @return Date date of creation.
     */
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_DT", nullable=false, length=7)
    public Date getCreateDt() {
        return this.createDt;
    }
    
    /**
     * Change the date of creation.
     * @param createDt new date of creation.
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    /**
     * Return the row version of the entry.
     * @return Int the row version.
     */
    @Column(name="ROWVERSION", nullable=false, precision=22, scale=0)
    public int getRowversion() {
        return this.rowversion;
    }
    
    /**
     * Change the rowversion of the entry.
     * @param rowversion int the new row version.
     */
    public void setRowversion(int rowversion) {
        this.rowversion = rowversion;
    }
}