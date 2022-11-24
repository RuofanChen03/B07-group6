package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Course {
    String courseName;
    String courseCode;
    boolean[] sessions;
    List<Course> prerequisites;

    static ArrayList<Course> courseList;

    //default course initializer
    public Course(){
        courseName = "";
        courseCode = "";
        sessions = new boolean[3];
        prerequisites = null;
    }

    //full course initializer
    public Course(String name, String code, boolean[] sessions, List<Course> prerequisites){
        this.courseName = name;
        this.courseCode = code;
        this.sessions = sessions; //not sure how to force it to be length 3, fix
        this.prerequisites = prerequisites;
    }

    public void AddPrerequisite(Course course) { prerequisites.add(course); }

}
