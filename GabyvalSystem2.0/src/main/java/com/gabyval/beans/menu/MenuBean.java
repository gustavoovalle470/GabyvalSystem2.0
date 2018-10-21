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
 * |   1.0   |  13/11/2017  |      GAOQ      | Creacion del bean de creacion del menu principal de la aplicacion.                                      |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
 */
package com.gabyval.beans.menu;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.controller.security.SecurityMan;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.persistence.user.security.AdSecMenulinks;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * This class controlled the principal menu bar of application.
 * @author GAOQ
 * @version 1.0
 * @since 13/11/2017
 */
@ManagedBean(name = "MenuBean")
@SessionScoped
public class MenuBean implements Serializable{
    
    private String pageView;
    private String username;
    private HashMap<String, AdSecMenulinks> rendered_menus;
    private ArrayList<String> rendered_p_menus;
    
    public MenuBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            rendered_p_menus = SecurityMan.getInstance(username).getPrincipals();
            rendered_menus = SecurityMan.getInstance(username).getFunctionsAllowed();
            pageView = "/core/welcome.xhtml";
        } catch (GB_Exception ex) {
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setActualView(String menu_id){
        this.pageView = getDestination(menu_id);
    }
    
    public String getActualView(){
        return pageView;
    }
    
    public void setView(int id){
        System.out.println("com.gabyval.beans.menu.MenuBean.setView()");
        if(id == 1){
            setActualView("/core/welcome.xhtml");
        }else{
            setActualView("/core/welcome_1.xhtml");
        }
    }
    
    public String getMenuName(String menu_id){
        if(isRenderedComponent(menu_id)){
            return rendered_menus.get(menu_id).getGbMenuName();
        }
        return "Funcion no disponible";
    }
    
    public String getDestination(String menu_id){
        if(isRenderedComponent(menu_id)){
            return rendered_menus.get(menu_id).getGbPageView();
        }
        return "#";
    }
    
    public boolean isRenderedComponent(String menu_id){
        if(rendered_menus != null){
            return rendered_menus.containsKey(menu_id);
        }
        return false;
    }
    
    public boolean isRenderedPrincipalMenu(String p_menu_id){
        if(rendered_p_menus != null){
            return rendered_p_menus.contains(p_menu_id);
        }
        return false;
    }
}
