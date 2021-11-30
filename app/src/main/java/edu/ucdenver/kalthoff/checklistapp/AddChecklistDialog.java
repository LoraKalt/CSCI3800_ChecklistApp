package edu.ucdenver.kalthoff.checklistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Calendar;

import edu.ucdenver.kalthoff.checklistapp.databinding.DialogAddChecklistBinding;
import edu.ucdenver.kalthoff.checklistapp.databinding.DialogViewChecklistBinding;

public class AddChecklistDialog extends DialogFragment {
    private DialogAddChecklistBinding binding;
    private Checklist check;

    public AddChecklistDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        binding = DialogAddChecklistBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());


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
                Log.i("info", "Save button clicked.");
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

                Checklist item = new Checklist(task, false, date, priority);

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.addNewItem(item);
                dismiss();

            }
        });


        return builder.create();

    }

    public void sendSelectedItem(Checklist check) {
        this.check = check;
    }


}