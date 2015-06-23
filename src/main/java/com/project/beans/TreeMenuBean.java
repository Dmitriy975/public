package com.project.beans;

import com.project.model.Menu;
import com.project.service.MenuService;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


/** Дерево меню */
@ManagedBean
@RequestScoped
public class TreeMenuBean implements Serializable {

    @ManagedProperty(value="#{MenuServiceImpl}")
    private MenuService menuService;
    /** Список меню для отображения */
    private List<Menu> menu;
    /** Корневой элемент меню */
    private TreeNode root;
    /** Выбранный элемент меню */
    private TreeNode selectedNode;

    @PostConstruct
    public void init() {
        //long startTime = System.currentTimeMillis();
        this.menu = this.menuService.listMenus();
        Menu menu = new Menu();
        menu.setName("menu");
        menu.setChildMenus(this.menu);
        root = new DefaultTreeNode(menu, null);
        for (Menu subMenu : this.menu) {
            getNodeWithChildren(subMenu, root);
        }
    }

    /**
     * Создаём рекурсивно дерево
     * @param subMenu Объект меню, который помещаем в родительскую ветку parent
     * @param parent Родительская ветка в которую помещаем subMenu
     */
    public void getNodeWithChildren(Menu subMenu, TreeNode parent){
        TreeNode newNode = new DefaultTreeNode(subMenu, parent);
        for (Menu menu : subMenu.getChildMenus()){
            getNodeWithChildren(menu, newNode);
        }
    }

    /**
     * Сортируем элементы меню перетаскивая а так же переносим их в другие родительские меню
     * @param event
     */
    public void onDragDrop(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
        menuService.changeOrderAndParent((Menu)dropNode.getData(), (Menu) dragNode.getData(), dropIndex);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropIndex);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

}