package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Student_Operation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_operation);

        Button go_back = (Button) findViewById(R.id.Go_Back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent( Student_Operation.this,MainActivity.class);
                //startActivity(intent);
                finish();
            }

        });

        Button view_past = (Button) findViewById(R.id.Student_View_past);
        view_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Student_Operation.this,Student_View_Past_activity.class);
                startActivity(intent);
                //finish();
            }

        });

        Button view_future = (Button) findViewById(R.id.Student_View_future);
        view_future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Student_Operation.this,Student_View_Future_activity.class);
                startActivity(intent);
                //finish();
            }

        });
    }




}