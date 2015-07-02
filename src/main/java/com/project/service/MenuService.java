package com.project.service;

import com.project.model.Menu;

import java.util.List;

public interface MenuService {

    public void addMenu(Menu menu);
    public List<Menu> getListMenus();
    public void updateMenu(Menu menu);
    public void changeOrderAndParent(Menu menuParent, Menu menu, int indexOrder);

}
