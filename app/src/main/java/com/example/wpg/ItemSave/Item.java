package com.example.wpg.ItemSave;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private Long birthDate;
    private Long expirationDate;
    private Long remindDate;
    private String src;

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

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Long remindDate) {
        this.remindDate = remindDate;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}

