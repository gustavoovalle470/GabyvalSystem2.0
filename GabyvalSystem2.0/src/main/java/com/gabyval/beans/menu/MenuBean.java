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
 * |   2.0   |  02/11/2018  |      GAOQ      | Se re crea la forma como se obtine el arbol de seguridad por usuario. Adaptacion para Manhattan theme.  |   
 * |---------|--------------|----------------|---------------------------------------------------------------------------------------------------------|
*/
package com.gabyval.beans.menu;

import com.gabyval.beans.system.security.SessionController;
import com.gabyval.beans.utilities.GBMessage;
import com.gabyval.controller.security.SecurityMan;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.security.AdSecMenulinks;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;

/**
 * This class controlled the principal menu bar of application.
 * @author GAOQ
 * @version 2.0
 * @since 13/11/2017
 */
@ManagedBean(name = "MenuBean")
@SessionScoped
public class MenuBean implements Serializable{
    
    private GB_Logger LOG = GB_Logger.getLogger(MenuBean.class);
    private String username;
    private DefaultMenuModel menu;
    
    public MenuBean(){
        try {
            username = SessionController.getInstance().getUser((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false));
            menu = new DefaultMenuModel();
            chargeMenus(); 
            
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
            GBMessage.putException(ex);
        }
    }
    
    public DefaultMenuModel getMenu() {
        return menu;
    }

    public void setMenu(DefaultMenuModel menu) {
        this.menu = menu;
    }

    private void chargeMenus() {
        try {
            for(AdSecMenulinks m: SecurityMan.getAllNodesMenus()){
                List<AdSecMenulinks> allowed = SecurityMan.getAllowedMenus(m, username);
                if(allowed != null && allowed.size()>0){
                    menu.addElement(assembleComponent(m, allowed));   
                }
            }
        } catch (GB_Exception ex) {
            LOG.fatal(ex);
            GBMessage.putException(ex);
        }
    }
    
    private MenuElement assembleComponent(AdSecMenulinks superMenu, List<AdSecMenulinks> menus) throws GB_Exception {
        if(superMenu.getGbIsNode() == 1){
            DefaultSubMenu sub = assembleSubMenu(superMenu);
            for(AdSecMenulinks mt : menus){
                List<AdSecMenulinks> allowed = SecurityMan.getAllowedMenus(mt, username);
                if(mt.getGbIsNode()==1 && allowed != null && allowed.size()>0){
                    sub.addElement(assembleComponent(mt, allowed));
                }else{
                    sub.addElement(assembleItem(mt));
                }
            }
            return sub;
        }else{
            return assembleItem(superMenu);
        }
    }
    
    private DefaultMenuItem assembleItem(AdSecMenulinks menu){
        DefaultMenuItem item = new DefaultMenuItem(menu.getGbMenuName());
        item.setId(menu.getGbMenuId());
        item.setIcon(menu.getGbIcon());
        item.setCommand(menu.getGbPageView());
        return item;
    }
    
    private DefaultSubMenu assembleSubMenu(AdSecMenulinks menu){
        DefaultSubMenu sub = new DefaultSubMenu(menu.getGbMenuName());
        sub.setId(menu.getGbMenuId());
        sub.setIcon(menu.getGbIcon());
        return sub;
    }
}