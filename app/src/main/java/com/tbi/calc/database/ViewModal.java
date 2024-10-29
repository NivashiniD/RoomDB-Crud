package com.tbi.calc.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tbi.calc.ui.model.DetailModel;

import java.util.List;

public class ViewModal extends AndroidViewModel {
    private Repository repository;

    private LiveData<List<DetailModel>> allDetail;

    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allDetail = repository.getAllDetail();
    }
    public void insert(DetailModel model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(DetailModel model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(DetailModel model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllDetail() {
        repository.deleteAllDetail();
    }

    // below method is to get all the courses in our list.
    public LiveData<List<DetailModel>> getAllDetail() {
        return allDetail;
    }
}
