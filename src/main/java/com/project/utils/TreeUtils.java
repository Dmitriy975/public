package com.project.utils;

import com.project.model.TreeEntity;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Классик для упрощения построения/работы с деревьями PrimeFaces
 * Created by dmitriy on 25.06.15.
 */
public class TreeUtils {

    /**
     * Создаём рекурсивно дерево
     * @param subNodes Объект меню, который помещаем в родительскую ветку parent
     * @param parent Родительская ветка в которую помещаем subMenu
     */
    public static<T> void buildTreeNodesRecursive(TreeEntity<T> subNodes, TreeNode parent) {
        TreeNode newNode = new DefaultTreeNode(subNodes, parent);
        for (T node : subNodes.getChilds()){
            buildTreeNodesRecursive((TreeEntity)node, newNode);
        }
    }

}
