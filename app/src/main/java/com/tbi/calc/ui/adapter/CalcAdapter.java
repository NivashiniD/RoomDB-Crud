package com.tbi.calc.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbi.calc.database.ViewModal;
import com.tbi.calc.databinding.DialogCalcBinding;
import com.tbi.calc.databinding.RvCalcBinding;
import com.tbi.calc.ui.model.DetailModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalcAdapter extends RecyclerView.Adapter<CalcAdapter.MyViewHolder> {

    DialogCalcBinding dialogCalcBinding;

    List<DetailModel> modelList;

    List<String> genderNameList = new ArrayList<>(Arrays.asList("Male", "Female", "Others"));

    Context context;
    private OnItemClickListener listener;
    private  ViewModal viewModal;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<DetailModel> newList) {
        modelList.clear();
        modelList.addAll(newList);
        notifyDataSetChanged();
    }

    public CalcAdapter(List<DetailModel> modelList, Context context,ViewModal viewModal) {
        this.modelList = modelList;
        this.context = context;
        this.viewModal=viewModal;
    }


    @NonNull
    @Override
    public CalcAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvCalcBinding binding=RvCalcBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalcAdapter.MyViewHolder holder, int position) {
        DetailModel model=modelList.get(position);

        holder.binding.tvName.setText(model.getName());
        holder.binding.tvGender.setText(model.getGender());
        holder.binding.tvCity.setText(model.getCity());
        holder.binding.btnRemove.setOnClickListener(v->{
            viewModal.delete(model);
            Toast.makeText(context, "Detail deleted", Toast.LENGTH_SHORT).show();
        });

        holder.binding.btnEdit.setOnClickListener(v -> showEditDialog(model));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RvCalcBinding binding;
        public MyViewHolder(RvCalcBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DetailModel model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private void showEditDialog(DetailModel model) {
        dialogCalcBinding = DialogCalcBinding.inflate(LayoutInflater.from(context));
        View view = dialogCalcBinding.getRoot();

        dialogCalcBinding.edtName.setText(model.getName());
        dialogCalcBinding.edtCity.setText(model.getCity());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, genderNameList);
        dialogCalcBinding.spnGender.setAdapter(adapter);

        int genderPosition = genderNameList.indexOf(model.getGender());
        if (genderPosition != -1) {
            dialogCalcBinding.spnGender.setSelection(genderPosition);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setTitle("Edit Details")
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = dialogCalcBinding.edtName.getText().toString();
                    String newGender = "";
                    if (dialogCalcBinding.spnGender.getSelectedItem() != null) {
                        newGender = dialogCalcBinding.spnGender.getSelectedItem().toString();
                    }
                    String newCity = dialogCalcBinding.edtCity.getText().toString();

                    model.setName(newName);
                    model.setGender(newGender);
                    model.setCity(newCity);
                    viewModal.update(model);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Details updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
