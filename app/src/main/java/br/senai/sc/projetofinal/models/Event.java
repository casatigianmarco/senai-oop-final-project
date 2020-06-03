package br.senai.sc.projetofinal.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Serializable {
    private int id;
    private String name;
    private Date date;
    private String place;

    public Event(int id, String name, Date date, String place) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.place = place;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return name + " \n( " + place + ", " + format.format(date) + " )" ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
