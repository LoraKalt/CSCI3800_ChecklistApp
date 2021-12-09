package edu.ucdenver.kalthoff.checklistapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChecklistDao {
    @Query("SELECT * FROM checklist")
    List<Checklist> getAll();

    @Insert
    void insertAll(Checklist...checklists);

    @Insert
    void insertTask(Checklist checklist);

    @Update
    void updateTask(Checklist checklist);

    @Delete
    void deleteTask(Checklist checklist);

}
