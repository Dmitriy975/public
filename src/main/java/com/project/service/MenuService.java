package com.project.service;

import com.project.model.Menu;

import java.util.List;

/**
 * Created by dmitriy on 04.04.15.
 */
public interface MenuService {

    public void addMenu(Menu menu);
    public List<Menu> listMenus();
    public void updateMenu(Menu menu);
    public void changeOrderAndParent(Menu menuParent, Menu menu, int indexOrder);

}
