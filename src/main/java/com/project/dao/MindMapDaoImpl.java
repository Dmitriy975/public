package com.project.dao;

import com.project.model.MindMap;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Дао для работы с таблицей задач
 * @author dmitriy
 */
@Repository
public class MindMapDaoImpl implements MindMapDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Добавить задачу
     * @param mindMap задача
     */
    @Override
    public void addMindMap(MindMap mindMap) {
        this.sessionFactory.getCurrentSession().save(mindMap);
    }

    /**
     * Изменить задачу
     * @param mindMap задача
     */
    @Override
    public void updateMindMap(MindMap mindMap) {
        this.sessionFactory.getCurrentSession().update(mindMap);
    }

    /**
     * Получить список задач
     * @return список задач
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<MindMap> getListMindMap() {
        return this.sessionFactory.getCurrentSession().createQuery("from MindMap where mind_map_id is null").list();
    }

}
