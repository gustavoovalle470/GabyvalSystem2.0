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

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

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
    private MenuModel menu;
    
    public MenuBean(){
        pageView = "/core/welcome.xhtml";
        chargeMenuOptions();
    }
    
    public void goHome(){
        System.out.println("Ir a pagina bienvenida...");
    }
    
    public void setActualView(String pageView){
        this.pageView = pageView;
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

    private void chargeMenuOptions() {
        menu = new DefaultMenuModel();
        DefaultSubMenu sub1 = new DefaultSubMenu("PRUEBA");
        sub1.setStyle("height: 100px; border: 0px;background: transparent;");
        DefaultMenuItem item1 = new DefaultMenuItem("Prueba 1");
        item1.setCommand("#{MenuBean.setView('1')}");
        item1.setUpdate("@form");
        DefaultMenuItem item2 = new DefaultMenuItem("Prueba 2");
        item2.setCommand("#{MenuBean.setView('2')}");
        item2.setUpdate("@form");
        sub1.addElement(item1);
        sub1.addElement(item2);
        menu.addElement(sub1);
    }

    public MenuModel getMenu() {
        return menu;
    }

    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }
}
