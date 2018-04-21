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
 * |   1.0   |  09/04/2017  |      GAOQ      | Creacion de la clase de entidad.                                                                        |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */

package com.gabyval.core.module.configuration;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * This entity class control the information for all configurations in database.
 * @author Gustavo Adolfo Ovalle Quintero.
 * @version 1.0
 * @since 09/04/2017
 */
@Entity
@Table(name="AD_MODULE_CONFIGURATION" ,schema="GABYVAL", uniqueConstraints = @UniqueConstraint(columnNames="GB_MODULE_CONFIG_NAME"))
public class AdModuleConfiguration  implements java.io.Serializable {

    private int gbModuleConfigId; //Unique id for the configuration.
    private String gbModuleConfigName; //Configuration name.
    private int gbModuleConfigType; //Configuration type (Java type)
    private String gbModuleConfigValue; //Configuration value
    private String gbModuleConfigDesc; //Configuration description.
    private Date createDt; // Entry creation date.
    private int rowversion; //Entry version num.

    /**
     * Create a new instance of this controller.
     */
    public AdModuleConfiguration() {
    }

    /**
     * Create a new instance of this controller whit a following parameter:
     * @param gbModuleConfigId int configuration id.
     * @param gbModuleConfigName String configuration name.
     * @param gbModuleConfigType String configuration type.
     * @param gbModuleConfigValue String configuration value.
     * @param gbModuleConfigDesc String configuration description.
     * @param createDt Date of creation
     * @param rowversion Actually version of this configuration.
     */
    public AdModuleConfiguration(int gbModuleConfigId, String gbModuleConfigName, int gbModuleConfigType, String gbModuleConfigValue, String gbModuleConfigDesc, Date createDt, int rowversion) {
       this.gbModuleConfigId = gbModuleConfigId;
       this.gbModuleConfigName = gbModuleConfigName;
       this.gbModuleConfigType = gbModuleConfigType;
       this.gbModuleConfigValue = gbModuleConfigValue;
       this.gbModuleConfigDesc = gbModuleConfigDesc;
       this.createDt = createDt;
       this.rowversion = rowversion;
    }
   
    /**
     * Return the configuration id.
     * @return int the configuration id
     */
    @Id
    @Column(name="GB_MODULE_CONFIG_ID", unique=true, nullable=false, precision=22, scale=0)
    public int getGbModuleConfigId() {
        return this.gbModuleConfigId;
    }
    
    /**
     * Change the module configuration id.
     * @param gbModuleConfigId int new configuration id.
     */
    public void setGbModuleConfigId(int gbModuleConfigId) {
        this.gbModuleConfigId = gbModuleConfigId;
    }

    /**
     * Return the configuration name.
     * @return String the configuration name.
     */
    @Column(name="GB_MODULE_CONFIG_NAME", unique=true, nullable=false, length=30)
    public String getGbModuleConfigName() {
        return this.gbModuleConfigName;
    }
    
    /**
     * Change the name for configuration name.
     * @param gbModuleConfigName String new configuration name.
     */
    public void setGbModuleConfigName(String gbModuleConfigName) {
        this.gbModuleConfigName = gbModuleConfigName;
    }
    
    /**
     * Return the configuration type.
     * @return String configuration type.
     */
    @Column(name="GB_MODULE_CONFIG_TYPE", nullable=false, length=15)
    public int getGbModuleConfigType() {
        return this.gbModuleConfigType;
    }
    
    /**
     * Change the configuration type.
     * @param gbModuleConfigType String new configuration type.
     */
    public void setGbModuleConfigType(int gbModuleConfigType) {
        this.gbModuleConfigType = gbModuleConfigType;
    }
    
    /**
     * Return the configuration value.
     * @return String the configuration value.
     */
    @Column(name="GB_MODULE_CONFIG_VALUE", nullable=false, length=100)
    public String getGbModuleConfigValue() {
        return this.gbModuleConfigValue;
    }
    
    /**
     * Change the configuration value.
     * @param gbModuleConfigValue String new configuration value. 
     */
    public void setGbModuleConfigValue(String gbModuleConfigValue) {
        this.gbModuleConfigValue = gbModuleConfigValue;
    }
    
    /**
     * return the configuration description.
     * @return String configuration description.
     */
    @Column(name="GB_MODULE_CONFIG_DESC", nullable=false, length=150)
    public String getGbModuleConfigDesc() {
        return this.gbModuleConfigDesc;
    }
    
    /**
     * Change the configuration description.
     * @param gbModuleConfigDesc String new description.
     */
    public void setGbModuleConfigDesc(String gbModuleConfigDesc) {
        this.gbModuleConfigDesc = gbModuleConfigDesc;
    }

    /**
     * Return the date of creation.
     * @return Date date of creation.
     */
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_DT", nullable=false, length=7)
    public Date getCreateDt() {
        return this.createDt;
    }
    
    /**
     * Change the creation date.
     * @param createDt Date new of creation. 
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }
    
    /**
     * Return version of this entry.
     * @return int the version.
     */
    @Column(name="ROWVERSION", nullable=false, precision=22, scale=0)
    public int getRowversion() {
        return this.rowversion;
    }
    
    /**
     * Change the version of this entry.
     * @param rowversion version of this entry 
     */
    public void setRowversion(int rowversion) {
        this.rowversion = rowversion;
    }
}