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
 * |   1.0   |  09/07/2017  |      GAOQ      | Creacion de la entidad de control para los catalogos.                                                   |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.constants;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * This is a entity class for catalogs.
 * @author GAOQ
 * @version 1.0
 * @since 09/07/2017.
 */
@Entity
@Table(name="AD_CATALOGS" ,schema="GABYVAL", uniqueConstraints = @UniqueConstraint(columnNames={"GB_CATALOG_NAME", "GB_CATALOG_ITEM"}))
public class AdCatalogs  implements java.io.Serializable {


    private int gbCatalogId;//Unique id for a catalog.
    private String gbCatalogName;//Catalog name.
    private int gbCatagogItemId;//Item id for this catalog.
    private String gbCatalogItem;//Item into this catalog.
    private String gbCatalogDescription;//Description for this item catalog.
    private Date createDt;//Creation date.
    private int rowversion;//Version.

    /**
     * Create a new instance for this entity without params.
     */
    public AdCatalogs() {
    }

    /**
     * Create a new instance for this entity with following params:
     * @param gbCatalogId unique id for this catalog
     * @param gbCatalogName the catalog name.
     * @param gbCatagogItemId the item id for this catalog.
     * @param gbCatalogItem item for this catalog.
     * @param gbCatalogDescription Description for this catalog.
     * @param createDt date creation 
     * @param rowversion row version.
     */
    public AdCatalogs(int gbCatalogId, String gbCatalogName, int gbCatagogItemId, String gbCatalogItem, String gbCatalogDescription, Date createDt, int rowversion) {
       this.gbCatalogId = gbCatalogId;
       this.gbCatalogName = gbCatalogName;
       this.gbCatagogItemId = gbCatagogItemId;
       this.gbCatalogItem = gbCatalogItem;
       this.gbCatalogDescription = gbCatalogDescription;
       this.createDt = createDt;
       this.rowversion = rowversion;
    }
   
    /**
     * Return the unique id.
     * @return int the unique id.
     */
    @Id
    @Column(name="GB_CATALOG_ID", unique=true, nullable=false, precision=22, scale=0)
    public int getGbCatalogId() {
        return this.gbCatalogId;
    }
    
    /**
     * Change the unique id.
     * @param gbCatalogId int new unique id.
     */
    public void setGbCatalogId(int gbCatalogId) {
        this.gbCatalogId = gbCatalogId;
    }

    /**
     * Return the catalog name.
     * @return String the catalog name.
     */
    @Column(name="GB_CATALOG_NAME", unique=true, nullable=false, length=50)
    public String getGbCatalogName() {
        return this.gbCatalogName;
    }
    
    /**
     * Change the catalog name.
     * @param gbCatalogName String the new catalog name.
     */
    public void setGbCatalogName(String gbCatalogName) {
        this.gbCatalogName = gbCatalogName;
    }

    /**
     * Return the catalog item id.
     * @return int the catalog item id.
     */
    @Column(name="GB_CATAGOG_ITEM_ID", nullable=false, precision=22, scale=0)
    public int getGbCatagogItemId() {
        return this.gbCatagogItemId;
    }
    
    /**
     * Change the catalog item id.
     * @param gbCatagogItemId int the new item id.
     */
    public void setGbCatagogItemId(int gbCatagogItemId) {
        this.gbCatagogItemId = gbCatagogItemId;
    }

    /**
     * Return the item for this catalog.
     * @return String the item catalog.
     */
    @Column(name="GB_CATALOG_ITEM", nullable=false, length=60)
    public String getGbCatalogItem() {
        return this.gbCatalogItem;
    }
    
    /**
     * Change the item for this catalog.
     * @param gbCatalogItem String new item.
     */
    public void setGbCatalogItem(String gbCatalogItem) {
        this.gbCatalogItem = gbCatalogItem;
    }

    /**
     * Return the description for item catalog.
     * @return String the description.
     */
    @Column(name="GB_CATALOG_DESCRIPTION", nullable=false, length=150)
    public String getGbCatalogDescription() {
        return this.gbCatalogDescription;
    }
    
    /**
     * Change the description for this item catalog.
     * @param gbCatalogDescription This a catalog description.
     */
    public void setGbCatalogDescription(String gbCatalogDescription) {
        this.gbCatalogDescription = gbCatalogDescription;
    }

    /**
     * Return the creation date 
     * @return Date the creation date.
     */
    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_DT", nullable=false, length=7)
    public Date getCreateDt() {
        return this.createDt;
    }
    
    /**
     * Change the creation date.
     * @param createDt Date the new creation date.
     */
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    /**
     * Return the row version.
     * @return int the row version.
     */
    @Column(name="ROWVERSION", nullable=false, precision=22, scale=0)
    public int getRowversion() {
        return this.rowversion;
    }
    
    /**
     * Change the row version.
     * @param rowversion int the new row version.
     */
    public void setRowversion(int rowversion) {
        this.rowversion = rowversion;
    }
}