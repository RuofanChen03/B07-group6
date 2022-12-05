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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentAdminCreateBinding;
import com.example.myapplication.databinding.FragmentAdminEditBinding;
import com.example.myapplication.databinding.AdminFragmentBinding;

import com.google.android.material.chip.Chip;
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
    private View rootView;
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
        rootView = inflater.inflate(R.layout.fragment_admin_edit, container, false);
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.AdminEditLinearLayout);

        int i=10000;
        for(Course c : AdminViewModel.courses){
            Button b = new Button(getActivity());
            b.setText(c.courseCode);
            b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setId(i);
            i++;
            ll.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button searchCourseButton = getActivity().findViewById(R.id.SearchCourseForEditButton);
                    if(searchCourseButton==null || searchCourseButton.toString().equals("")){
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Please enter a course code first!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        System.out.println("codeOfGetInput is: "+codeOfGetInput);
                        AddPrerequisite(c);
                    }
                }
            });
        }

        return rootView;
    }

    String p = "";

    public void AddPrerequisite(Course course) {
        String[] prereqsAsArray = (p).split(",");
        for(String pre : prereqsAsArray){
            System.out.println("pre = "+pre);
            if(pre.equals(course.courseCode)){
                Toast.makeText(getActivity().getApplicationContext(),
                        "\""+course.courseCode+"\" has already been included as a prerequisite!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if(codeOfGetInput != null){
                if(codeOfGetInput.equals(course.courseCode)){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "\""+course.courseCode+"\" cannot be its own prerequisite!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        }

        p += course.courseCode+",";
        Toast.makeText(getActivity().getApplicationContext(),
                "\""+course.courseCode + "\" has been added as a prerequisite.",
                Toast.LENGTH_SHORT).show();
    }

    Button SaveInput;
    EditText GetInput;
    String name;
    String code;
    String codeOfGetInput;
    String[] splitPrerequisites = new String[AdminViewModel.courses.size()];

    //checking if the course exists
    public boolean courseInDatabase(String c){
        for(Course storedCourse : AdminViewModel.courses){
            if (storedCourse.courseCode.equals(c)){
                return true;
            }
        }
        return false;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("In ADMIN EDIT");
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.AdminEditLinearLayout);

        Button searchCourseButton = getActivity().findViewById(R.id.SearchCourseForEditButton);
        searchCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("search course button reached");
                EditText GetInput = getActivity().findViewById(R.id.CourseToEditText);
                codeOfGetInput = GetInput.getText().toString();

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
                    for(Course storedCourse : AdminViewModel.courses){
                        if(storedCourse.courseCode.equals(codeOfGetInput)){ //based on the matching key code
                            tempName.setText(storedCourse.courseName);
                            tempCode.setText(storedCourse.courseCode);
                        }
                    }
                }
            }
        });

        //navigation
        Button adminHomeButton = getActivity().findViewById(R.id.AdminHome);
        adminHomeButton.setOnClickListener(new View.OnClickListener() {
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

        Chip fallChip = getActivity().findViewById(R.id.FallSessionChip);
        fallChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[0]==false) sessionsOffered[0] = true;
                else sessionsOffered[0] = false;
            }
        });

        Chip winterChip = getActivity().findViewById(R.id.WinterSessionChip);
        winterChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[1]==false) sessionsOffered[1] = true;
                else sessionsOffered[1] = false;
            }
        });

        Chip summerChip = getActivity().findViewById(R.id.SummerSessionChip);
        summerChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[2]==false) sessionsOffered[2] = true;
                else sessionsOffered[2] = false;
            }
        });

        //on submitting the changes
        Button submitButton = getActivity().findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
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

                    System.out.println(strSessionsOffered);
                    System.out.println(code);
                    System.out.println(name);
                    System.out.println(p);
                    //looking through the courses hashset to find which one we want to change
                    for(Course storedCourse : AdminViewModel.courses){
                        //System.out.println("storedCourse.courseCode: "+storedCourse.courseCode);
                        //System.out.println("codeOfGetInput: "+codeOfGetInput);
                        if(storedCourse.courseCode.equals(codeOfGetInput)){
                            System.out.println("WE HAVE REACHED");
                            System.out.println("hashcode: "+storedCourse.hashCode());
                            ref.child(""+storedCourse.hashCode()).child("courseName").setValue(name);
                            ref.child(""+storedCourse.hashCode()).child("courseCode").setValue(code);
                            ref.child(""+storedCourse.hashCode()).child("prerequisites").setValue(p);
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