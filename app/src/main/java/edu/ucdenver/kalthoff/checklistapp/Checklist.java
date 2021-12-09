package edu.ucdenver.kalthoff.checklistapp;


import java.util.Calendar;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Checklist Class using DAO database
 */
@Entity
public class Checklist {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;

    @NonNull
    @ColumnInfo(name = "task")
    private String todoItem;

    @ColumnInfo(name = "due_date")
    private Calendar dueDate;

    @ColumnInfo(name = "creation_date")
    private Calendar creationDate;

    @NonNull
    @ColumnInfo(name = "priority")
    private int priority; //1-urgent, 2-moderate, 3-not urgent

    @ColumnInfo(name = "status")
    private boolean taskStatus;

    /**
     * Constructor
     * @param todoItem String specifying the task that needs to be done
     * @param taskStatus boolean specifying the completion of task
     * @param dueDate Calendar specifying the date task needs to be completed. Can be null
     * @param priority Integer specifying the type of priority of the task
     * @param creationDate Calendar specifying the date the object was created
     */
    public Checklist(String todoItem, boolean taskStatus, Calendar dueDate, int priority, Calendar creationDate){
       this.todoItem = todoItem;
       this.dueDate = dueDate;
       this.priority = priority;
       this.taskStatus = taskStatus;
       this.creationDate = creationDate;
    }

    /**
     * Getters and Setters
     */
    public int getId(){return id;}
    public void setId(int id){this.id = id;}

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

/**
 * Sorts list by due date, implements Caomparator.
 * If a task does not have a due date, creates a calendar object of year 9000
 * to ensure it is sorted last.
 */
class dateComparator implements Comparator<Checklist>{
    public int compare(Checklist c1, Checklist c2){
        try{
            //creates 2 new Calendar objects in case comparing against a null object
            Calendar c1New = c1.getCreationDate();
            c1New.set(Calendar.YEAR, 9000);
            Calendar c2New = c2.getCreationDate();
            c2New.set(Calendar.YEAR, 9000);

            if (c1.getDueDate() != null && c2.getDueDate() != null) {
                return c1.getDueDate().compareTo(c2.getDueDate());
            } else if (c1.getDueDate() != null && c2.getDueDate() == null) {
                return c1.getDueDate().compareTo(c2New);
            } else if (c1.getDueDate() == null && c2.getDueDate() != null) {
                return c1New.compareTo(c2.getDueDate());
            } else {
                return c1New.compareTo(c2New);
            }
        } catch(NullPointerException npe){
            return -1;
        }
    }
}

/**
 * Sorts list by priority, implements Comparator class to do so.
 */
class priorityComparator implements Comparator<Checklist>{
    public int compare(Checklist c1, Checklist c2){
        return Integer.compare(c1.getPriorityNumber(), c2.getPriorityNumber());
    }
}

/**
 * Sorts list by oldest to newest by using their auto incremented id
 */
class creationComparator implements Comparator<Checklist>{
    public int compare(Checklist c1, Checklist c2){
        return Integer.compare(c1.getId(), c2.getId());
    }
}



