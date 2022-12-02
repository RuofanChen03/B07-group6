package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CourseListData extends ViewModel {
    // Expose screen UI state
    private MutableLiveData<List<Course>> courseList;
    public LiveData<List<Course>> getCourses() {
        if (courseList == null) {
            courseList = new MutableLiveData<List<Course>>();
            loadCourses();
        }
        return courseList;
    }

    // Handle business logic
    private void loadCourses() {
        // Do an asynchronous operation to fetch Courses

    }
}