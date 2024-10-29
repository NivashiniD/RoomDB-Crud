package com.tbi.calc.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tbi.calc.R;
import com.tbi.calc.database.ViewModal;
import com.tbi.calc.databinding.ActivityMainBinding;
import com.tbi.calc.databinding.DialogCalcBinding;
import com.tbi.calc.ui.adapter.CalcAdapter;
import com.tbi.calc.ui.model.DetailModel;
import com.tbi.calc.ui.model.GenderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DialogCalcBinding dialogCalcBinding;

    CalcAdapter adapter;
    List<DetailModel> model=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    List<String> genderNameList = new ArrayList<>();
    List<String> genderIdList = new ArrayList<>();

    String genderName = "", genderId ="";

    //Database
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewmodal = new ViewModelProvider(this).get(ViewModal.class);

        onCLick();
        initRv();


    }

    private void onCLick() {
        binding.filterBtn.setOnClickListener(v -> GetDetailsDialog());
        binding.deleteAll.setOnClickListener(data -> showDeleteDialog());
    }

    private void initRv() {
        adapter = new CalcAdapter(model, MainActivity.this,viewmodal);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        binding.rvCalc.setAdapter(adapter);
        binding.rvCalc.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(model -> {
           // showEditDialog(model);
            System.out.println("######### IOPPP");
        });

        viewmodal.getAllDetail().observe(this, models -> {
            if (models != null) {
                adapter.updateList(models);
            }
        });
    }

    private void showDeleteDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setTitle("Are You Sure to Delete All ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {

                    viewmodal.deleteAllDetail();
                    Toast.makeText(MainActivity.this, "All Details are deleted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                })
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss());

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void addDetails(String name,String genderName,String city) {

        if (adapter != null) {
            DetailModel detail = new DetailModel(name, genderName, city);
            viewmodal.insert(detail);
        } else {
            Log.e("MainActivity", "Adapter is null");
        }
    }

    private void GetDetailsDialog() {
        dialogCalcBinding = DialogCalcBinding.inflate(getLayoutInflater());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(dialogCalcBinding.getRoot());

        List<GenderModel> genderList = new ArrayList<>();
        genderList.add(new GenderModel("Male", 0));
        genderList.add(new GenderModel("Female", 1));
        genderList.add(new GenderModel("Others", 2));

        genderNameList.clear();
        genderIdList.clear();

        genderList.forEach(data ->{
            genderIdList.add(String.valueOf(data.getId()));
            genderNameList.add(data.getName());
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(MainActivity.this, R.layout.simple_spinner_dropdown_item,
                genderNameList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogCalcBinding.spnGender.setAdapter(adapter1);

        dialogCalcBinding.spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView t1 = (TextView) view;
                String item = t1.getText().toString();
                int i = genderNameList.indexOf(item);

                if (i != -1) {
                    genderId = genderIdList.get(i);
                    genderName = genderNameList.get(i);

                    System.out.println("####### genderId : "+ genderId);
                    System.out.println("####### genderName : "+ genderName);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // SET DIALOG MESSAGE
        alertDialogBuilder
                .setTitle("Details")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    String name = Objects.requireNonNull(dialogCalcBinding.edtName.getText()).toString();
                    String city = Objects.requireNonNull(dialogCalcBinding.edtCity.getText()).toString();
                    addDetails(name,genderName,city);

                    dialog.dismiss();

                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_color));
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.app_color));

    }

}