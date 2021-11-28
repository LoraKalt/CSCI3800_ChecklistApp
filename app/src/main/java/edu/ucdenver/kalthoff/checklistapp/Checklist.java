package edu.ucdenver.kalthoff.checklistapp;

import java.util.Calendar;
import java.util.Date;

public class Checklist {
    private String todoItem;
    private Calendar dueDate;
    private int priority; //1-urgent, 2-moderate, 3-not urgent
    private boolean taskStatus; //0=false, 1=true
    //Note: creation date will be based on incremental id in database

    public Checklist(String todoItem, boolean taskStatus, Calendar dueDate, int priority){
       this.todoItem = todoItem;
       this.dueDate = dueDate;
       this.priority = priority;
       this.taskStatus = taskStatus;
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

    private void setTaskStatus(boolean taskStatus){
        this.taskStatus = taskStatus;
    }
}
