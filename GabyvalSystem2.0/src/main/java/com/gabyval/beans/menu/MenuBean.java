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

import com.gabyval.controller.security.SecurityEntity;
import com.gabyval.controller.security.SecurityMan;
import com.gabyval.beans.system.security.SessionController;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 * This class controlled the principal menu bar of application.
 * @author GAOQ
 * @version 1.0
 * @since 13/11/2017
 */
@ManagedBean(name = "menuBean")
@SessionScoped
public class MenuBean implements Serializable{
    
    private static final GB_Logger LOG = GB_Logger.getLogger(MenuBean.class); //Log for this class.
    private String pageView; //Actual Page view.
    private MenuModel menu; // the menu model.
    private String username; //Username logged.
    private final String command; //This represent action in click over any menu item.
    private final String target; // Page view to navigation.
    
    /**
     * Creates a new instance of LoginBean
     */
    public MenuBean() {
        LOG.debug("Start with menu creation.");
        command = "#{menuBean.setPageView('$')}";
        target = "deployFunction";
        pageView = "core/Welcome.xhtml";
        menu = new DynamicMenuModel();
        menu.generateUniqueIds();
        LOG.debug("All properties was charged.");
        try{
            LOG.debug("Getting the user name.");
            username = SessionController.getInstance().getUser((HttpSession) FacesContext.
                             getCurrentInstance().getExternalContext().getSession(false));
            LOG.debug("The user name was obtain is: "+username);
            LOG.debug("Creating the security tree");
            createMenuModel(username);
            LOG.debug("Menu model is complete.");
        }catch(GB_Exception ex){
            LOG.error(ex);
        }
    }    

    /**
     * Return the actual page view.
     * @return String the page view.
     */
    public String getPageView() {
        return pageView;
    }

    /**
     * Modify the actual page view.
     * @param pageView String the new page view.
     */
    public void setPageView(String pageView) {
        this.pageView = pageView;
    }

    /**
     * Return the menu model.
     * @return MenuModel the menu model.
     */
    public MenuModel getMenu() {
        return menu;
    }

    /**
     * Modify the menu model.
     * @param menu MenuModel the new menu model.
     */
    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }

    /**
     * Create a menu model from security profile of user.
     * @param username String the user.
     */
    private void createMenuModel(String username) {
        for(SecurityEntity entity :SecurityMan.getSecurityTree(username)){
            LOG.debug("GABYVAL is rendering the menu view...");
            boolean rendered = false;
            DefaultSubMenu submenu = new DefaultSubMenu(entity.getMenu().getGbMenuName());
            for(SecurityEntity subentity : entity.getAllSubEntities()){
                if(subentity.getMenu().getGbMenuStatus() == 1){
                    LOG.debug("GABYVAL load security tree for submenu...");
                    rendered = true;
                    DefaultMenuItem item = new DefaultMenuItem(subentity.getMenu().getGbMenuName());
                    LOG.debug("GABYVAL put the menu: "+subentity.getMenu().getGbMenuName());
                    item.setCommand(command.replace("$", subentity.getMenu().getGbPageView()));
                    LOG.debug("GABYVAL put the command: "+command.replace("$", subentity.getMenu().getGbPageView())+" for the last menu item.");
                    item.setUpdate(target);
                    LOG.debug("GABYVAL change the target for menu item: "+target);
                    submenu.addElement(item);
                    LOG.debug("GABYVAL finished whit the menu item insertion.");
                }
            }
            if(rendered){
                menu.addElement(submenu);
                LOG.debug("GABYVAL added a submenu: "+submenu.getLabel());
            }
        }
    }
}
