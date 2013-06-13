package com.example.test5.models;

import java.io.Serializable;
import java.util.Date;

/**
 * модель задачи, хранимая в списка задач
 *
 * @author Drepp
 * @since: 24.03.13
 */
public class TaskModel implements Serializable {

    Long id;
    String text;
    Date date;
    Integer raiting;
    Boolean completed;

    public TaskModel(Long id, String text) {
        this.id = id;
        this.text = text;
        this.date = new Date();
        this.raiting = 0;
        this.completed = false;
    }

    public TaskModel(Long id, String text, Integer raiting) {
        this.id = id;
        this.text = text;
        this.raiting = raiting;
    }

    public TaskModel(Long id, String text, Date date, Integer raiting) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.raiting = raiting;
        this.completed = false;
    }

    public Long getId() {
        return id;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRaiting() {
        return raiting;
    }

    public void setRaiting(Integer raiting) {
        this.raiting = raiting;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", raiting=" + raiting +
                ", completed=" + completed +
                '}';
    }


}
