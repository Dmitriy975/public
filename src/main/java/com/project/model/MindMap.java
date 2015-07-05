package com.project.model;

import com.project.enums.MindMapStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Объект задач
 */
@Entity
@Table(name = "mind_map")
public class MindMap implements TreeEntity<MindMap>, Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "need_to_do")
    private String needToDo;

    @Column(name = "status")
    private MindMapStatus status;

    @ManyToOne(cascade= {CascadeType.REFRESH}, fetch= FetchType.LAZY)
    @JoinColumn(name="mind_map_id", nullable = true)
    private MindMap parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="parent")
    @Fetch(FetchMode.SUBSELECT)
    private List<MindMap> childs = new ArrayList<MindMap>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MindMap getParent() {
        return parent;
    }

    public void setParent(MindMap parent) {
        this.parent = parent;
    }

    public List<MindMap> getChilds() {
        return childs;
    }

    public void setChilds(List<MindMap> childMenus) {
        this.childs = childMenus;
    }

    public String getNeedToDo() {
        return needToDo;
    }

    public void setNeedToDo(String needToDo) {
        this.needToDo = needToDo;
    }

    public MindMapStatus getStatus() {
        return status;
    }

    public void setStatus(MindMapStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MindMap{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", needToDo='" + needToDo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
