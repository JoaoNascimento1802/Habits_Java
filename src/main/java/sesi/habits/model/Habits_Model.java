package sesi.habits.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import java.time.LocalDate;


@Entity
public class Habits_Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "description")
    private LocalDate Date;

    public Habits_Model() {}

    public Habits_Model(String title, String description, LocalDate Date) {}

    public long getId() {
        return id;
    }

    public void SetId(long id) {
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void SetTitle(String title){
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }
}
