package com.stejavu.konkanrailwayapp.com.konkanrail.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Train {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "train_name")
    public String name;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
