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
 *	   ins_db_admin_user			|		1.0			|     GUSTAVO ADOLFO OVALLE QUINTERO	|		                 26/03/2017                    |
 *******************************************************************************************************************************************************/

/* *********************************************************************
 * Esquema: 	GABYVAL
 * Tabla: 		ad_user_profile
 * Descripcion: Tabla que contendra los usuarios registrados del sistema
 * *********************************************************************/ 
CREATE TABLE GABYVAL.ad_user_profiling
(	gb_profile_name		VARCHAR(100) NOT NULL,
	gb_username			VARCHAR(20)  NOT NULL,
	gb_user_create		VARCHAR(20)  NOT NULL,
	gb_last_user_up		VARCHAR(20)  NOT NULL,
	gb_last_up_dt		TIMESTAMP	 NOT NULL,
	create_dt			DATE		 NOT NULL,
	rowversion			INTEGER		NOT NULL
);

/* ***********************************************************************************************
 * Esquema: 			 GABYVAL
 * Tabla: 				 ad_users
 * Columna: 			 gb_username
 * Adicion/Modificacion: Modificacion
 * Descripcion: 		 Se modifica la columna para establecerla como clave primaria de la tabla.
 * ***********************************************************************************************/
ALTER TABLE GABYVAL.ad_user_profiling ADD CONSTRAINT PK_User_profiling PRIMARY KEY (gb_profile_name, gb_username);

/* *****************************************************************
 * Esquema: 		 GABYVAL
 * Tabla: 			 gb_username
 * Cantidad inserts: 1
 * Descripcion: 	 Adicion del usuario administrador del sistema.
 * *****************************************************************/
INSERT INTO GABYVAL.ad_user_profiling VALUES('ADMINISTRATOR', 'ADMINISTRATOR', 'SYSTEM', 'SYSTEM', SYSDATE, SYSDATE, 0);
COMMIT;