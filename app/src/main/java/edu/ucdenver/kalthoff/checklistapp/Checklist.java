package edu.ucdenver.kalthoff.checklistapp;

import android.util.Log;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class Checklist {
    private String todoItem;
    private Calendar dueDate;
    private Calendar creationDate;
    private int priority; //1-urgent, 2-moderate, 3-not urgent
    private boolean taskStatus; //0=false, 1=true
    //Note: creation date will be based on incremental id in database

    public Checklist(String todoItem, boolean taskStatus, Calendar dueDate, int priority){
       this.todoItem = todoItem;
       this.dueDate = dueDate;
       this.priority = priority;
       this.taskStatus = taskStatus;
       this.creationDate = Calendar.getInstance();
    }

    public String getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(String todoItem) {
        this.todoItem = todoItem;
    }

    public Calendar getDueDate() { return dueDate; }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getTaskStatus(){
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus){
        this.taskStatus = taskStatus;
    }

    public Calendar getCreationDate(){ return creationDate;}

    //used in order to sort by priority for the comparator
    public int getPriorityNumber(){
        if(R.drawable.priority_low_image == this.priority){
            return 3;
        } else if (R.drawable.priority_moderate_image == this.priority){
            return 2;
        } else if(R.drawable.priority_high_image == this.priority){
            return 1;
        }
        return 4;
    }
}

class dateComparator implements Comparator<Checklist>{
    public int compare(Checklist c1, Checklist c2){
        //TODO: Try to figure out how to handle null due dates
        try{
            if (c1.getDueDate() != null && c2.getDueDate() != null) {
                return c1.getDueDate().compareTo(c2.getDueDate());
            } else if (c1.getDueDate() != null && c2.getDueDate() == null) {
                Calendar c2New = c2.getCreationDate();
                c2New.set(Calendar.YEAR, 9000);
                return c1.getDueDate().compareTo(c2New);
            } else if (c1.getDueDate() == null && c2.getDueDate() != null) {
                Calendar c1New = c1.getCreationDate();
                c1New.set(Calendar.YEAR, 9000);
                return c1New.compareTo(c2.getDueDate());
            } else {
                Calendar c1New = c1.getCreationDate();
                c1New.set(Calendar.YEAR, 9000);
                Calendar c2New = c2.getCreationDate();
                c2New.set(Calendar.YEAR, 9000);
                return c1New.compareTo(c2New);
            }
        } catch(NullPointerException npe){
            return -1;
        }


    }
}

class priorityComparator implements Comparator<Checklist>{
    public int compare(Checklist c1, Checklist c2){
        if (c1.getPriorityNumber() == c2.getPriorityNumber()){
            return 0;
        } else if (c1.getPriorityNumber() > c2.getPriorityNumber()){
            return 1;
        } else {
            return -1;
        }
    }
}

class creationComparator implements Comparator<Checklist>{
    public int compare(Checklist c1, Checklist c2){
        return c1.getCreationDate().compareTo(c2.getCreationDate());
    }
}
