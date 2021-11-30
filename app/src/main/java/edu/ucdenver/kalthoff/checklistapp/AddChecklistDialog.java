package edu.ucdenver.kalthoff.checklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Calendar;

import edu.ucdenver.kalthoff.checklistapp.databinding.DialogAddChecklistBinding;

public class AddChecklistDialog extends DialogFragment {
    private DialogAddChecklistBinding binding;
    private Checklist checkItem;
    private boolean isEdit;
    private int index;

    public AddChecklistDialog() {
        this.isEdit = false;
        this.index = 0;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        binding = DialogAddChecklistBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        if (checkItem != null){
            Log.i("info", "Inside editCheckItem");
            binding.editTextTask.setText(checkItem.getTodoItem());
            if (checkItem.getDueDate() != null){
                binding.editTextDate.setText(checkItem.getTodoItem());
            }
            if (checkItem.getPriority() == R.drawable.priority_high_image){
                binding.urgentRadioBtn.setChecked(true);
            } else if (checkItem.getPriority() == R.drawable.priority_moderate_image){
                binding.moderateRadioBtn.setChecked(true);
            } else {
                binding.lowRadioBtn.setChecked(true);
            }
            this.isEdit = true;
        }


        binding.exitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });
        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("info", "Clear button clicked.");
                binding.editTextTask.setText("");
                //TODO: Change date picker on a later date
                binding.editTextDate.setText("");

                binding.lowRadioBtn.setChecked(true);
                binding.editTextTask.requestFocus();
            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = binding.editTextTask.getText().toString();
                //TODO: Get Date
                Calendar date = null;

                int priority = 0;
                if (binding.lowRadioBtn.isChecked()) {
                    priority = R.drawable.priority_low_image;
                } else if (binding.moderateRadioBtn.isChecked()) {
                    priority = R.drawable.priority_moderate_image;
                } else {
                    priority = R.drawable.priority_high_image;
                }
                //TODO: Try/catch with date and make sure task and priority are specified

                //TODO: specify if we're adding or editing
                MainActivity mainActivity = (MainActivity) getActivity();
                Checklist item = new Checklist(task, false, date, priority);
                if(isEdit){
                    mainActivity.editItem(item, index);
                }
                else{
                    mainActivity.addNewItem(item);
                }

                //Clears it for the next time we edit
                isEdit = false;
                checkItem = null;
                dismiss();

            }
        });

        return builder.create();

    }

    public void sendSelectedItem(Checklist check, int index) {
        this.checkItem = check;
        this.index = index;
    }


}