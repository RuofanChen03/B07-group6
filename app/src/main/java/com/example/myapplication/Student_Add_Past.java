package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import Student.TestData;

public class Student_Add_Past extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_past);

        Button go_back = (Button) findViewById(R.id.Add_past_go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent( Student_Operation.this,MainActivity.class);
                //startActivity(intent);
                finish();
            }

        });

        //测试数据导入
        TestData Test = new Student.TestData();
        //Creating the instance of ArrayAdapter containing list of names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Test.courseCodeList);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView Choices =  (AutoCompleteTextView)findViewById(R.id.Add_Past_Choice);
        Choices.setThreshold(1);//will start working from first character
        Choices.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //Choices.setTextColor(Color.RED);


        Button APC = (Button) findViewById(R.id.Add_Past_Choice_Button);
        APC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent( Student_Operation.this,MainActivity.class);
                //startActivity(intent);
                //
                // finish();

                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.Add_Past_Choice);
                String CC =  CCV.getText().toString();
                //判断是否在可选的课程列表中
                if (!Test.courseCodeList.contains(CC)){
                    Toast.makeText(Student_Add_Past.this, "The Course is not available now. Please reenter.", Toast.LENGTH_SHORT).show();
                    return;
                }








            }

        });

    }
}