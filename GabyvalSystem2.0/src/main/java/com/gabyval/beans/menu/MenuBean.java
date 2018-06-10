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
import com.gabyval.core.commons.controllers.PersistenceManager;
import com.gabyval.core.exception.GB_Exception;
import com.gabyval.core.logger.GB_Logger;
import com.gabyval.persistence.user.security.AdSecMenulinks;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

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
    private final String subMenuStyle = "width:100%; padding: 0 15px;border: 0;background: transparent;color: #0d6aad;font-weight: bolder;";
    private final String menuItemStyle = "width:30%;color: #0d6aad;font-size: small;text-align: rigth;";
    private List<AdSecMenulinks> menus;
    
    /**
     * Creates a new instance of LoginBean
     */
    public MenuBean() {
        System.out.println("Inicia creacion del menu...");
        menu = new DynamicMenuModel();
        LOG.debug("Start with menu creation.");
        command = "#{menuBean.setPageView('$')}";
        target = "GBDeployView";
        pageView = "pages/Welcome.xhtml";
        LOG.debug("All properties was charged.");
        /*try{
            LOG.debug("Getting the user name.");
            username = SessionController.getInstance().getUser((HttpSession) FacesContext.
                             getCurrentInstance().getExternalContext().getSession(false));
            LOG.debug("The user name was obtain is: "+username);
            LOG.debug("Creating the security tree");
            createMenuModel(username, getSubMenus(null, username));
            LOG.debug("Menu model is complete.");
            }catch(GB_Exception ex){
                LOG.error(ex);
            }*/
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

    public String change_page(String page){
        return page;
    }
    
    /**
     * Create a menu model from security profile of user.
     * @param username String the user.
     */
    private void createMenuModel(String username, List<AdSecMenulinks> submenus) throws GB_Exception {
        for(AdSecMenulinks m : submenus){
            List <AdSecMenulinks> subMenuM = getSubMenus(m.getGbMenuId(), username);
            if(!subMenuM.isEmpty()){
                createMenuModel(username, subMenuM);
            }else{
                if(m.getGbPageView() == null){
                    menu.addElement(new DefaultSubMenu(m.getGbMenuName()));
                }else{
                    menu.addElement(new DefaultMenuItem(m.getGbMenuName()));
                }
            }
        }
    }

    private List<AdSecMenulinks> getSubMenus(String parent, String username) throws GB_Exception {
        return PersistenceManager
                .getInstance()
                .runSQL("SELECT menu.* FROM AD_SEC_MENULINKS menu\n" +
                        "JOIN AD_SEC_MENU_PROFILING prof ON (menu.GB_MENU_ID = prof.GB_MENU_ID)\n" +
                        "JOIN AD_USER_PROFILING uprof ON (uprof.GB_PROFILE_NAME = prof.GB_PROFILE_NAME)\n" +
                        "WHERE uprof.GB_USERNAME = '"+username+"' \n" +
                        "AND GB_PARENT_ID = "+parent+"\n" +
                        "UNION\n" +
                        "SELECT * FROM AD_SEC_MENULINKS WHERE GB_PARENT_ID = "+parent);
    }
    
    public void goHome(){
        System.out.println("Ir a pagina bienvenida...");
    }
}
