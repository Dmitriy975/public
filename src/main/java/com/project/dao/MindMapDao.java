package com.project.dao;

import com.project.model.MindMap;

import java.util.List;

public interface MindMapDao {

    public void addMindMap(MindMap mindMap);
    public void updateMindMap(MindMap mindMap);
    public List<MindMap> getListMindMap();

}
