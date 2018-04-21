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
 * ******************************************************** Control de versiones ***********************************************************************
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * | Version |    Fecha     |  Responsable   |                                                  Comentarios                                            |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.0   |  17/05/2017  |      GAOQ      | Creacion del controlador generico.                                                                      |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  29/05/2017  |      GAOQ      | Adicion de busquedas a base de criterias                                                                |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.2   |  13/06/2017  |      GAOQ      | Adicion del manejo transaccion en concurrencia para el update y el save.                                |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.3   |  19/06/2017  |      GAOQ      | Adicion del metodo de actualizacion de objetos.                                                         |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.4   |  22/07/2017  |      GAOQ      | Se modifica el metodo load para utilizar el Hibernate.get y que en caso de no existir el objeto retorne |
 * |         |              |                | nulo y que la clase que hace la invocacion maneje el evento.                                            |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.5   |  26/08/2017  |      GAOQ      | Se modifica el metodo de update y save, para que controlen el caso en que el lock no este suelto.       |
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.commons.controllers;

import com.gabyval.core.connections.connectors.ConnectionFactory;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * This class will control the persistence with database.
 * @author GAOQ
 * @version 1.5
 * @since 17/05/2017
 */
public class PersistenceManager implements EntityManager{
    
    private final GB_Logger LOG = GB_Logger.getLogger(PersistenceManager.class);//Central log for this class.
    private final Session SESSION = ConnectionFactory.getConnection();//Connection with database.
    private static PersistenceManager instance;//Instance of this class.
    private final Lock lock = new ReentrantLock();
    
    public PersistenceManager() throws GB_Exception{
        if(SESSION == null){
            throw new GB_Exception(60);
        }
    }
    
    /**
     * This method return the instance of this controller.
     * @return PersistenceManager this instance.
     * @throws GB_Exception if:
     * <ol><li>The database session is null</li></ol>
     */
    public static PersistenceManager getInstance() throws GB_Exception{
        if (instance == null){
            instance = new PersistenceManager();
        }
        return instance;
    }
    
    /**
     * Charge any object from database, if the entity is mapped.
     * @param classType Class of the entity mapped.
     * @param id Serializable the unique ID for the object.
     * @return Object the object that was recovery.
     */
    @Override
    public Object load(Class classType, Serializable id){
        LOG.debug("The system has gone to load the object... Entity to recovery "+classType.getCanonicalName());
        try{
            Object o = SESSION.get(classType, id);
            if(o != null){
                return o;
            }else{
                LOG.error(new GB_Exception(1));
                return null;
            }
        }catch(Exception ex){
            LOG.error(new GB_Exception(7));
            LOG.error(ex);
            return null;
        }
    }

    /**
     * This method allow save any object into database.
     * @param o Object the object to save.
     * @return boolean true if the object can be save, false otherwise.
     * @throws GB_Exception if:
     * <ol><li> The object exist.</li>
     *     <li> The entity is no mapped</li>
     * </ol>
     */
    @Override
    public boolean save(Object o) throws GB_Exception {
        Transaction tx = startTransaccion();
        LOG.debug("The transaction is started for save in database.");
        if(tx != null){
            try{
                SESSION.save(o);
                LOG.debug("The object was save in database.");
                tx.commit();
                LOG.debug("The transactions finished successfully.");
                lock.unlock();
                return true;
            } catch (Exception ex){
                lock.unlock();
                LOG.error(new GB_Exception(3));
                LOG.error(ex);
                tx.rollback();
                SESSION.clear();
                LOG.error(new GB_Exception(8));
                throw new GB_Exception(ex);
            }
        }else{
           GB_Exception ex = new GB_Exception(6);
           LOG.error(ex);
           throw ex;
        }
    }

    /**
     * This method allow update the object.
     * @param o Object the object to update.
     * @return boolean true if the object was update successfully, false otherwise.
     * @throws GB_Exception if:
     * <ol><li> The object not exist.</li>
     *     <li> The entity is no mapped</li>
     * </ol>
     */
    @Override
    public boolean update(Object o) throws GB_Exception {
       Transaction tx = startTransaccion();
       LOG.debug("The transaction is started for update in database. "+o);
       if(tx != null){
           if(o != null){
               try{
                   SESSION.update(o);
                   LOG.debug("The object was update in database.");
                   tx.commit();
                   LOG.debug("The transactions finished successfully.");
                   lock.unlock();
                   return true;
                } catch (Exception ex){
                   lock.unlock();
                   LOG.error(new GB_Exception(2));
                   LOG.error(ex);
                   tx.rollback();
                   LOG.error(new GB_Exception(8));
                   throw new GB_Exception(ex);
                }
           }else{
               GB_Exception ex = new GB_Exception(9);
               LOG.error(9);
               throw ex;
           }
        }else{
           GB_Exception ex = new GB_Exception(6);
           LOG.error(ex);
           throw ex;
        }
    }

    /**
     * Obtain all objects of any kind.
     * @param classType Class class to load.
     * @return List of objects from database.
     * @throws GB_Exception if:
     * <ol><li> The object not exist.</li>
     *     <li> The entity is no mapped</li>
     * </ol>
     */
    @Override
    public synchronized List getAll(Class classType) throws GB_Exception{
        try{
            Query q = SESSION.createQuery("FROM "+classType.getSimpleName());
            return q.list();
        }catch(Exception ex){
            LOG.error(ex);
            throw new GB_Exception(ex);
        }
    }
    
    /**
     * This method execute a SQL sentence.
     * @param sql String the SQL sentence.
     * @return List return all objects found for the sentence.
     * @throws GB_Exception if:
     * <ol><li> The object not exist.</li>
     *     <li> The entity is no mapped</li>
     * </ol>
     */
    @Override
    public List runSQL(String sql) throws GB_Exception{
        try{
            SQLQuery q = SESSION.createSQLQuery(sql);
            return q.list();
        }catch(Exception ex){
            LOG.error(ex);
            throw new GB_Exception(ex);
        }
    }
    
    /**
     * This method execute a criteria HQL, the from expression must contain a entity name mapped.
     * @param sql String the criteria.
     * @return List of objects.
     * @throws GB_Exception if:
     */
    public List runCriteria(String sql) throws GB_Exception{
        try{
            Query q = SESSION.createQuery(sql);
            return q.list();
        }catch(Exception ex){
            LOG.error(ex);
            throw new GB_Exception(ex);
        }
    }
    
    
    public Transaction startTransaccion() throws GB_Exception{
        if(lock.tryLock()){
            return SESSION.beginTransaction();
        }
        return null;
    }
    
    /**
     * This method refresh the instance of a entity whit database info.
     * @param o Object to update.
     * @throws GB_Exception :
     */
    public void refresh(Object o) throws GB_Exception{
        try {
            LOG.debug("The object is reload from data base.");
            SESSION.refresh(o);
            LOG.debug("Reload is complete.");
        } catch (Exception ex) {
            LOG.fatal(ex);
            throw new GB_Exception(5);
        }
        
    }
    
    public void delete(Object o) throws GB_Exception{
        Transaction tx = startTransaccion();
        if(tx != null){
            try{
                SESSION.delete(o);
                tx.commit();
                lock.unlock();
            } catch (Exception ex) {
                lock.unlock();
                LOG.error(new GB_Exception(4));
                LOG.error(ex);
                tx.rollback();
                LOG.error(new GB_Exception(8));
                throw new GB_Exception(ex);
            }
        }else{
           GB_Exception ex = new GB_Exception(6);
           LOG.error(ex);
           throw ex; 
        }
    }
}
