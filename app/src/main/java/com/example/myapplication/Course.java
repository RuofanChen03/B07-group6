package com.example.myapplication;

import android.util.Log;

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

    static ArrayList<Course> courseList = new ArrayList<Course>();

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

    public void AddPrerequisite(Course course) {
        prerequisites += ","+course.courseCode;
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
    //public void AddPrerequisite(Course course) { prerequisites.add(course.courseCode); }
    /*
    public Course GetCourseFromCourseCode(String code){
        Course[] returnedCourse = new Course[1];
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    courseList.clear();
                    Log.i("course database", "data changed");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Course c = child.getValue(Course.class);
                        if(c.courseCode.equals(code)) returnedCourse[0]=c;
                    }
                }
                catch(Exception e){
                    Log.w("warning","error with persistent listener", e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });
        return returnedCourse[0];
    }

     */

    public static int getByCourseCode(String code){
        for(int i=0; i<courseList.size(); i++){
            System.out.println(courseList.get(i).courseCode);
            if(courseList.get(i).courseCode.equals(code)) return i;
        }
        return -1;
    }


    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }

    public static boolean doesCourseCodeExist(String code){
        for(Course c : courseList){
            if(c.courseCode.toLowerCase(Locale.ROOT).equals(code.toLowerCase())) return true;
        }
        return false;
    }
}