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
 * |   1.0   |  08/05/2017  |      GAOQ      | Creacion de la clase de entidad.                                                                        |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.commons.controllers;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This is a entity class for the ID controller.
 * @author GAOQ
 * @version 1.0
 * @since 08/05/2017
 */
@Entity
@Table(name="AD_ID_CONTROL", schema="GABYVAL")
public class AdIdControl  implements java.io.Serializable {


    private String gbNomTabla;//The name of the table that the id register is save.
    private int gbNumCtrl;//The actually number of the ID.
    private int gbNumInc;//The next number fot the ID.
    private Date createDt;//Date of create for this register.
    private int rowversion;//Entry rowversion.

    /**
     * Create a new instance for this entity without parameters.
     */
    public AdIdControl() {
    }

    /**
     * Create a new instance for this entity with the following parameters:
     * @param gbNomTabla String name of the table.
     * @param gbNumCtrl int actually number of ID for the table.
     * @param gbNumInc  int next number of ID for the table.
     */	
    public AdIdControl(String gbNomTabla, int gbNumCtrl, int gbNumInc) {
        this.gbNomTabla = gbNomTabla;
        this.gbNumCtrl = gbNumCtrl;
        this.gbNumInc = gbNumInc;
    }
    public AdIdControl(String gbNomTabla, int gbNumCtrl, int gbNumInc, Date createDt, int rowversion) {
       this.gbNomTabla = gbNomTabla;
       this.gbNumCtrl = gbNumCtrl;
       this.gbNumInc = gbNumInc;
       this.createDt = createDt;
       this.rowversion = rowversion;
    }
   
    /**
     * Return the table name.
     * @return String the table name.
     */
    @Id
    @Column(name="GB_NOM_TABLA", unique=true, nullable=false, length=30)
    public String getGbNomTabla() {
        return this.gbNomTabla;
    }
    
    /**
     * Change the table name.
     * @param gbNomTabla String the new table name.
     */
    public void setGbNomTabla(String gbNomTabla) {
        this.gbNomTabla = gbNomTabla;
    }

    /**
     * Return the actually ID number for the table.
     * @return int actually ID number.
     */
    @Column(name="GB_NUM_CTRL", nullable=false, precision=22, scale=0)
    public int getGbNumCtrl() {
        return this.gbNumCtrl;
    }
    
    /**
     * Change the actually ID number.
     * @param gbNumCtrl Actually id number.
     */
    public void setGbNumCtrl(int gbNumCtrl) {
        this.gbNumCtrl = gbNumCtrl;
    }
    
    /**
     * Return the next ID number.
     * @return int the next ID number.
     */
    @Column(name="GB_NUM_INC", nullable=false, precision=22, scale=0)
    public int getGbNumInc() {
        return this.gbNumInc;
    }
    
    /**
     * Change the next ID number.
     * @param gbNumInc int the new next number.
     */
    public void setGbNumInc(int gbNumInc) {
        this.gbNumInc = gbNumInc;
    }

    /**
     * Return the date of entry creation.
     * @return Date the date of entry creation.
     */
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_DT", length=7)
    public Date getCreateDt() {
        return this.createDt;
    }
    
    /**
     * Change the entry date creation.
     * @param createDt the new creation date.
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }
    
    /**
     * Return the entry version.
     * @return int the entry version.
     */
    @Column(name="ROWVERSION", precision=22, scale=0)
    public int getRowversion() {
        return this.rowversion;
    }
    
    /**
     * Change the rowversion.
     * @param rowversion int the new row version.
     */
    public void setRowversion(int rowversion) {
        this.rowversion = rowversion;
    }
}