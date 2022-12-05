package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

import Student.CourseList;

public class Student_View_Future_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_future);

        //view future course text
        String text="";

        HashSet<CourseList> TestCourses = Student_Operation.Student_Future_Courses.CourseHAshSet;
        for (CourseList Course: TestCourses) {
            text = text + Course.courseCode +": "+ Course.courseName + '\n'+ "                 "
                    +Course.offeringSession + '\n'+ "                 " +
                    Course.prerequisite +'\n';
        }
        //set textview
        TextView FCL = (TextView) findViewById(R.id.FutureCouseList);
        FCL.setText(text);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Student_Operation.AllCoursesCode);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView Choices =  (AutoCompleteTextView)findViewById(R.id.add_future_Choice);
        Choices.setThreshold(1);//will start working from first character
        Choices.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        Button add_future = (Button) findViewById(R.id.student_Add_Course);
        add_future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_future_Choice);
                String CC =  CCV.getText().toString();
                //whether the course is valid
                if (!Student_Operation.AllCoursesCode.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this,
                            "The Course is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Student_Operation.Student_Future_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this,
                            "The Course has existed. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Student_Operation.Student_Past_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this,
                            "The Course has been taken. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (CourseList C: Student_Operation.AllCourseHashSet){
                    if (C.courseCode.equals(CC)){
                        TestCourses.add(C);

                        Student_Operation.Student_Future_Courses.courseCodeList.add(CC);
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
                FCL.setText(text);
            }
        });

        //delete course
        Button deleteC = (Button) findViewById(R.id.student_Delete_Course);
        deleteC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_future_Choice);
                String CC =  CCV.getText().toString();

                //whether the course is valid
                if (!Student_Operation.Student_Future_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this,
                            "The Course is not existing. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (CourseList C: TestCourses){
                    if (C.courseCode.equals(CC)){
                        TestCourses.remove(C);
                        Student_Operation.Student_Future_Courses.courseCodeList.remove(CC);
                        break;
                    }
                }

                String text="";
                for (CourseList Course: TestCourses) {
                    text = text + Course.courseCode +": "+ Course.courseName + '\n'+ "                 "
                            +Course.offeringSession + '\n'+ "                 " +
                            Course.prerequisite +'\n';
                }
                FCL.setText(text);
            }

        });

        Button generate_timeline = (Button) findViewById(R.id.Generate_TimeLine);
        generate_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Student_View_Future_activity.this,Student_Generate_Timeline.class);
                startActivity(intent);
            }

        });

        Button go_back = (Button) findViewById(R.id.future_go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}