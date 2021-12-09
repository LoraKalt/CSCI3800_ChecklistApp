package edu.ucdenver.kalthoff.checklistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.ucdenver.kalthoff.checklistapp.databinding.DialogAddChecklistBinding;

public class AddChecklistDialog extends DialogFragment {
    private DialogAddChecklistBinding binding;
    private Checklist checkItem;
    private boolean isEdit;
    private int index;
    private int year;
    private int month;
    private int day;
    private Calendar pickedDate;

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

        Calendar currentDate = Calendar.getInstance();

        if (checkItem != null) {
            binding.editTextTask.setText(checkItem.getTodoItem());
            if (checkItem.getDueDate() != null) {
                String weekName = checkItem.getDueDate().getDisplayName(Calendar.DAY_OF_WEEK,
                        Calendar.SHORT, Locale.US);
                String monthName = checkItem.getDueDate().getDisplayName(Calendar.MONTH,
                        Calendar.SHORT, Locale.US);
                SimpleDateFormat sdf = new SimpleDateFormat("dd, yyyy", Locale.US);
                binding.editTextDate.setText(String.format("%s, %s. %s", weekName, monthName,
                        sdf.format(checkItem.getDueDate().getTime())));
                pickedDate = checkItem.getDueDate();

            }
            if (checkItem.getPriority() == R.drawable.priority_high_image) {
                binding.urgentRadioBtn.setChecked(true);
            } else if (checkItem.getPriority() == R.drawable.priority_moderate_image) {
                binding.moderateRadioBtn.setChecked(true);
            } else {
                binding.lowRadioBtn.setChecked(true);
            }
            this.isEdit = true;
        } else {
            pickedDate = null;
        }


        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit && pickedDate != null) {
                    year = pickedDate.get(Calendar.YEAR);
                    month = pickedDate.get(Calendar.MONTH);
                    day = pickedDate.get(Calendar.DAY_OF_MONTH);
                } else {
                    Calendar currentDate = Calendar.getInstance();
                    year = currentDate.get(Calendar.YEAR);
                    month = currentDate.get(Calendar.MONTH);
                    day = currentDate.get(Calendar.DAY_OF_MONTH);
                }

                //Allows user to select a date from date picker widget
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int
                            selectedMonth, int selectedDay) {

                        Calendar date = Calendar.getInstance();
                        ;

                        date.set(Calendar.YEAR, selectedYear);
                        date.set(Calendar.MONTH, selectedMonth);
                        date.set(Calendar.DAY_OF_MONTH, selectedDay);
                        String weekName = date.getDisplayName(Calendar.DAY_OF_WEEK,
                                Calendar.SHORT, Locale.US);
                        String monthName = date.getDisplayName(Calendar.MONTH,
                                Calendar.SHORT, Locale.US);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd, yyyy", Locale.US);
                        binding.editTextDate.setText(String.format("%s, %s. %s", weekName, monthName,
                                sdf.format(date.getTime())));

                        pickedDate = date;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //Returns to Main menu
        binding.exitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });

        //Clears fields
        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                binding.editTextTask.setText("");
                binding.editTextDate.setText("");
                binding.lowRadioBtn.setChecked(true);
                binding.editTextTask.requestFocus();
            }
        });

        //Saving the task
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //picked date turns null if string is empty
                String task = binding.editTextTask.getText().toString();
                if (binding.editTextDate.getText().toString().isEmpty()) {
                    pickedDate = null;
                }
                //Assigns priority variable to image ID
                int priority = 0;
                if (binding.lowRadioBtn.isChecked()) {
                    priority = R.drawable.priority_low_image;
                } else if (binding.moderateRadioBtn.isChecked()) {
                    priority = R.drawable.priority_moderate_image;
                } else if (binding.urgentRadioBtn.isChecked()) {
                    priority = R.drawable.priority_high_image;
                } else {
                    priority = 0;
                }
                /* Tests to see if picked date and current date are indeed same day, but
                have different set times. Used for when user edits a task that has today
                set as it's due date. */
                Calendar testPicked = Calendar.getInstance();
                if(pickedDate != null){
                    testPicked.set(Calendar.YEAR, pickedDate.get(Calendar.YEAR));
                    testPicked.set(Calendar.MONTH, pickedDate.get(Calendar.MONTH));
                    testPicked.set(Calendar.DAY_OF_MONTH, pickedDate.get(Calendar.DAY_OF_MONTH));
                }


                //Makes sure that required fields are filled
                if (task.equals("") || priority == 0) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Empty fields");
                    alert.setMessage("All required fields must be filled in");
                    alert.setCancelable(false);
                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    //Makes sure that the date is valid
                } else if (pickedDate != null && pickedDate.before(currentDate) && testPicked.before(currentDate)) {
                    AlertDialog.Builder calAlert = new AlertDialog.Builder(getContext());
                    calAlert.setTitle("Due date");
                    calAlert.setMessage("Date should be set the day of or after today's date");
                    calAlert.setCancelable(false);
                    calAlert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = calAlert.create();
                    alertDialog.show();


                    //Makes sure that the string didn't exceed character limit
                } else if (task.length() >= 60) {
                    AlertDialog.Builder limitAlert = new AlertDialog.Builder(getContext());
                    limitAlert.setTitle("Exceeded Character Limit");
                    limitAlert.setMessage("Task field should be no longer than 60 characters");
                    limitAlert.setCancelable(false);
                    limitAlert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = limitAlert.create();
                    alertDialog.show();
                    //If all fields are valid, push new task into Arraylist
                } else {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    Checklist item = new Checklist(task, false, pickedDate, priority, Calendar.getInstance());
                    if (isEdit) {
                        mainActivity.editItem(item, index);
                    } else {
                        mainActivity.addNewItem(item);
                    }

                    //Clears it for the next time we edit
                    isEdit = false;
                    checkItem = null;
                    dismiss();
                }

            }//end on click
        });

        return builder.create();

    }

    public void sendSelectedItem(Checklist check, int index) {
        this.checkItem = check;
        this.index = index;
    }


}