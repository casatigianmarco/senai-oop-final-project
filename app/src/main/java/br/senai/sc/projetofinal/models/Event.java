package br.senai.sc.projetofinal.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event implements Serializable {
    private int id;
    private String name;
    private LocalDate date;
    private String place;

    public Event(int id, String name, LocalDate date, String place) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.place = place;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " \n( " + place + ", " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " )" ;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
