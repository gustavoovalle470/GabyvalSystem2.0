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
 * |   1.0   |  23/06/2016  |      GAOQ      | Creacion del lector general de archivos.                                                                |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 * |   1.1   |  09/04/2017  |      GAOQ      | Correccion de lectura de archivos de propiedades.                                                       |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.core.IO;

import com.gabyval.core.logger.GB_Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class helper could be used to write and load different file of configuration
 * or other type.
 * @author Gustavo Ovalle
 * @version 1.1
 * @since June 23th, 2016.
 */
public class GB_IOController {
    
    private static GB_Logger log = GB_Logger.getLogger(GB_IOController.class);
    
    /**
     * Load the configuration file separate by any string
     * @param file_path String the path of file to read.
     * @param separator String the separator of information contain. 
     * @return HasMap the mapping information.
     */
    public static HashMap<String, String> getConfiguration(String file_path, String separator){
        HashMap<String, String> mapping = new HashMap<>();
        File f = new File(file_path);
        if(f.exists()){
            try {
                String line;
                BufferedReader br = new BufferedReader(new FileReader(f));
                line = br.readLine();
                while(line != null){
                    String [] data = line.split(separator);
                    mapping.put(data[0], data[1]);
                    line = br.readLine();
                }
                br.close();
            } catch (Exception ex) {
                log.error(ex);
            }
        }
        return mapping;
    }
    
    /**
     * Load any file. 
     * @param file_path String the path of file to read.
     * @return ArrayList the lines in the file.
     */
    public static ArrayList<String> getContent(String file_path){
        ArrayList<String> mapping = new ArrayList<>();
        File f = new File(file_path);
        if(f.exists()){
            try {
                String line;
                BufferedReader br = new BufferedReader(new FileReader(f));
                line = br.readLine();
                while(line != null){
                    mapping.add(line);
                    line = br.readLine();
                }
                br.close();
            } catch (Exception ex) {
                log.error(ex);
            }
        }
        return mapping;
    }
    
    /**
     * Write any content in the file.
     * @param file_path String the path of file to write.
     * @param content String content to write.
     */
    public static void wirteContent(String file_path, String content){
        File f = new File(file_path);
        if(f.exists() && f.canWrite()){
            f.delete();
        }
        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(content);
                bw.close();
            } catch (Exception ex) {
                log.error(ex);
            }
    }
    
    /**
     * Write configuration file.
     * @param file_path String the path of file to write.
     * @param content HasMap the mapping configuration.
     * @param separator String the separator to use.
     */
    public static void wirteConf(String file_path, HashMap<String, String> content, String separator){
        File f = new File(file_path);
        if(f.exists() && f.canWrite()){
            f.delete();
        }
        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                for(String key: content.keySet()){
                    bw.write(key+separator+content.get(key));
                }
                bw.close();
            } catch (Exception ex) {
                log.error(ex);
            }
    }
    
    /**
     * Write any object in the file.
     * @param file_path String the path of file to write.
     * @param content Object the object to save
     */
    public static void wirteObject(String file_path, Object content){
        File f = new File(file_path);
        if(f.exists() && f.canWrite()){
            f.delete();
        }
        try {
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file_path));
                os.writeObject(content);
                os.close();
            } catch (Exception ex) {
                log.error(ex);
            }
    }
    
    /**
     * Load any object from file
     * @param file_path String the path of file to write.
     * @return Object the object content into the file.
     */
    public static Object loadObject(String file_path){
        Object ob = null;
        File f = new File(file_path);
        if(f.exists() && f.canWrite()){
            f.delete();
        }
        try {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(file_path));
                ob= os.readObject();
                os.close();
            } catch (IOException | ClassNotFoundException ex) {
                log.error(ex);
            }
        return ob;       
    }
}