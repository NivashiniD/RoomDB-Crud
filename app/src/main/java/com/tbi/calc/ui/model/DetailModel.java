package com.tbi.calc.ui.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "detail_table")
public class DetailModel {

    @PrimaryKey(autoGenerate = true)

    int id;

    public DetailModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String Name;
    String Gender;
    String City;

    public DetailModel(String name, String gender, String city) {
        Name = name;
        Gender = gender;
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

}
