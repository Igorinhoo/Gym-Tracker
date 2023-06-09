package com.application.gymtracker.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.io.Serializable;

 @Entity(tableName = "pulltb")
public class Pull implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "exe_name")
    private String exe_name = "";

    @ColumnInfo(name = "reps_numb")
    private String reps_numb = "";

    @ColumnInfo(name = "weight")
    private String weight_pull = "";

    @ColumnInfo(name = "date")
    private String date = "";

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getExe_name() {
        return exe_name;
    }

    public void setExe_name(String exe_name) {
        this.exe_name = exe_name;
    }

    public String getReps_numb() {
        return reps_numb;
    }

    public void setReps_numb(String reps_numb) {
        this.reps_numb = reps_numb;
    }

    public String getWeight_pull() {
        return weight_pull;
    }

    public void setWeight_pull(String weight) {
        this.weight_pull = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}