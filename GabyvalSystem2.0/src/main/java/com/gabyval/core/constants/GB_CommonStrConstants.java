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
 * |   1.0   |  09/04/2017  |      GAOQ      | Creacion constantes de valores cadena de texto.                                                         |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  13/06/2017  |      GAOQ      | Adicion de rutas de archivos de propiedades para fechas del sistema.                                    |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  19/06/2017  |      GAOQ      | Adicion de constantes de scheduler.                                                                     |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.3   |  22/07/2017  |      GAOQ      | Correccion rutas para inclusion en el aplicativo WEB                                                    |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.4   |  26/08/2017  |      GAOQ      | Adicion de comodin para la recuperacion de mensajes.                                                    |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.5   |  07/09/2017  |      GAOQ      | Adicion de la mascara de fecha y hora.                                                                  |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.6   |  13/11/2017  |      GAOQ      | Cambio de nombre de propiedades de fecha y hora, adicion de nombre de modulo para fecha y hora.         |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.7   |  04/06/2018  |      GAOQ      | Correcion de los archivos de configuracion.                                                             |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
*/
package com.gabyval.core.constants;

/**
 * This class contains all string constant of the application.
 * @author GAOQ
 * @version 1.1
 * @since 09/04/2017.
 */
public class GB_CommonStrConstants {
    /***************************************************************************************************************************
     *APP Params
     ***************************************************************************************************************************/
    public static final String APP_NAME = "GABYVAL System";
    public static final String SYS_DATE_KEY = "SysDate";
    public static final String GBCENTRALSCH = "GBCentral";
    public static final String GB_EACH_SEC_SCH = "0/1 * * * * ?";
    public static final String GB_SCH = "GBJobTr";
    public static final String ERROR_RPLCE  = "$";
    public static final String PUBLIC_APP_KEY = "GabyValSystemsG&TSystemsCorporation.";
    /***************************************************************************************************************************
     *RUTES
     ***************************************************************************************************************************/
    public static final String PAHT_EXIT_LOG_LOG4J = "/GABYVAL/gblogs/SystemOut.log";
    public static final String PAHT_EXIT_LOG_SCH = "/GABYVAL/gblogs/SchOut.log";
    public static final String CONNECT_PROP = "/GABYVAL/conf/connection.properties";
    public static final String SYS_PARAMS = "/GABYVAL/conf/SystemParams.properties";
    /***************************************************************************************************************************
     *PATTERNS
     ***************************************************************************************************************************/
    public static final String PATTERN_EXIT_LOG = "%d{dd/mm/yyyy HH:mm:ss,SSS} - SystemOut [%t] %-5p [%c:%L]: %m%n";
    public static final String DEFAULT_DATETIME_FORMAT  = "dd/MM/yyyy HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT  = "dd/MM/yyyy";
    public static final String PROPERTY_DATE_FORMAT  = "DATEPATTERN";
    public static final String PROPERTY_DATETIME_FORMAT  = "DATETIMEPATTERN";
    public static final String PROPERTY_DATETIME_FORMAT_FULL  = "DATETIMEPATTERNFULL";
    /***************************************************************************************************************************
     *CATALOGS 
     ***************************************************************************************************************************/
    public static final String CT_GENDER  = "GENDER";
    public static final String CT_IDTYPE  = "DOCUMENTTYPE";
    public static final String CT_STATUS  = "STATUS";
    public static final String CT_LSTATUS  = "LOGIN_STATUS";
    public static final String CT_OSTATUS  = "OPERATIVE_STATUS";
    public static final String CT_LEVEL_ERROR = "ERROR_LEVEL";
    public static final String CT_TYPE_CONF = "TYPE_CONF";
  /***************************************************************************************************************************
   *NIVELES DE ERROR
   ***************************************************************************************************************************/
    public static final String FATAL = "FATAL";
    public static final String ERROR = "ERROR";
    public static final String WARN = "WARN";
    public static final String INFO = "INFO";
}
