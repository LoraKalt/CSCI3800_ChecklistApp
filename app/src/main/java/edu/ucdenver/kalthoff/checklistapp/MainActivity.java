package edu.ucdenver.kalthoff.checklistapp;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucdenver.kalthoff.checklistapp.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ChecklistAdapter checklistAdapter;

    private RecyclerView checklistRV;
    private ArrayList<Checklist> checklist;
    //private DataManager db;

    private int sortBy; //0=default, 1=by due date, 2=by priority

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        //Set up Recycler View
        checklistRV = findViewById(R.id.checklistRecyclerView);

        checklist = new ArrayList<>();

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
                //builder.setCancelable(false); (for when you want to prevent them from exiting outside menu
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
            if(!checklist.isEmpty()) {
                Collections.sort(checklist, new creationComparator());
                checklistAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "Nothing to sort", Toast.LENGTH_LONG).show();
            }
        }
        else if (id == R.id.action_date_sort){
            if(!checklist.isEmpty()){
                Collections.sort(checklist, new dateComparator());
                checklistAdapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getApplicationContext(), "Nothing to sort", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.action_urgent_sort){
            if(!checklist.isEmpty()) {
                Collections.sort(checklist, new priorityComparator());
                checklistAdapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getApplicationContext(), "Nothing to sort", Toast.LENGTH_LONG).show();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    public void addNewItem(Checklist item){
        checklist.add(item);
        Log.i("info", Integer.toString(checklist.size()));
        checklistAdapter.notifyDataSetChanged();

    }

    public void deleteItem(int index){
        checklist.remove(index);
        checklistAdapter.notifyDataSetChanged();
    }

    public void deleteAllChecked(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checklist.removeIf(n -> (n.getTaskStatus() == true));
        }

        checklistAdapter.notifyDataSetChanged();

    }

    public void editItem(Checklist updatedItem, int index){
        Log.i("info", "In editItem() method");
        checklist.get(index).setTodoItem(updatedItem.getTodoItem());
        checklist.get(index).setTaskStatus(updatedItem.getTaskStatus());
        checklist.get(index).setDueDate(updatedItem.getDueDate());
        checklist.get(index).setPriority(updatedItem.getPriority());
        checklistAdapter.notifyDataSetChanged();

    }

    public void showCheckItem(int showCheckItem){
        Log.i("info", "Inside showCheckItem");
        ViewChecklistDialog viewChecklistDialog = new ViewChecklistDialog();
        viewChecklistDialog.setCheckItem(checklist.get(showCheckItem), showCheckItem);  //gets index of arraylist
        viewChecklistDialog.show(getSupportFragmentManager(), "");
    }

}