package com.repp.todo.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

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
    Boolean completed = false;
    String audio = "";
    String photo = "";
    String adress = "";

    private static Random random = new Random();

    public TaskModel() {
        this.id = random.nextLong();
        this.text = "";
        this.date = new Date();
        this.raiting = 0;
        this.completed = false;
    }

    public TaskModel(String text) {
        this.id = random.nextLong();
        this.text = text;
        this.date = new Date();
        this.raiting = 0;
        this.completed = false;
    }

    public TaskModel(String text, Integer raiting) {
        this.id =  random.nextLong();
        this.text = text;
        this.raiting = raiting;
    }

    public TaskModel(String text, Date date, Integer raiting) {
        this.id =  random.nextLong();
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

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "adress='" + adress + '\'' +
                ", photo='" + photo + '\'' +
                ", audio='" + audio + '\'' +
                ", completed=" + completed +
                ", raiting=" + raiting +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", id=" + id +
                '}';
    }
}
