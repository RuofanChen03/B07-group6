package com.example.myapplication;

public class PastCourse extends Operation {
    public void addCourse(CourseList course) {
        newList.add(course);
    }

    public void deleteCourse(CourseList course) {
        newList.remove(course);
    }
}
