package com.example.myapplication;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

public class AdminViewModel {
    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    private DatabaseReference ref;
    static protected HashSet<Course> courses = new HashSet<Course>();
    static protected HashSet<Course> coursesCopy = new HashSet<Course>();

    private void deepCopy(HashSet<Course> source, HashSet<Course> dest){
        for (Course course: source){
            Course place = new Course();

            place.courseName = course.getCourseName();
            place.courseCode = course.getCourseCode();
            place.sessions = course.getSessions();
            place.prerequisites = course.getPrerequisites();

            dest.add(place);
        }
    }
    public static int getDeepCopySize()
    {
        return coursesCopy.size();
    }

    public AdminViewModel(){
        ref = FirebaseDatabase.getInstance(DATABASEURL).getReference("Courses");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    courses.clear();
                    Log.i("Courses database", "data changed");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Course course = child.getValue(Course.class);
                        courses.add(course);
                        Log.i("course added; ", course.toString());
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
    }
}
