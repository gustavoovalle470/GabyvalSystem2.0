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
 * |   1.0   |  23/04/2017  |      GAOQ      | Creacion de la clase de entidad.                                                                        |
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
import javax.persistence.UniqueConstraint;

/**
 * This is a entity class, mapped all features of no_working_days whit the
 * table in database.
 * @author GAOQ
 * @since 23/04/2017
 * @version 1.0
 */
@Entity
@Table(name="AD_NO_WORKING_DAYS" ,schema="GABYVAL", uniqueConstraints = @UniqueConstraint(columnNames="GB_DATE"))
public class AdNoWorkingDays  implements java.io.Serializable {


    private int gbNoWorkingDayId;//ID unique for this no working day
    private Date gbDate;//Date of no working day
    private String gbDateDescription; //Description of this no working day
    private Date createDt;//Creation date of this entry.
    private int rowversion; //Entry version.

    /**
     * Create a new instance of no working day without parameters.
     */
    public AdNoWorkingDays() {
    }

    /**
     * Create a new instance of no working day with following parameters:
     * @param gbNoWorkingDayId Unique id.
     * @param gbDate Date of the no working day.
     * @param gbDateDescription the description of why is not working day.
     * @param createDt the entry date create.
     * @param rowversion  the entry version.
     */
    public AdNoWorkingDays(int gbNoWorkingDayId, Date gbDate, String gbDateDescription, Date createDt, int rowversion) {
       this.gbNoWorkingDayId = gbNoWorkingDayId;
       this.gbDate = gbDate;
       this.gbDateDescription = gbDateDescription;
       this.createDt = createDt;
       this.rowversion = rowversion;
    }
   
    /**
     * Return the unique ID for this no working day.
     * @return Int unique id.
     */
    @Id
    @Column(name="GB_NO_WORKING_DAY_ID", unique=true, nullable=false, precision=22, scale=0)
    public int getGbNoWorkingDayId() {
        return this.gbNoWorkingDayId;
    }
    
    /**
     * Change the unique id.
     * @param gbNoWorkingDayId new unique id.
     */
    public void setGbNoWorkingDayId(int gbNoWorkingDayId) {
        this.gbNoWorkingDayId = gbNoWorkingDayId;
    }

    /**
     * Return the date of no working day.
     * @return Date of no working day.
     */
    @Temporal(TemporalType.DATE)
    @Column(name="GB_DATE", unique=true, nullable=false, length=7)
    public Date getGbDate() {
        return this.gbDate;
    }
    
    /**
     * Change the date of no working date.
     * @param gbDate Date new Date of no working date.
     */
    public void setGbDate(Date gbDate) {
        this.gbDate = gbDate;
    }

    /**
     * Return the description of this no working day.
     * @return String the description.
     */
    @Column(name="GB_DATE_DESCRIPTION", nullable=false, length=100)
    public String getGbDateDescription() {
        return this.gbDateDescription;
    }
    
    /**
     * Change the description.
     * @param gbDateDescription String new description.
     */
    public void setGbDateDescription(String gbDateDescription) {
        this.gbDateDescription = gbDateDescription;
    }

    /**
     * Creation date of this entry.
     * @return Date Creation date.
     */
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_DT", nullable=false, length=7)
    public Date getCreateDt() {
        return this.createDt;
    }
    
    /**
     * Change the creation date.
     * @param createDt Date new creation date.
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    /**
     * Return the entry version.
     * @return Int the entry version.
     */
    @Column(name="ROWVERSION", nullable=false, precision=22, scale=0)
    public int getRowversion() {
        return this.rowversion;
    }
    
    /**
     * Change the entry version.
     * @param rowversion int new entry version.
     */
    public void setRowversion(int rowversion) {
        this.rowversion = rowversion;
    }
}