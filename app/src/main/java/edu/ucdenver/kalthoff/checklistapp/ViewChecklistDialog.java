package edu.ucdenver.kalthoff.checklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import edu.ucdenver.kalthoff.checklistapp.databinding.DialogViewChecklistBinding;

public class ViewChecklistDialog extends DialogFragment {
    private DialogViewChecklistBinding binding;
    private Checklist checkItem;
    private int index;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        binding = DialogViewChecklistBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());


        binding.taskCheckboxView.setText(checkItem.getTodoItem());
        binding.taskCheckboxView.setChecked(checkItem.getTaskStatus());

        if(checkItem.getDueDate() != null){
            binding.dueDateTextView.setText(String.format("%s", checkItem.getDueDate().getTime()));
        } else {
            binding.dueDateTextView.setText("None");
        }
        binding.priorityImageView.setImageResource(checkItem.getPriority());


        //Back to Main Menu
        binding.exitViewBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                dismiss();
            }
        });
        //Deletes Task
        binding.deleteBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //TODO: Confirm Delete Message
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.deleteItem(index);
                dismiss();
            }
        });

        //Edits Task
        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddChecklistDialog addChecklistDialog = new AddChecklistDialog();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                addChecklistDialog.show(fm, "");
                addChecklistDialog.sendSelectedItem(checkItem, index);
                dismiss();

            }
        });


        return builder.create();
    }


    public void setCheckItem(Checklist checkItem, int index){
        this.checkItem = checkItem;
        this.index = index;
    }
}