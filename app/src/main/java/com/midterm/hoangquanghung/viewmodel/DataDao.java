package com.midterm.hoangquanghung.viewmodel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.midterm.hoangquanghung.model.Data;

import java.util.List;

@Dao
public interface DataDao {
    @Query("select * from data")
    List<Data> getAllData();

    @Query("select * from data where title like :s order by title")
    List<Data> findByName(String s);

    @Insert
    void insertAll(Data... data);

    @Delete
    void delete(Data data);
}
