package com.project.beans;

import com.project.enums.MindMapStatus;
import com.project.model.MindMap;
import com.project.model.TreeEntity;
import com.project.service.MindMapService;
import com.project.utils.ContextUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.mindmap.MindmapNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO переименовать переменные по человечески, добавить комментарии, почистить код
 * TODO не работает редактирование элемента с существующим родителем, думает что родитель null
 * TODO не работает обновление mindMap при добавлении задачи
 */
@ManagedBean
@ViewScoped
public class DialogMindMapBean implements Serializable {

    @ManagedProperty(value="#{mindMapServiceImpl}")
    private MindMapService mindMapService;

    private List<SelectItem> statuses;

    private MindMap selectedMindMap;

    private Integer parentId;

    private TreeNode mindMapTree;
    /** Выбранный элемент меню */
    private TreeNode selectedNode;

    @PostConstruct
    public void init() {
        selectedMindMap = new MindMap();
        selectedMindMap.setStatus(MindMapStatus.NEW);
        statuses = new ArrayList<SelectItem>();
        for (MindMapStatus status : MindMapStatus.values()) {
            statuses.add(new SelectItem(status, status.getName()));
        }

        MindMapBean mindMapBean = ContextUtils.findBean("mindMapBean");
        MindMap menu = new MindMap();
        menu.setName("Задачи");
        menu.setChilds(mindMapBean.getMindMap());
        mindMapTree = new DefaultTreeNode(menu, null);
        buildTreeNodesRecursive(menu, mindMapTree, mindMapBean.getSelectedMindMap());
    }

    public void buildTreeNodesRecursive(TreeEntity<MindMap> subNodes, TreeNode parent, MindMap selectedItem) {
        TreeNode newNode = new DefaultTreeNode(subNodes, parent);
        if (subNodes == selectedItem) {
            newNode.setSelected(true);
            selectedNode = newNode;
        }
        for (MindMap node : subNodes.getChilds()){
            buildTreeNodesRecursive(node, newNode, selectedItem);
        }
    }

    public void addMindMapToCurrent() {
        init();
    }

    public void saveMindMap() {
        MindMap parent = selectedNode == null ? null : (MindMap)selectedNode.getData();
        selectedMindMap.setParent(parent);
        if (selectedMindMap.getId() != null) {
            mindMapService.updateMindMap(selectedMindMap);
        } else {
            mindMapService.addMindMap(selectedMindMap);
        }
    }

    public void onNodeDblselect(SelectEvent event) {
        this.selectedMindMap = (MindMap)((MindmapNode) event.getObject()).getData();
    }

    public List<SelectItem> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<SelectItem> statuses) {
        this.statuses = statuses;
    }

    public MindMap getSelectedMindMap() {
        return selectedMindMap;
    }

    public void setSelectedMindMap(MindMap selectedMindMap) {
        this.selectedMindMap = selectedMindMap;
    }

    public void setMindMapService(MindMapService mindMapService) {
        this.mindMapService = mindMapService;
    }

    public MindMapService getMindMapService() {
        return mindMapService;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public TreeNode getMindMapTree() {
        return mindMapTree;
    }

    public void setMindMapTree(TreeNode mindMapTree) {
        this.mindMapTree = mindMapTree;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

}
