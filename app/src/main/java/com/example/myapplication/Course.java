package com.example.myapplication;

import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.HashSet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Course {
    String courseName;
    String courseCode;
    String sessions;
    String prerequisites;

    private DatabaseReference ref;
    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    protected HashSet<Course> courses = new HashSet<Course>();

    //default course initializer
    public Course(){
        courseName = "";
        courseCode = "";
        sessions = "000";
        prerequisites = "";
    }

    //full course initializer
    public Course(String name, String code, String sessions, String prerequisites){
        this.courseName = name;
        this.courseCode = code;
        this.sessions = sessions;
        this.prerequisites = prerequisites;
    }
    public String[] splitPrereqsIntoArray(String prereqs){
        return prereqs.split(",");
    }

    public String getCourseName(){
        return this.courseName;
    }
    public String getCourseCode(){
        return this.courseCode;
    }
    public String getSessions(){
        return this.sessions;
    }
    public String getPrerequisites(){
        return this.prerequisites;
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }

}