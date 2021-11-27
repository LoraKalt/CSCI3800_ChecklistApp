package edu.ucdenver.kalthoff.checklistapp;

import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucdenver.kalthoff.checklistapp.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private RecyclerView checklistRV;
    private ArrayList<Checklist> todoList;

    private DataManager db;

    private int sortBy; //0=default, 1=by due date, 2=by priority

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        //Set up Recycler View
        checklistRV = findViewById(R.id.checklistRecyclerView);

        //Testing purposes only:
        todoList = new ArrayList<>();
        todoList.add(new Checklist("Finish Homework",0, Calendar.getInstance(), R.drawable.priority_high_image));
        todoList.add(new Checklist("Finish new show",0,
                Calendar.getInstance(), R.drawable.priority_low_image));
        todoList.add(new Checklist("Do the Dishes", 0, null, R.drawable.priority_moderate_image));

        ChecklistAdapter checklistAdapter = new ChecklistAdapter(this, todoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        checklistRV.setLayoutManager(linearLayoutManager);
        checklistRV.setAdapter(checklistAdapter);

        //floating action button
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        loadData();
//    }

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
            return true;
        }
         else if (id == R.id.action_sort){
             return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToDo(Checklist checklist){
    }

//    public void addToDo(Checklist checklist){
//        String todo = checklist.getTodoItem();
//        int taskStatus = checklist.getTaskStatus();
//        String dueDate = checklist.getDueDate();
//        int priority = checklist.getPriority();
//
//        db.insert(todo, taskStatus, dueDate, priority);
//        loadData();
//    }

//    public void loadData(){
//        Cursor cursor = db.selectAll();
//        int listCount = cursor.getCount();
//        todoList.clear();
//
//        if(listCount > 0){
//            while(cursor.moveToNext()){
//                String todo = cursor.getString(1);
//                int taskStatus = cursor.getInt(2);
//                String dueDate = cursor.getString(3);
//                int priority = cursor.getInt(4);
//
//                Checklist check = new Checklist(todo, taskStatus, dueDate, priority);
//            }
//        }
//    }



}