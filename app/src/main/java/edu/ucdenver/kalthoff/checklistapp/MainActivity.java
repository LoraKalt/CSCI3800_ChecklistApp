package edu.ucdenver.kalthoff.checklistapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucdenver.kalthoff.checklistapp.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main view of App
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ChecklistAdapter checklistAdapter;

    private RecyclerView checklistRV;
    private ArrayList<Checklist> checklist;
    private ChecklistDatabase checklistDatabase;
    private int sortType; //0=default, 1=date, 2=priority

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        sortType = 0; //default


        //Set up Recycler View
        checklistRV = findViewById(R.id.checklistRecyclerView);

        checklist = new ArrayList<>();
        checklistDatabase = ChecklistDatabase.getInstance(getApplicationContext());

        checklistAdapter = new ChecklistAdapter(this, checklist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        checklistRV.setLayoutManager(linearLayoutManager);
        checklistRV.setAdapter(checklistAdapter);



        //floating action button
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddChecklistDialog addChecklistDialog = new AddChecklistDialog();
                addChecklistDialog.show(getSupportFragmentManager(), "");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         /*Handle action bar item clicks here. The action bar will
         automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml. */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            AddChecklistDialog addChecklistDialog = new AddChecklistDialog();
            addChecklistDialog.show(getSupportFragmentManager(), "");
            return true;
        } else if (id == R.id.action_delete_all){
            //Creates popup menu asking for user confirmation
            if(!checklist.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Tasks");
                builder.setMessage("Are you sure you want to delete all checked items?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAllChecked();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                //If the list is empty, pops up a message
                Toast.makeText(getApplicationContext(), "Checklist is empty", Toast.LENGTH_LONG).show();
            }

        }else if (id == R.id.action_default_sort) {
            //Default Sort
            if(!checklist.isEmpty()) {
                sortType = 0;
//                Collections.sort(checklist, new creationComparator());
//                checklistAdapter.notifyDataSetChanged();
                loadData();
            } else {
                Toast.makeText(getApplicationContext(), "Nothing to sort", Toast.LENGTH_LONG).show();
            }
        }
        else if (id == R.id.action_date_sort){
            //Sort By Date
            if(!checklist.isEmpty()){
                sortType = 1;
//                Collections.sort(checklist, new dateComparator());
//                checklistAdapter.notifyDataSetChanged();
                loadData();
            }else {
                Toast.makeText(getApplicationContext(), "Nothing to sort", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.action_urgent_sort){
            //Sort by Priority
            if(!checklist.isEmpty()) {
                sortType = 2;
//                Collections.sort(checklist, new priorityComparator());
//                checklistAdapter.notifyDataSetChanged();
                loadData();
            }else {
                Toast.makeText(getApplicationContext(), "Nothing to sort", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        loadData();
    }

    /**
     * Add new Task to the list
     * @param item Checklist
     */
    public void addNewItem(Checklist item){
        checklistDatabase.checklistDao().insertTask(item);
        loadData();

    }

    /**
     * Loads all data from a database
     */
    public void loadData(){
        checklist.clear();
        List<Checklist> list2 = checklistDatabase.checklistDao().getAll();
        if(list2.size() != 0){
            checklist.addAll(list2);
            if(sortType == 1){
                //sort by date
                Collections.sort(checklist, new dateComparator());
            } else if(sortType == 2){
                //sort by priority
                Collections.sort(checklist, new priorityComparator());
            } else {
                Collections.sort(checklist, new creationComparator());
            }
        }
        checklistAdapter.notifyDataSetChanged();
    }

    /**
     * Deletes Task from the list
     * @param item Checklist
     */
    public void deleteItem(Checklist item){
        checklistDatabase.checklistDao().deleteTask(item);
        loadData();
    }

    /**
     * Delete all Checked/Completed tasks from the list
     */
    public void deleteAllChecked(){
        for(int i = 0; i< checklist.size(); i++){
            if(checklist.get(i).getTaskStatus()){
                checklistDatabase.checklistDao().deleteTask(checklist.get(i));
            }
        }
        loadData();
    }

    /**
     * Edits a given task
     * @param oldTask Checklist
     * @param updateTask Checklist
     */
    public void editItem(Checklist oldTask, Checklist updateTask){
        oldTask.setTodoItem(updateTask.getTodoItem());
        oldTask.setTaskStatus(updateTask.getTaskStatus());
        oldTask.setDueDate(updateTask.getDueDate());
        oldTask.setPriority(updateTask.getPriority());
        checklistDatabase.checklistDao().updateTask(oldTask);
        loadData();

    }

    /**
     * Updates the status of the task
     * @param task Checklist
     */
    public void updateStatus(Checklist task){
        checklistDatabase.checklistDao().updateTask(task);
        loadData();
    }

    /**
     * Pops of the View Task window
     * @param showCheckItem Integer, index on the list
     */
    public void showCheckItem(int showCheckItem){
        ViewChecklistDialog viewChecklistDialog = new ViewChecklistDialog();
        viewChecklistDialog.setCheckItem(checklist.get(showCheckItem), showCheckItem);  //gets index of arraylist
        viewChecklistDialog.show(getSupportFragmentManager(), "");
    }

}