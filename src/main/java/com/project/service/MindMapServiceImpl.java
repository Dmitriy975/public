package com.project.service;

import com.project.dao.MindMapDaoImpl;
import com.project.model.MindMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для работы с MindMap
 * @author dmitriy
 */
@Service
@Transactional(readOnly = true)
public class MindMapServiceImpl implements MindMapService {

    @Autowired
    private MindMapDaoImpl mindMapDao;

    /**
     * Добавить объект
     */
    @Override
    @Transactional(readOnly = false)
    public void addMindMap(MindMap mindMap) {
        mindMapDao.addMindMap(mindMap);
    }

    /**
     * Получить список задач
     */
    @Override
    public List<MindMap> getListMindMap() {
        return mindMapDao.getListMindMap();
    }

    /**
     * Изменить объект
     */
    @Override
    @Transactional(readOnly = false)
    public void updateMindMap(MindMap mindMap) {
        mindMapDao.updateMindMap(mindMap);
    }

}
