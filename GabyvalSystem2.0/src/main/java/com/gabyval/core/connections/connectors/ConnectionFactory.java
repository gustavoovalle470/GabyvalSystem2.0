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
 * |   1.0   |  23/06/2016  |      GAOQ      | Creacion de la clase de conexion.                                                                       |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  24/07/2016  |      GAOQ      | Correccion no documentada                                                                               |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.3   |  14/01/2017  |      GAOQ      | Correccion no documentada                                                                               |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.4   |  10/02/2017  |      GAOQ      | Correccion no documentada                                                                               |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.5   |  29/03/2017  |      GAOQ      | Correccion no documentada                                                                               |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.6   |  01/04/2017  |      GAOQ      | Correccion no documentada                                                                               |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.7   |  09/04/2017  |      GAOQ      | Se establece el mecanismo de carga de clases para que se realice por medio de la base de datos          |
 * |         |              |                | unicamente. Se crea la precarga y apertura de sesion y el manejo transaccional.                         |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */

package com.gabyval.core.connections.connectors;

import com.gabyval.core.constants.GB_CommonStrConstants;
import com.gabyval.core.IO.GB_IOController;
import com.gabyval.core.connections.classloader.AdResource;
import com.gabyval.core.logger.GB_Logger;
import java.util.HashMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This class control the connection spool, charges all resource and entities
 * registered into database.
 * @author Gustavo Adolfo Ovalle Quintero
 * @version 1.7
 * @since 23/06/2016
 */
public class ConnectionFactory{

    private static final GB_Logger LOG = GB_Logger.getLogger(ConnectionFactory.class);//Central LOG for this class.
    private static SessionFactory sessionFactory;//Actually database session.
    private static Configuration conf; //Actually session configuration.
    
    /**
     * Return the open session whit data base.
     * @return Session whit database.
     */
    public static Session getConnection() {
        try{
            configure();
            classLoader();
            sessionFactory = conf.buildSessionFactory();
            return sessionFactory.openSession();
        }catch (Exception ex){
            LOG.fatal(ex);
            return null;
        }
    }
    
    /**
     * Restart pool connection whit database.
     * @return true if connection was restart. False otherwise.
     */
    public static boolean restartConnection(){
        sessionFactory = null;
        configure();
        classLoader();
        sessionFactory = conf.buildSessionFactory();
        return true;
    }
    
    /**
     * Close all database connections.
     * @return true if connection is close. False otherwise.
     */
    public static boolean closeConnection(){
        sessionFactory.close();
        return true;
    }
    
    /**
     * This method configure all parameters to establish the data base connection.
     */
    private static void configure(){
        conf = new Configuration();
        LOG.info("Iniciando conexcion a la base de datos.");
        HashMap<String, String> configuration = GB_IOController.getConfiguration(GB_CommonStrConstants.CONNECT_PROP, "=");
        LOG.info("Cargando la configuracion de la conexion.");
        for(String key: configuration.keySet()){
            LOG.info("Nombre de la propiedad: "+key+" valor actual: "+configuration.get(key));
            conf.setProperty(key, configuration.get(key));
        }
        LOG.info("Finalizo con exito la carga de la conexion.");
        Class c;
        try {
            c = Class.forName("com.gabyval.core.connections.classloader.AdResource");
            conf.addAnnotatedClass(c);
        } catch (ClassNotFoundException ex) {
            LOG.fatal(ex);
        }
        sessionFactory = conf.buildSessionFactory();
    }
    
    /**
     * Charge all class registered into data base configuration. This classes are
     * all entity manager into application LOGic and database persistence.
     */
    private static void classLoader(){
        boolean finished = false;
        int resourceId= 1;
        LOG.info("Estableciendo conexion para realizar la carga de las entidades de persistencia...");
        Session confSession=  sessionFactory.openSession();
        if(confSession.isConnected()){
            confSession.beginTransaction();
            while(!finished){
                AdResource resource = (AdResource)confSession.get(AdResource.class, resourceId);
                if(resource == null){
                    finished = true;
                }else{
                    try {
                        LOG.debug("Cargando clase: "+resource.getGbResource());
                        conf.addAnnotatedClass(Class.forName(resource.getGbResource()));
                        LOG.debug("La clase: "+resource.getGbResource()+" fue encotrada y cargada correctamente.");
                    } catch (ClassNotFoundException ex) {
                        LOG.fatal("ERROR Cargando la clase: "+resource.getGbResource()+" verifique que el classpath existe.");
                    }finally{
                        resourceId++;
                    }
                }
            }
            LOG.info("Finaliza carga de controladores, se cargaron "+resourceId+" clases.");
            LOG.info("Finaliza la apertura de la sesion de comunicacion con la base de dateos.");
        }else{
            LOG.info("No se pudo establecer la conexion con la base de datos. GABYVAL no esta operando actualmente. Contacte con su administrador.");
        }
    }
}