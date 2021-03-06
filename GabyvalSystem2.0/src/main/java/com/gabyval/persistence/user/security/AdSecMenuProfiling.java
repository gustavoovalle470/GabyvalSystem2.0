package com.gabyval.persistence.user.security;
// Generated Jul 29, 2017 1:26:07 PM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AdSecMenuProfiling generated by hbm2java
 */
@Entity
@Table(name="AD_SEC_MENU_PROFILING"
    ,schema="GABYVAL"
)
public class AdSecMenuProfiling  implements java.io.Serializable {


     private AdSecMenuProfilingId id;
     private Date createDt;
     private int rowversion;

    public AdSecMenuProfiling() {
    }

    public AdSecMenuProfiling(AdSecMenuProfilingId id, Date createDt, int rowversion) {
       this.id = id;
       this.createDt = createDt;
       this.rowversion = rowversion;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="gbMenuId", column=@Column(name="GB_MENU_ID", nullable=false, length=100) ), 
        @AttributeOverride(name="gbProfileName", column=@Column(name="GB_PROFILE_NAME", nullable=false, length=100) ) } )
    public AdSecMenuProfilingId getId() {
        return this.id;
    }
    
    public void setId(AdSecMenuProfilingId id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_DT", nullable=false, length=7)
    public Date getCreateDt() {
        return this.createDt;
    }
    
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    
    @Column(name="ROWVERSION", nullable=false, precision=22, scale=0)
    public int getRowversion() {
        return this.rowversion;
    }
    
    public void setRowversion(int rowversion) {
        this.rowversion = rowversion;
    }




}


