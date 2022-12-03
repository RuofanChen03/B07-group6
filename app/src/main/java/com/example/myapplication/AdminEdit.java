package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentAdminCreateBinding;
import com.example.myapplication.databinding.FragmentAdminEditBinding;
import com.example.myapplication.databinding.AdminFragmentBinding;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class AdminEdit extends Fragment {

    private FragmentAdminEditBinding binding;
    private DatabaseReference ref;
    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    protected HashSet<Course> courses = new HashSet<Course>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("In AdminEdit");
        ref = FirebaseDatabase.getInstance(DATABASEURL).getReference("Courses");
        binding = FragmentAdminEditBinding.inflate(inflater, container, false);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    courses.clear();
                    Log.i("Courses database", "data changed");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Course course = child.getValue(Course.class);
                        // Due to hashset properties, if changes are made to the accounts, the old
                        // data would be automatically overwritten.
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
        return binding.getRoot();
    }
/*
    public static int getByCourseCode(String code){
        for(int i=0; i<Course.courseList.size(); i++){
            System.out.println(Course.courseList.get(i).courseCode);
            if(Course.courseList.get(i).courseCode.equals(code)) return i;
        }
        return -1;
    }

 */

    Button SaveInput;
    EditText GetInput;
    String name;
    String code;

    //checking if the course exists
    public boolean courseInDatabase(String c){
        for(Course storedCourse : courses){
            if (storedCourse.courseCode.equals(c)){
                return true;
            }
        }
        return false;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("In ADMIN EDIT");

        binding.SearchCourseForEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText GetInput = getActivity().findViewById(R.id.CourseToEditText);
                String codeOfGetInput = GetInput.getText().toString();

                //check if course doesn't exist
                if(!courseInDatabase(codeOfGetInput)){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Course does not exist. Try spelling it again or go back to add a new course!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    TextView tempName = (TextView) getActivity().findViewById(R.id.EditNameInput);
                    TextView tempCode = (TextView) getActivity().findViewById(R.id.EditCodeInput);
                    //filling in all the fetched data
                    for(Course storedCourse : courses){
                        if(storedCourse.courseCode.equals(codeOfGetInput)){
                            tempName.setText(storedCourse.courseName);
                            tempCode.setText(storedCourse.courseCode);
                        }
                    }

                    /*
                    ref.child("" + createdCourse.hashCode()).setValue(createdCourse);
                    Log.i("course created", createdCourse.toString());
                    TextView tempText = (TextView) getActivity().findViewById(R.id.EditNameInput);
                    tempText.setText());
                    //tempText.setText(Course.courseList.get(indexOfCourseToEdit).courseName);
                    tempText = (TextView) getActivity().findViewById(R.id.EditCodeInput);
                    //tempText.setText(Course.courseList.get(indexOfCourseToEdit).courseCode);

                     */
                }
/*
                //CHECK IF EXISTS



                ref.child("" + code.hashCode()).setValue(createdCourse);
                Log.i("course created", createdCourse.toString());



                courseToEdit = GetInput.getText().toString();
                int indexOfCourseToEdit = getByCourseCode(courseToEdit);
                //FOR TESTING
                //indexOfCourseToEdit = 0;
                //END TESTING
                if(indexOfCourseToEdit < 0) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Course does not exist. Try spelling it again or go back to add a new course!",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    //filling in all the fetched data
                    TextView tempText = (TextView) getActivity().findViewById(R.id.EditNameInput);
                    tempText.setText(Course.courseList.get(indexOfCourseToEdit).courseName);
                    tempText = (TextView) getActivity().findViewById(R.id.EditCodeInput);
                    tempText.setText(Course.courseList.get(indexOfCourseToEdit).courseCode);
                }

 */
            }


        });



        binding.AdminHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdminEdit.this)
                        .navigate(R.id.action_AdminEdit_to_AdminFragment);
            }
        });

        SaveInput = getActivity().findViewById(R.id.submit);
        GetInput = getActivity().findViewById(R.id.CourseToEditText);
        boolean sessionsOffered[] = {false, false, false};

        binding.FallSessionChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[0]==false) sessionsOffered[0] = true;
                else sessionsOffered[0] = false;
            }
        });
        binding.WinterSessionChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[1]==false) sessionsOffered[1] = true;
                else sessionsOffered[1] = false;
            }
        });
        binding.SummerSessionChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[2]==false) sessionsOffered[2] = true;
                else sessionsOffered[2] = false;
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Reached the submission of edit course");

                EditText GetInput = getActivity().findViewById(R.id.CourseToEditText);
                String codeOfGetInput = GetInput.getText().toString();

                //course to edit was not properly inputted
                if(!courseInDatabase(codeOfGetInput)){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter an existing course to edit.",
                            Toast.LENGTH_SHORT).show();
                }
                //proper input
                else {
                    System.out.println("Before call:"+Course.courseList.size());
                    GetInput = getActivity().findViewById(R.id.CreateNameInput);
                    name = GetInput.getText().toString();
                    GetInput = getActivity().findViewById(R.id.CreateCodeInput);
                    code = GetInput.getText().toString();

                    //converting sessions into a string
                    String strSessionsOffered = "";
                    for(boolean offered : sessionsOffered){
                        if(offered==false)strSessionsOffered+=0;
                        else strSessionsOffered+=1;
                    }

                    for(Course storedCourse : courses){
                        if(storedCourse.courseCode.equals(codeOfGetInput)){
                            System.out.println("storedCourse = "+storedCourse.courseCode);
                            ref.child(""+storedCourse.hashCode()).child("courseName").setValue(name);
                            ref.child(""+storedCourse.hashCode()).child("courseCode").setValue(code);
                            ref.child(""+storedCourse.hashCode()).child("sessions").setValue(strSessionsOffered);
                            //ref.child("" + storedCourse.hashCode()).updateChildren();
                        }
                    }
                    Toast.makeText(getActivity().getApplicationContext(),
                            ("Course \"" + code + "\" has been successfully created!"),
                            Toast.LENGTH_SHORT).show();

                }
                /*
                //Course c = new Course();

                    Course createdCourse = new Course(name, code, strSessionsOffered, "");
                    //Course.courseList.add(createdCourse);
                    ref.child("" + createdCourse.hashCode()).update(createdCourse);
                    Log.i("course created", createdCourse.toString());
                    System.out.println("Code of most recent course: " + Course.courseList.get(Course.courseList.size()-1).courseCode);
                    /*
                    System.out.println("courseList.get(3) (fall)" + Course.courseList.get(3).sessions.charAt(0));
                    System.out.println("courseList.get(3) (winter)" + Course.courseList.get(3).sessions.charAt(0));
                    System.out.println("courseList.get(3) (summer)" + Course.courseList.get(3).sessions.charAt(0));
                    */


                //System.out.println("REA");
                //Course c = new Course();
                //System.out.println("IS COURSE NULL: "+Course.courseList == null);

                //PLEASE NOTE THAT THIS DOESN'T CURRENTLY WORK. THIS IS BECAUSE OF HOW THE DATA
                // LIFECYCLE IN ANDROID WORKS, ESSENTIALLY KILLING THE USE OF THE STATIC LIST<COURSE>
                // ELEMENT. THIS WILL BE FIXED WHEN FIREBASE IS FULLY IMPLEMENTED
                //System.out.println("Before call:"+Course.courseList.size());
/*
                int indexOfCourseToEdit = getByCourseCode(courseToEdit);

                if(indexOfCourseToEdit>=0){
                    GetInput = getActivity().findViewById(R.id.EditNameInput);
                    name = GetInput.getText().toString();
                    GetInput = getActivity().findViewById(R.id.EditCodeInput);
                    code = GetInput.getText().toString();
                    String strSessionsOffered = "";
                    for(boolean offered : sessionsOffered){
                        if(offered==false)strSessionsOffered+=0;
                        else strSessionsOffered+=1;
                    }

                    Course updatedCourse = new Course(name, code, strSessionsOffered, "");
                    Course.courseList.add(updatedCourse);
                    System.out.println("After call:" + Course.courseList.size());
                    System.out.println("courseList.get(3) (fall)" + Course.courseList.get(3).sessions.charAt(0));
                    System.out.println("courseList.get(3) (winter)" + Course.courseList.get(3).sessions.charAt(1));
                    System.out.println("courseList.get(3) (summer)" + Course.courseList.get(3).sessions.charAt(2));
                    Toast.makeText(getActivity().getApplicationContext(),
                            ("Course \"" + code + "\" has been successfully updated!"),
                            Toast.LENGTH_SHORT).show();

                }

 */

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}