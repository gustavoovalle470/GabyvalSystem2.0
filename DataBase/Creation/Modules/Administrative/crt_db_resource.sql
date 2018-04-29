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
 *	    crt_db_resource    			|		1.0			|     GUSTAVO ADOLFO OVALLE QUINTERO	|						26/03/2017   				   |
 *******************************************************************************************************************************************************/

/* *****************************************************************
 * Esquema: 	GABYVAL
 * Tabla: 		ad_resource
 * Descripcion: Tabla de configuraciones generales.
 * *****************************************************************/
CREATE TABLE GABYVAL.ad_resource
(
	gb_resource_id			INTEGER			NOT NULL,
	gb_resource				VARCHAR(600)	NOT NULL,
	gb_resource_desc		VARCHAR(100)			,
	create_dt				DATE		NOT NULL,
	rowversion				INTEGER			NOT NULL
);
 
/* *****************************************************************
 * Esquema:     GABYVAL
 * Tabla: 	    ad_resource
 * Constraint:  PK_Resource
 * Descripcion: Creacion de la clave primaria de esta tabla
 * *****************************************************************/
ALTER TABLE GABYVAL.ad_resource ADD CONSTRAINT PK_Resource PRIMARY KEY (gb_resource_id);
 
/* *****************************************************************
 * Esquema:     GABYVAL
 * Tabla: 	    ad_resource
 * Constraint:  UK_ResourceName
 * Descripcion: Creacion de clave unica para la tabla
 * *****************************************************************/
ALTER TABLE GABYVAL.ad_resource ADD CONSTRAINT UK_ResourceName UNIQUE (gb_resource); 
  
COMMIT;