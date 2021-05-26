package com.example.taskmanagement.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {

    @Insert(onConflict = REPLACE)
    void insertTask(Task... mainData);

    @Delete
    void delete(Task task);

    @Delete
    void reset(List<Task> mainData);

    @Query("SELECT * FROM Task")
    List<Task> getAllTasks();

}
