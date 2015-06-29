package com.project.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu implements TreeEntity<Menu>, Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade= {CascadeType.REFRESH}, fetch= FetchType.LAZY)
    @JoinColumn(name="menu_id", nullable = true)
    private Menu parent;

    @Column(name = "order_index")
    private int order;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="parent")
    @OrderBy("order")
    @Fetch(FetchMode.SUBSELECT)
    private List<Menu> childs = new ArrayList<Menu>();

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

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public List<Menu> getChilds() {
        return childs;
    }

    public void setChilds(List<Menu> childMenus) {
        this.childs = childMenus;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
