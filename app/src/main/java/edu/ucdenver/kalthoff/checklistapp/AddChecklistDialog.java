package edu.ucdenver.kalthoff.checklistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Calendar;

import edu.ucdenver.kalthoff.checklistapp.databinding.DialogAddChecklistBinding;

public class AddChecklistDialog extends DialogFragment {
    private DialogAddChecklistBinding binding;

    public AddChecklistDialog(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        binding = DialogAddChecklistBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //Sets up Menu Toolbar
        binding.toolbarAdd.inflateMenu(R.menu.menu_add);
        binding.toolbarAdd.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()){
                case R.id.action_exit:
                    dismiss();
                    return true;
            }
            return true;
        });

        binding.exitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });

        binding.clearBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                binding.editTextTask.setText("");
                //TODO: Change date picker on a later date
                binding.editTextDate.setText("");
                binding.editTextTime.setText("");

                binding.lowRadioBtn.setChecked(true);
                binding.editTextTask.requestFocus();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = binding.editTextTask.getText().toString();
                //TODO: Get Date and Time
                Calendar date = null;

                int priority = 0;
                if(binding.lowRadioBtn.isChecked()){
                    priority = R.drawable.priority_low_image;
                }
                else if (binding.moderateRadioBtn.isChecked()){
                    priority = R.drawable.priority_moderate_image;
                }
                else {
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






}