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
 * |   1.0   |  09/04/2017  |      GAOQ      | Creacion del cargador de clases.                                                                        |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */


package com.gabyval.core.connections.classloader;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * This is the entity class for table of class resource of the system.
 * @author Gustavo Adolfo Ovalle Quintero.
 * @version 1.0
 * @since 09/04/2017
 */
@Entity
@Table(name="AD_RESOURCE" ,schema="GABYVAL", uniqueConstraints = @UniqueConstraint(columnNames="GB_RESOURCE"))
public class AdResource  implements java.io.Serializable {
    
    private int gbResourceId; //Id unique of the resource IS PK.
    private String gbResource; //String whit the class path for the resource.
    private String gbResourceDesc; //String whit resource's description.
    private Date createDt; //Date of entry creation.
    private int rowversion; //Actually entry version.

    /**
     * Create a new instance of this entity.
     */ 
    public AdResource() {
    }

    /**
     * Create a new instance of this entity whit the following parameters:
     * @param gbResourceId int id unique of the resource.
     * @param gbResource String resource class path
     * @param createDt Date entry create
     * @param rowversion int actually entry version
     */
    public AdResource(int gbResourceId, String gbResource, Date createDt, int rowversion) {
        this.gbResourceId = gbResourceId;
        this.gbResource = gbResource;
        this.createDt = createDt;
        this.rowversion = rowversion;
    }

    /**
     * Create a new instance of this entity whit the following parameters:
     * @param gbResourceId int id unique of the resource.
     * @param gbResource String resource class path.
     * @param gbResourceDesc String resource description.
     * @param createDt Date entry create
     * @param rowversion int actually entry version
     */
    public AdResource(int gbResourceId, String gbResource, String gbResourceDesc, Date createDt, int rowversion) {
       this.gbResourceId = gbResourceId;
       this.gbResource = gbResource;
       this.gbResourceDesc = gbResourceDesc;
       this.createDt = createDt;
       this.rowversion = rowversion;
    }
   
    /**
     * Return the id value.
     * @return int id value.
     */
    @Id     
    @Column(name="GB_RESOURCE_ID", unique=true, nullable=false, precision=22, scale=0)
    public int getGbResourceId() {
        return this.gbResourceId;
    }
    
    /**
     * Change the id value.
     * @param gbResourceId int the new id value.
     */
    public void setGbResourceId(int gbResourceId) {
        this.gbResourceId = gbResourceId;
    }

    /**
     * Return string whit class path resource.
     * @return String whit class path
     */
    @Column(name="GB_RESOURCE", unique=true, nullable=false, length=100)
    public String getGbResource() {
        return this.gbResource;
    }
    
    /**
     * Change the class path for this resource.
     * @param gbResource String new class path.
     */
    public void setGbResource(String gbResource) {
        this.gbResource = gbResource;
    }
    
    /**
     * Return the resource description.
     * @return String resource description.
     */
    @Column(name="GB_RESOURCE_DESC", length=100)
    public String getGbResourceDesc() {
        return this.gbResourceDesc;
    }
    
    /**
     * Change the resource description.
     * @param gbResourceDesc String new resource description.
     */
    public void setGbResourceDesc(String gbResourceDesc) {
        this.gbResourceDesc = gbResourceDesc;
    }

    /**
     * Return the resource date creation.
     * @return Date of creation.
     */
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_DT", nullable=false, length=7)
    public Date getCreateDt() {
        return this.createDt;
    }
    
    /**
     * Change the resource date creation.
     * @param createDt Date new creation date.
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }
    
    /**
     * Return the actually entry version.
     * @return int actually entry version.
     */
    @Column(name="ROWVERSION", nullable=false, precision=22, scale=0)
    public int getRowversion() {
        return this.rowversion;
    }
    
    /**
     * Change the actually version entry.
     * @param rowversion int new version entry.
     */
    public void setRowversion(int rowversion) {
        this.rowversion = rowversion;
    }
}


