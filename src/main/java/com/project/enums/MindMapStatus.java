package com.project.enums;

/**
 * Статусы задач
 * @author dmitriy
 */
public enum MindMapStatus {

    NEW   (0, "Новое", "aa3333"),
    IN_WORK (1, "Делается", "D2D713"),
    COMPLETE   (2, "Выполнено", "33aa33");

    private final int id;
    private final String name;
    private final String color;

    MindMapStatus(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}