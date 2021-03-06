package com.project.beans;

import com.project.model.MindMap;
import com.project.service.MindMapService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Бин диаграммы дел
 * @author dmitriy
 */

@ManagedBean
@ViewScoped
public class MindMapBean implements Serializable {

    @ManagedProperty(value="#{mindMapServiceImpl}")
    private MindMapService mindMapService;

    /** Список меню для отображения */
    private List<MindMap> mindMap;

    private MindmapNode root;

    private MindMap selectedMindMap;

    @PostConstruct
    public void init() {
        this.mindMap = this.mindMapService.getListMindMap();
        MindMap mindMapRoot = new MindMap();
        root = new DefaultMindmapNode("Задачи", mindMapRoot, "999999", false);

        for (MindMap subMindMap : this.mindMap) {
            buildMindMapTreeNodesRecursive(subMindMap, root);
        }
    }

    private void buildMindMapTreeNodesRecursive(MindMap subNode, MindmapNode parent) {
        DefaultMindmapNode newNode = new DefaultMindmapNode(subNode.getName(), subNode, subNode.getStatus().getColor(), true);
        for (MindMap node : subNode.getChilds()){
            buildMindMapTreeNodesRecursive(node, newNode);
        }
        parent.addNode(newNode);
    }

    public void onNodeSelect(SelectEvent event) {
        selectedMindMap = (MindMap) ((MindmapNode) event.getObject()).getData();
    }

    public MindMapService getMindMapService() {
        return mindMapService;
    }

    public void setMindMapService(MindMapService mindMapService) {
        this.mindMapService = mindMapService;
    }

    public List<MindMap> getMindMap() {
        return mindMap;
    }

    public void setMindMap(List<MindMap> mindMap) {
        this.mindMap = mindMap;
    }

    public MindmapNode getRoot() {
        return root;
    }

    public void setRoot(MindmapNode root) {
        this.root = root;
    }

    public MindMap getSelectedMindMap() {
        return selectedMindMap;
    }

    public void setSelectedMindMap(MindMap selectedMindMap) {
        this.selectedMindMap = selectedMindMap;
    }

}
