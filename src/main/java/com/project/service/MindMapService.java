package com.project.service;

import com.project.model.MindMap;

import java.util.List;

public interface MindMapService {

    public void addMindMap(MindMap mindMap);
    public List<MindMap> getListMindMap();
    public void updateMenu(MindMap mindMap);

}
