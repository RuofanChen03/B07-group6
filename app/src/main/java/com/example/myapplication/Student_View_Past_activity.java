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
import Student.TestData;

public class Student_View_Past_activity extends AppCompatActivity {

    //past course list
    //HashSet<CourseList> TestCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_past);

        //测试课程数据 view
        String text="";
        //MainActivity.Student_Past_Courses = new Student.TestData(1);
        HashSet<CourseList> TestCourses = MainActivity.Student_Past_Courses.testCourse;

        for (CourseList Course: TestCourses) {
            text = text + Course.courseCode +": "+ Course.offeringSession+ '\n'+ "                 " +
                    Course.prerequisite  +'\n';
        }


        //更改数据显示
        TextView PCL = (TextView) findViewById(R.id.PastCourseList);
        PCL.setText(text);

        //测试数据导入
        TestData ChoiceList = MainActivity.AllCourses;
        //Creating the instance of ArrayAdapter containing list of names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ChoiceList.courseCodeList);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView Choices =  (AutoCompleteTextView)findViewById(R.id.add_Past_Choice);
        Choices.setThreshold(1);//will start working from first character
        Choices.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //Choices.setTextColor(Color.RED);

        Button add_past = (Button) findViewById(R.id.student_Add_Course2);
        add_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firebase添加数据
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.setValue("Hello, World!");
                //Intent intent = new Intent( Student_View_Past_activity.this,Student_Add_Past.class);
                //startActivity(intent);
                //finish();
                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_Past_Choice);
                String CC =  CCV.getText().toString();

                //判断是否在可选的课程列表中
                if (!ChoiceList.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Past_activity.this, "The Course is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (MainActivity.Student_Past_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Past_activity.this, "The Course has existed. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (CourseList C: ChoiceList.testCourse){
                    //Toast.makeText(Student_View_Past_activity.this, C.courseCode+"\n"+CC, Toast.LENGTH_LONG).show();
                    if (C.courseCode.equals(CC)){
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        TestCourses.add(C);
                        MainActivity.Student_Past_Courses.courseCodeList.add(CC);
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        break;
                    }
                }

                //update View
                String text="";
                //TestData Test = new Student.TestData(1);
                //TestCourses = Test.testCourse;


                for (CourseList Course: TestCourses) {
                    text = text + Course.courseCode +": "+ Course.offeringSession+ '\n'+ "                 " +
                            Course.prerequisite  +'\n';
                }
                //可以不用重新生成text，可以直接再最后加？
                PCL.setText(text);
            }
        });

        //delete course
        Button deleteC = (Button) findViewById(R.id.student_Delete_Course2);
        deleteC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Firebase删除数据
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.removeValue();

                //Intent intent = new Intent( Student_Operation.this,MainActivity.class);
                //startActivity(intent);
                //finish();

                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_Past_Choice);
                String CC =  CCV.getText().toString();

                //判断是否在已有的课程列表中
                if (!MainActivity.Student_Past_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Past_activity.this, "The Course is not existing. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }


                for (CourseList C: TestCourses){
                    //Toast.makeText(Student_View_Past_activity.this, C.courseCode+"\n"+CC, Toast.LENGTH_LONG).show();
                    if (C.courseCode.equals(CC)){
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        TestCourses.remove(C);
                        MainActivity.Student_Past_Courses.courseCodeList.remove(CC);
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        break;
                    }
                }

               // TestCourses.remove(CC);
                String text="";
                //TestData Test = new Student.TestData(1);
                //TestCourses = Test.testCourse;

                for (CourseList Course: TestCourses) {
                    text = text + Course.courseCode +": "+ Course.offeringSession+ '\n'+ "                 " +
                            Course.prerequisite  +'\n';
                }
                PCL.setText(text);


            }

        });


        Button go_back = (Button) findViewById(R.id.past_go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent( Student_Operation.this,MainActivity.class);
                //startActivity(intent);
                finish();
            }

        });
    }
}