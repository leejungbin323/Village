package com.example.village.rdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDao {


    @Insert
    public void insert(Users users);

    @Update
    public void update(Users users);

    @Delete
    public void delete(Users users);

    @Query("SELECT * FROM Users ")
    List<Users> getAll();

}
