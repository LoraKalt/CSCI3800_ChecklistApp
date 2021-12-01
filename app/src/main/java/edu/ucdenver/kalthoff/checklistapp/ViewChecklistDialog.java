package edu.ucdenver.kalthoff.checklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
            String weekName = checkItem.getDueDate().getDisplayName(Calendar.DAY_OF_WEEK,
                    Calendar.SHORT, Locale.US);
            String monthName = checkItem.getDueDate().getDisplayName(Calendar.MONTH,
                    Calendar.SHORT, Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("dd, yyyy", Locale.US);
            binding.dueDateTextView.setText(String.format("%s, %s. %s", weekName, monthName,
                    sdf.format(checkItem.getDueDate().getTime())));
        } else {
            binding.dueDateTextView.setText("None");
        }
        binding.priorityImageView.setImageResource(checkItem.getPriority());
        if(checkItem.getPriority() == R.drawable.priority_low_image){
            binding.priorityTextView.setText(R.string.lowPriorityString);
        } else if(checkItem.getPriority() == R.drawable.priority_moderate_image){
            binding.priorityTextView.setText(R.string.moderatePriorityString);
        } else{
            binding.priorityTextView.setText(R.string.urgentPriorityString);
        }


        //Back to Main Menu
        binding.exitViewBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                dismiss();
            }
        });
        //Deletes Task
        binding.deleteBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(getContext());
                deleteAlert.setTitle("Delete Task?");
                deleteAlert.setMessage("Are you sure you want to delete this task?");
                deleteAlert.setCancelable(false);

                deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.deleteItem(index);
                        dismiss();
                    }
                });
                deleteAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = deleteAlert.create();
                alertDialog.show();
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