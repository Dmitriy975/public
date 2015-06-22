package com.project.dao;

import com.project.model.Menu;

import java.util.List;

/**
 * Created by dmitriy on 04.04.15.
 */
public interface MenuDao {

    public void addMenu(Menu menu);
    public void updateMenu(Menu menu);
    public List<Menu> listMenus();
    public Menu getMenuById(int id);
    public void changeOrder(Integer parentId, int indexOrderBefore, Integer indexOrderAfter, int iteration);

}
