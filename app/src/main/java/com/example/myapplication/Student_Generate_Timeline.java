package com.example.myapplication;

import static java.util.Arrays.asList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Student.CourseList;
import Student.Timeline;
import Student.operation;

public class Student_Generate_Timeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_generate_timeline);

        //import data Sessions
        List<String> Sessions_Choices = asList("Fall","Winter","Summer");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Sessions_Choices);
        AutoCompleteTextView Choices =  (AutoCompleteTextView)findViewById(R.id.Session_Choices);
        Choices.setThreshold(1);
        Choices.setAdapter(adapter);

        //import data years
        List<Integer> year_Choices = new ArrayList<Integer>();
        int year=2022;
        for(int i=0;i<100;i++){
            year_Choices.add(year);
            year+=1;
        }

        ArrayAdapter<Integer> adapter_year = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_dropdown_item_1line, year_Choices);
        AutoCompleteTextView Choices_Year =  (AutoCompleteTextView)findViewById(R.id.Year_Choice);
        Choices_Year.setThreshold(1);//will start working from first character
        Choices_Year.setAdapter(adapter_year);//setting the adapter data into the AutoCompleteTextView

        Button generate_real = (Button) findViewById(R.id.GenerateR);
        generate_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ensure future courses list is not empty
                if (Student_Operation.Student_Future_Courses.CourseHAshSet.isEmpty()){
                    Toast.makeText(Student_Generate_Timeline.this,
                            "Please add some future course you want to take at first.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                AutoCompleteTextView YC = (AutoCompleteTextView) findViewById(R.id.Year_Choice);
                if (YC.getText().toString().length() == 0 || YC.getText().toString().length()>=6) {
                    Toast.makeText(Student_Generate_Timeline.this,
                            "The Year is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int year =  Integer.parseInt(YC.getText().toString());
                if (!year_Choices.contains(year)){
                    Toast.makeText(Student_Generate_Timeline.this,
                            "The Year is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AutoCompleteTextView SSC = (AutoCompleteTextView) findViewById(R.id.Session_Choices);
                String session =  SSC.getText().toString();

                //ensure session choice is in session_choice_list
                if (!Sessions_Choices.contains(session)){
                    Toast.makeText(Student_Generate_Timeline.this,
                            "Please re-enter the session name in \"\"Fall\",\"Winter\",\"Summer\".",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                operation op = new operation();
                Timeline result = op.generateTimeline(Student_Operation.Student_Past_Courses.CourseHAshSet,
                        Student_Operation.Student_Future_Courses.CourseHAshSet,
                        Student_Operation.AllCourseHashSet, year, session);

                String text="";
                List<String> s = result.sessions;
                List<ArrayList<String>> c = result.courses;

                for (int i=0; i<s.size(); i++) {
                    text = text + s.get(i) + ":" + "\n";
                    for (int j=0; j<c.get(i).size(); j++) {
                        text = text + c.get(i).get(j) + "\n";
                    }
                }

                TextView TLV = (TextView) findViewById(R.id.Timeline_view);
                TLV.setText(text);
            }
        });

        Button go_back = (Button) findViewById(R.id.GT_Go_Back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}