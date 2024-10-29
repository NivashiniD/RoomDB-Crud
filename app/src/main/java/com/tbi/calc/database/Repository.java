package com.tbi.calc.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tbi.calc.ui.model.DetailModel;

import java.util.List;

public class Repository {
    private Dao dao;
    private LiveData<List<DetailModel>> allDetail;

    // creating a constructor for our variables
    // and passing the variables to it.
    public Repository(Application application) {
        myDatabase database = myDatabase.getInstance(application);
        dao = database.Dao();
        allDetail = dao.getAllDetail();
    }

    // creating a method to insert the data to our database.
    public void insert(DetailModel model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(DetailModel model) {
        new UpdateDetailAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(DetailModel model) {
        new DeleteDetailAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllDetail() {
        new DeleteAllDetailAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<DetailModel>> getAllDetail() {
        return allDetail;
    }

    // we are creating a async task method to insert new course.
    private static class InsertCourseAsyncTask extends AsyncTask<DetailModel, Void, Void> {
        private Dao dao;

        private InsertCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DetailModel... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateDetailAsyncTask extends AsyncTask<DetailModel, Void, Void> {
        private Dao dao;

        private UpdateDetailAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DetailModel... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteDetailAsyncTask extends AsyncTask<DetailModel, Void, Void> {
        private Dao dao;

        private DeleteDetailAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DetailModel... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllDetailAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private DeleteAllDetailAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllDetail();
            return null;
        }
    }
}
