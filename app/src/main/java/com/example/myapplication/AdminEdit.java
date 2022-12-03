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
                //course exists
                else{
                    TextView tempName = (TextView) getActivity().findViewById(R.id.EditNameInput);
                    TextView tempCode = (TextView) getActivity().findViewById(R.id.EditCodeInput);

                    //filling in all the fetched data
                    for(Course storedCourse : courses){
                        if(storedCourse.courseCode.equals(codeOfGetInput)){ //based on the matching key code
                            tempName.setText(storedCourse.courseName);
                            tempCode.setText(storedCourse.courseCode);
                        }
                    }

                }
            }
        });

        //navigation
        binding.AdminHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdminEdit.this)
                        .navigate(R.id.action_AdminEdit_to_AdminFragment);
            }
        });

        //Setting the accessors for these buttons
        SaveInput = getActivity().findViewById(R.id.submit);
        GetInput = getActivity().findViewById(R.id.CourseToEditText);

        //listening for changes to the sessions available
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

        //on submitting the changes
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    System.out.println("Before call:");
                    GetInput = getActivity().findViewById(R.id.EditNameInput);
                    name = GetInput.getText().toString();
                    GetInput = getActivity().findViewById(R.id.EditCodeInput);
                    code = GetInput.getText().toString();

                    //converting sessions into a string
                    String strSessionsOffered = "";
                    for(boolean offered : sessionsOffered){
                        if(offered==false)strSessionsOffered+=0;
                        else strSessionsOffered+=1;
                    }

                    //looking through the courses hashset to find which one we want to change
                    for(Course storedCourse : courses){
                        if(storedCourse.courseCode.equals(codeOfGetInput)){
                            System.out.println("storedCourse = "+storedCourse.courseCode);
                            ref.child(""+storedCourse.hashCode()).child("courseName").setValue(name);
                            ref.child(""+storedCourse.hashCode()).child("courseCode").setValue(code);
                            //MISSING PREREQUISITE
                            ref.child(""+storedCourse.hashCode()).child("sessions").setValue(strSessionsOffered);
                        }
                    }
                    Toast.makeText(getActivity().getApplicationContext(),
                            ("Course \"" + code + "\" has been successfully edited!"),
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}