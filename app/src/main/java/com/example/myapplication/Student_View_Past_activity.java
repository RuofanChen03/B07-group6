package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;


import Student.CourseList;

public class Student_View_Past_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_past);

        //view past course text
        String text="";
        HashSet<CourseList> TestCourses = Student_Operation.Student_Past_Courses.CourseHAshSet;
        for (CourseList Course: TestCourses) {
            text = text + Course.courseCode +": "+ Course.courseName + '\n'+ "                 "
                    +Course.offeringSession + '\n'+ "                 " +
                    Course.prerequisite +'\n';
        }

        //update text view
        TextView PCL = (TextView) findViewById(R.id.PastCourseList);
        PCL.setText(text);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, Student_Operation.AllCoursesCode);
        AutoCompleteTextView Choices =  (AutoCompleteTextView)findViewById(R.id.add_Past_Choice);
        Choices.setThreshold(1);
        Choices.setAdapter(adapter);

        Button add_past = (Button) findViewById(R.id.student_Add_Course2);
        add_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_Past_Choice);
                String CC =  CCV.getText().toString();

                //whether course is valid
                if (!Student_Operation.AllCoursesCode.contains(CC)){
                    Toast.makeText(Student_View_Past_activity.this,
                            "The Course is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Student_Operation.Student_Past_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Past_activity.this,
                            "The Course has existed. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (CourseList C: Student_Operation.AllCourseHashSet){
                    if (C.courseCode.equals(CC)){
                        TestCourses.add(C);
                        Student_Operation.Student_Past_Courses.courseCodeList.add(CC);
                        break;
                    }
                }
                //update View
                String text="";
                for (CourseList Course: TestCourses) {
                    text = text + Course.courseCode +": "+ Course.courseName + '\n'+ "                 "
                            +Course.offeringSession + '\n'+ "                 " +
                            Course.prerequisite +'\n';
                }
                PCL.setText(text);
            }
        });

        //delete course
        Button deleteC = (Button) findViewById(R.id.student_Delete_Course2);
        deleteC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_Past_Choice);
                String CC =  CCV.getText().toString();

                //whether the course is valid
                if (!Student_Operation.Student_Past_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Past_activity.this,
                            "The Course is not existing. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (CourseList C: TestCourses){
                    if (C.courseCode.equals(CC)){
                        TestCourses.remove(C);
                        Student_Operation.Student_Past_Courses.courseCodeList.remove(CC);
                        break;
                    }
                }

                String text="";
                for (CourseList Course: TestCourses) {
                    text = text + Course.courseCode +": "+ Course.courseName + '\n'+ "                 "
                            +Course.offeringSession + '\n'+ "                 " +
                            Course.prerequisite +'\n';
                }
                PCL.setText(text);

            }

        });

        Button go_back = (Button) findViewById(R.id.past_go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}