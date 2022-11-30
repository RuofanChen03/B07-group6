package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityAdminCreateBinding;

import java.util.ArrayList;

public class AdminCreateActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdminCreateBinding binding;
    Button SaveInput;
    EditText GetInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //GetInput = findViewById(R.id.CreateNameInput);
        //SaveInput = findViewById(R.id.submit);

        NavController navController = Navigation.findNavController(this, R.id.AdminCreate);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //System.out.println("reached");
        /*
        SaveInput.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                System.out.println("Before call:"+Course.courseList.size());
                Course createdCourse = new Course(GetInput.getText().toString(), "",  new boolean[]{false, false, false}, new ArrayList<Course>());
                Course.courseList.add(createdCourse);
                System.out.println("After call:"+Course.courseList.size());
            }
        });
        */

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }

    public void storeSubmit(){
        GetInput = findViewById(R.id.CreateNameInput);
        SaveInput = findViewById(R.id.submit);
        SaveInput.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                System.out.println("Before call:"+Course.courseList.size());
                Course createdCourse = new Course(GetInput.getText().toString(), "",  new boolean[]{false, false, false}, new ArrayList<Course>());
                Course.courseList.add(createdCourse);
                System.out.println("After call:"+Course.courseList.size());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.AdminCreate);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}