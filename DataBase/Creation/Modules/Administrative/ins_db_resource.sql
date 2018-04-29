/* *****************************************************************************************************************************************************
 * *****************************************************************************************************************************************************
 * *****************************************************************************************************************************************************
 * *******************************                                                                              ****************************************
 * *******************************	********** ********** ******    **      ** **      ** ********** **         ****************************************
 * *******************************	********** **********  ******   **      ** **      ** ********** **         ****************************************
 * *******************************	**         **      **  **  **   **      ** **      ** **      ** **         ****************************************
 * *******************************	**         **      **  **  **   **      ** **      ** **      ** **         ****************************************
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
 *_____Nombre del Archivo___________|_____VERSION_______|_________________AUTOR_________________|____________Fecha de creacion/modificacion____________|
 *       ins_db_resource			|		1.0			|     GUSTAVO ADOLFO OVALLE QUINTERO	|						09/04/2017					   |
 *******************************************************************************************************************************************************/
 
/* *****************************************************************
 * Esquema: GABYVAL
 * Tabla: AD_RESOURCE
 * Cantidad inserts: 5
 * Descripcion: Inserts iniciales de recursos predeterminados.
 * *****************************************************************/
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (1,'com.gabyval.core.module.configuration.AdModuleConfiguration','Controlador central de configuracion',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (2,'com.gabyval.core.exception.AdError','Catalogo central de errores',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (4,'com.gabyval.core.time.AdNoWorkingDays','No working days',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (3,'com.gabyval.core.time.AdSystemControl','Control central del fecha y hora del sistema',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (5,'com.gabyval.core.commons.controllers.AdIdControl','Control de claves primarias unicas para las tablas',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (6,'com.gabyval.core.scheduler.AdJob','Jobs mapeables del Scheduler',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (7,'com.gabyval.core.scheduler.AdExecuteJob','Log de ejecucion de jobs',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (8,'com.gabyval.persistence.user.AdUsers','Taba de usuarios del sistema',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (9,'com.gabyval.persistence.user.security.AdUserProfiling','Tabla de relacion entre usuarios y perfiles.',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (10,'com.gabyval.persistence.user.security.AdUserProfile','Perfiles de usuarios configurados.',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (11,'com.gabyval.persistence.user.security.AdSecMenulinks','Menus activos del sistema.',SYSDATE,0);
INSERT INTO GABYVAL.AD_RESOURCE (GB_RESOURCE_ID,GB_RESOURCE,GB_RESOURCE_DESC,CREATE_DT,ROWVERSION) VALUES (12,'com.gabyval.persistence.user.security.AdSecMenuProfiling','Tabla de relacion entre menus y perfiles de usuario',SYSDATE,0);
COMMIT;