package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Course {
    String courseName;
    String courseCode;
    boolean[] sessions;
    List<Course> prerequisites;

    static ArrayList<Course> courseList = new ArrayList<Course>();

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

    public static int getByCourseCode(String code){
        for(int i=0; i<courseList.size(); i++){
            System.out.println(courseList.get(i).courseCode);
            if(courseList.get(i).courseCode.equals(code)) return i;
        }
        return -1;
    }

    public static boolean doesCourseCodeExist(String code){
        for(Course c : courseList){
            if(c.courseCode.toLowerCase(Locale.ROOT).equals(code.toLowerCase())) return true;
        }
        return false;
    }
}