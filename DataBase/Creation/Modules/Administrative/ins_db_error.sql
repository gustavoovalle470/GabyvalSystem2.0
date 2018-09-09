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
 * Cantidad inserts: 34
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
INSERT INTO AD_ERROR VALUES (14, 'El sistema no pudo iniciar sesión para el usuario $. Por favor contacte con su administrador.', 'ERROR', 'Error intentando iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (15, 'El sistema no pudo cerrar la sesión del usuario $. Por favor contacte con su administrador.', 'ERROR', 'Error intentando iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (16, 'El sistema no pudo realizar el cambio de contraseña. Por favor contacte con su administrador.', 'ERROR', 'Error al actualizar credenciales.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (17, 'El sistema no pudo encontrar el usuario $, verifique e intente nuevamente.', 'ERROR', 'Error obteniendo usuario.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (18, 'Se inicio correctamente la sesión. Bienvenido $.', 'INFO', 'Éxito al iniciar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (19, 'La sesión finalizó correctamente.', 'INFO', 'Éxito al cerrar sesión.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (20, 'Error al cargar los modulos de seguridad.', 'ERROR', 'Error al cargar la seguridad.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (21, 'Error al cargar los sub modulos de seguridad.', 'ERROR', 'Error al cargar la seguridad.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (22, 'Se adiciono correctamente la vista.', 'WARN', 'Éxito al adicionar seguridades.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (23, 'No se pudo actualizar el registro de seguridad. Por favor contacte con su administrador.', 'ERROR', 'Error al adicionar seguridades.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (24, 'Error al guardar la vista $. Verifique e intente nuevamente.', 'ERROR', 'Error al adicionar la vista.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (25, 'La seguridad se actualizo correctamente.', 'WARN', 'Éxito al adicionar seguridades.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (26, 'Se adiciono correctamente la vista al perfil $. Los cambios se veran reflejados desde el próximo inicio de sesión.', 'WARN', 'Éxito al adicionar seguridades.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (27, 'No se pudo adicionar la vista al perfil seleccionado. Contacte con su administrador.', 'ERROR', 'Error al adicionar seguridades.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (28, 'No se pudo crear el usuario en el sistema. Contacte con su administrador.', 'ERROR', 'Error creando usuarios.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (29, 'No se pudo guardar la información personal del usuario. Contacte con su administrador.', 'ERROR', 'Error creando usuarios.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (30, 'Error al adjuntar la foto de perfil del usuario. Contacte con su administrador.', 'ERROR', 'Error creando usuarios.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (31, 'La contraseña de usuario se modifico correctamente.', 'INFO', 'Éxito al modificar la contraseña.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (32, 'La contraseña actual no es correcta. Verifique e intente nuevamente.', 'ERROR', 'Error al cambiar la contraseña.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (33, 'Se ha presentado un problema al modificar la contraseña, si el problema persiste contacte con su administrador.', 'ERROR', 'Error al cambiar la contraseña.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);
INSERT INTO AD_ERROR VALUES (34, 'La contraseña no cumple con las politicas definidas o la confirmacion fallo. Por favor valide e intente de nuevo.', 'ERROR', 'Error al cambiar la contraseña.', 'SYSTEM', 'SYSTEM', to_timestamp(sysdate,'DD/MM/RR HH12:MI:SSXFF AM'),1,to_date(sysdate,'DD/MM/RR'),0);

COMMIT;