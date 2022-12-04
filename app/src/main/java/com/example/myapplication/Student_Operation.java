package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import Student.CourseList;
import Student.CourseDataType;

public class Student_Operation extends AppCompatActivity {


    public static ArrayList<String> AllCoursesCode;
    public static HashSet<CourseList> AllCourseHashSet;
    public static CourseDataType Student_Past_Courses;
    public static CourseDataType Student_Future_Courses;
    private final String DS = "https://b07project-943e2-default-rtdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_operation);

        AllCourseHashSet = new HashSet<CourseList>();

        DatabaseReference CLref = FirebaseDatabase.getInstance(DS).getReference("Courses");
        // Read from the database
        CLref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    AllCourseHashSet.clear();
                    Log.i("Get All Courses", "getting all courses");

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        //transform prerequisites
                        String prepre = child.child("prerequisites").getValue(String.class);
                        ArrayList<String> pre1 = new ArrayList<String>();
                        if (!prepre.equals("")) {
                            List<String> prerequisite = Arrays.asList(prepre.split(","));
                            //change list to arraylist
                            for(String s1:prerequisite){
                                pre1.add(s1);
                            }
                        }

                        //transform offeringSession
                        ArrayList<String> offeringSession = new ArrayList<String>();
                        String s=child.child("sessions").getValue(String.class);

                        if (s.length()==3){
                            if(s.charAt(0)==('1')) offeringSession.add("Fall");
                            if(s.charAt(1)=='1') offeringSession.add("Winter");
                            if(s.charAt(2)=='1') offeringSession.add("Summer");
                        }

                        CourseList value = new CourseList(child.child("courseCode").getValue(String.class),
                                child.child("courseName").getValue(String.class),offeringSession,pre1);
                        AllCourseHashSet.add(value);
                        Log.d("Read all courses list", "CourseCode is: " + value.courseCode);
                    }
                    Student_Operation.AllCourseHashSet = AllCourseHashSet;
                } catch (Exception e) {
                    Log.w("warning", "error with persistent listener", e);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read courses", "Failed to read value.", error.toException());
            }
        });

        //initialize past and future course list
        Student_Future_Courses = new CourseDataType();
        Student_Past_Courses = new CourseDataType();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                AllCoursesCode = new ArrayList<String>();
                for (CourseList Course: AllCourseHashSet) {
                    AllCoursesCode.add(Course.courseCode);
                }
            }
        }, 500);   //0.5s

        Button go_back = (Button) findViewById(R.id.Go_Back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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