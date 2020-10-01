package com.stejavu.konkanrailwayapp.com.konkanrail.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.stejavu.konkanrailwayapp.com.konkanrail.entities.Train;

import java.util.List;

@Dao
public interface TrainDao {

    @Query("SELECT * FROM Train WHERE type = :type")
    List<Train> getAlltrain(String type);

    @Insert
    void insertAll(Train... trains);

    @Delete
    void delete(Train user);

    @Query("DELETE FROM Train")
    void deleteAll();
}
