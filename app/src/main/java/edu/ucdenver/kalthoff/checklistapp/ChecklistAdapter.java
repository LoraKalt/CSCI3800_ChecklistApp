package edu.ucdenver.kalthoff.checklistapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.Viewholder> {
    private MainActivity mainActivity;
    private ArrayList<Checklist> todoList;

    public ChecklistAdapter(MainActivity mainActivity, ArrayList<Checklist> todoList){
        this.mainActivity = mainActivity;
        this.todoList = todoList;
    }


    @NonNull
    @Override
    public ChecklistAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistAdapter.Viewholder holder, int position) {
        Checklist checklist = todoList.get(position);
        holder.checkbox.setText(checklist.getTodoItem());
        if (checklist.getDueDate() != null){
            Calendar date = checklist.getDueDate();
            holder.date.setText(String.format("Due: %s", date.getTime()));
        } else {
            holder.date.setText("");
        }
        //TODO: Set Images
        //TODO: Set check boolean
    }

    @Override
    public int getItemCount() {
        return todoList.size();
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
            //mainActivity.showCheckItem(getAdapterPosition());
            Log.i("info", "Item clicked on: " + getAdapterPosition());
        }
    }
}
