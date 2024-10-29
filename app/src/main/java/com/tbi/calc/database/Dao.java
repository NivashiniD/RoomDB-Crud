package com.tbi.calc.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tbi.calc.ui.model.DetailModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(DetailModel detailModel);

    @Update
    void update(DetailModel detailModel);

    @Delete
    void delete(DetailModel detailModel);

    @Query("DELETE FROM detail_table")
    void deleteAllDetail();

    @Query("SELECT * FROM detail_table ORDER BY  Name ASC")
    LiveData<List<DetailModel>> getAllDetail();
}
