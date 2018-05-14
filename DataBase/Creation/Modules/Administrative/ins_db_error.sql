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
 *       ins_db_error				|		1.0			|     GUSTAVO ADOLFO OVALLE QUINTERO	|						09/04/2017					   |
 *******************************************************************************************************************************************************/
 
/* *****************************************************************
 * Esquema: GABYVAL
 * Tabla: AD_ERROR
 * Cantidad inserts: 4
 * Descripcion: Inserts iniciales de errores predeterminados.
 * *****************************************************************/
INSERT INTO AD_ERROR VALUES (1, 'El sistema no pudo encontrar el registro solicitado o la conexión no está disponible. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (2, 'El sistema no pudo actualizar el registro solicitado o la conexión no está disponible. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (3, 'El sistema no pudo guardar el registro solicitado o la conexión no está disponible. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (4, 'El sistema no pudo borrar el registro solicitado o la conexión no está disponible. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (5, 'El sistema no pudo recargar el registro solicitado o la conexión no está disponible. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (6, 'El sistema no pudo borrar el registro solicitado o la conexión no está disponible. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (7, 'No se pudo acceder al registro de la base de datos. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (8, 'No se pudo guardar al registro de la base de datos. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (9, 'No se pudo actualizar al registro de la base de datos. Contacte con su administrador.', 'ERROR', 'Error grave en la base de datos.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (10, 'El usuario $ no se encuentra registrado en el sistema. Por favor verifique e intente nuevamente.', 'ERROR', 'Error intentando iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (11, 'El usuario $ actualmente tiene una sesión iniciada en otro equipo. Por favor verifique e intente nuevamente.', 'ERROR', 'Error intentando iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (12, 'El usuario $ fue dado de baja del sistema, por favor contacte con su administrador.', 'ERROR', 'Error intentando iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (13, 'El usuario y/o contraseña no son válidos, por favor verifique e intente nuevamente.', 'ERROR', 'Error intentando iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (14, 'El sistema no pudo iniciar sesión para el usuario $.', 'ERROR', 'Error intentando iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);

COMMIT;