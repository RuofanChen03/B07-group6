package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    private DatabaseReference ref = FirebaseDatabase.getInstance(DATABASEURL).getReference("Courses");

    static protected HashSet<Course> courses = new HashSet<Course>();

    public AdminViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminViewFragment newInstance(String param1, String param2) {
        AdminViewFragment fragment = new AdminViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    private void removeCourseFromDatabase(Course course) {
        String courseHashCode = ("" + course.hashCode());
        //System.out.println("REMOVING COURSE: "+ course.courseCode);
        //System.out.println("REMOVING COURSE OF HASH CODE: " + courseHashCode);

        //ref = FirebaseDatabase.getInstance().getReference("Courses");
        for(Course storedCourse : AdminViewModel.courses){
            p = storedCourse.prerequisites;
            DeletePrerequisite(course);
            ref.child(""+storedCourse.hashCode()).child("prerequisites").setValue(p);
        }
        ref.child(courseHashCode).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "Double-click to remove this course", Toast.LENGTH_SHORT).show();

                    //System.out.println("HEWWO???????????");
                }
                else
                {
                    Toast.makeText(getActivity(), "Failed to remove from database, did the course exist?", Toast.LENGTH_SHORT).show();
                    //System.out.println("HEWWO!!!!!!!!!!!");
                }
            }
        });

        return;
    }
    String p = "";
    public void DeletePrerequisite(Course course){
        String[] prereqsAsArray = (p).split(",");
        for(int i=0; i<prereqsAsArray.length; i++){
            System.out.println("p is currently "+p+". we are in the for loop for prereqsasarray = "+prereqsAsArray[i]);
            if(prereqsAsArray[i].equals(course.courseCode)){
                prereqsAsArray[i] = ""; // "removes" it from the array. Works because we don't allow empty codes
                p=""; //rebuilds p
                Toast.makeText(getActivity().getApplicationContext(),
                        "\""+course.courseCode+"\" has been removed as a prerequisite.",
                        Toast.LENGTH_SHORT).show();
                for(String newPre : prereqsAsArray){
                    if(!newPre.equals("")){
                        p += newPre+",";
                        System.out.println("p is currently:: "+p);
                    }
                }
                System.out.println("p is currently "+p);
                return;
            }

        }
    }

    static void removeCourse(Course course)
    {
        AdminViewFragment.courses.remove("" + course.hashCode());
    }
    void addCourse(Course course, LinearLayout ll)
    {
        courses.add(course);
        renderCourses(ll);
    }

    public void renderCourses(LinearLayout ll) {
        ll.removeAllViews();

        Iterator<Course> it = courses.iterator();
        int i=0;
        while (it.hasNext())
        {
            //System.out.println(it.next().courseCode);
            Course current = it.next();

            Button b = new Button(getActivity());
            b.setText(current.courseCode);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            b.setId(i);
            i++;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity().getBaseContext(), b.getText(), Toast.LENGTH_SHORT).show();
                    if (ll != null) {

                        removeCourseFromDatabase(current);
                        renderCourses(ll);
                    }

                }
            });
            ll.addView(b);
        }
    }

    void addCourse(Course course)
    {
        courses.add(course);
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //courses.clear();
        AdminFragment.x = new AdminViewModel();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_view, container, false);
        //test.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        //test.setId(2);

        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.linearlayout);

        courses = AdminViewModel.courses;
        System.out.println("TOTAL COURSES SIZE: " + courses.size());

        for (Course course: courses){
            System.out.println("COURSE CODE: " + course.courseCode);
            System.out.println("COURSE NAME: " + course.courseName);

        }

        renderCourses(ll);
        return rootView;
    }
}