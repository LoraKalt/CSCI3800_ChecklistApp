package edu.ucdenver.kalthoff.checklistapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Checklist Adaptor. Handles the visualization of a single task in Recycler View
 */
public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.Viewholder> {
    private MainActivity mainActivity;
    private ArrayList<Checklist> checklist;

    public ChecklistAdapter(MainActivity mainActivity, ArrayList<Checklist> checklist){
        this.mainActivity = mainActivity;
        this.checklist = checklist;
    }


    @NonNull
    @Override
    public ChecklistAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    /**
     * Display for each task
     * @param holder the task that is to be displayed
     * @param position the index of the task in the recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull ChecklistAdapter.Viewholder holder, int position) {
        Checklist checkItem = this.checklist.get(position);
        holder.checkbox.setText(checkItem.getTodoItem());
        holder.checkbox.setChecked(checkItem.getTaskStatus());
        //If due date is not null, display the date in the calendar
        if (checkItem.getDueDate() != null){
            Calendar date = checkItem.getDueDate();
            String weekName = date.getDisplayName(Calendar.DAY_OF_WEEK,
                    Calendar.SHORT, Locale.US);
            String monthName = date.getDisplayName(Calendar.MONTH,
                    Calendar.SHORT, Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("dd, yyyy", Locale.US);
            holder.date.setText(String.format("%s, %s. %s", weekName, monthName,
                    sdf.format(date.getTime())));
        } else {
            holder.date.setText("");
        }

        holder.priorityImage.setImageResource(checkItem.getPriority());

        Log.i("info", "Checked bool on " + holder.checkbox.getText() + " : " + checkItem.getTaskStatus());
        holder.checkbox.setOnCheckedChangeListener(null);
        holder.checkbox.setChecked(checkItem.getTaskStatus());
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                checkItem.setTaskStatus(checked);
            }
        });





    }

    @Override
    public int getItemCount() {
        try{
            return checklist.size();
        } catch (NullPointerException npe){
            Log.i("info", "NullPointerException");
            return 0;
        }

    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView priorityImage, boxImage;
        private TextView date;
        private CheckBox checkbox;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            priorityImage = itemView.findViewById(R.id.priorityImage);
            boxImage = itemView.findViewById(R.id.boxImage);
            date = itemView.findViewById(R.id.dateTextbox);
            checkbox = itemView.findViewById(R.id.todoCheckbox);


            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mainActivity.showCheckItem(getAdapterPosition());
            Log.i("info", "Item clicked on: " + getAdapterPosition());
        }
    }
}
