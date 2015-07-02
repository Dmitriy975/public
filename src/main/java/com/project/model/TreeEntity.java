package com.project.model;

import java.util.List;

/**
 * Интерфейс для сущностей замкнутых на себя
 * т.е. имеющих родителей и детей такого же типа
 * Требуется для построения дерева при помощи TreeUtils
 * @author dmitriy
 */
public interface TreeEntity<T> {

    public T getParent();
    public void setParent(T parent);
    public List<T> getChilds();
    public void setChilds(List<T> childs);
}
