package edu.ucdenver.kalthoff.checklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ucdenver.kalthoff.checklistapp.databinding.DialogViewChecklistBinding;

public class ViewChecklistDialog extends DialogFragment {
    private DialogViewChecklistBinding binding;
    private Checklist checklist;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogViewChecklistBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        binding.exitViewBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                dismiss();
            }
        });

        //TODO: Setup Image
        binding.taskCheckboxView.setText(checklist.getTodoItem());
        binding.taskCheckboxView.setChecked(checklist.getTaskStatus());
        binding.dueDateTextView.setText(String.format("%s", checklist.getDueDate()));

        return builder.create();

    }

    public void sendSelectedChecklist(Checklist checklist){
        this.checklist = checklist;
    }
}