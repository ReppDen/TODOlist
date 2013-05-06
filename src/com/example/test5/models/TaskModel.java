package com.example.test5.models;

import java.io.Serializable;

/**
 * модель задачи, хранимая в списка задач
 *
 * @author Drepp
 * @since: 24.03.13
 */
public class TaskModel implements Serializable{

    Long id;
    String name;

    public TaskModel() {
    }

    public TaskModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
