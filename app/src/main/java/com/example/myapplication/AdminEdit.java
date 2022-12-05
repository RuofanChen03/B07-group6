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
import android.widget.ToggleButton;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AdminEdit extends Fragment {

    private FragmentAdminEditBinding binding;
    private DatabaseReference ref;
    private View rootView;
    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    protected HashSet<Course> courses = new HashSet<Course>();
    ArrayList<ToggleButton> prereqButtons = new ArrayList<ToggleButton>();
    String p = "";

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
            ToggleButton b = new ToggleButton(getActivity());
            b.setText(c.courseCode);
            b.setTextOn(c.courseCode);
            b.setTextOff(c.courseCode);
            b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setId(i);
            i++;
            ll.addView(b);
            prereqButtons.add(b);
            /*
            System.out.println("Is button "+b.getText()+" already checked? "+b.isChecked());
            if(b.isChecked()){ //if the prerequisite already exists
                System.out.println(b.getText() + " is already checked.");
                b.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(!b.isChecked()){
                            DeletePrerequisite(c);
                        }
                        else{
                            AddPrerequisite(c);
                        }
                    }
                });
            }

             */
            //the prerequisite is being added
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(b.isChecked()){
                        boolean isHigherPrereq = false;
                        Button searchCourseButton = getActivity().findViewById(R.id.SearchCourseForEditButton);
                        if(searchCourseButton==null || searchCourseButton.toString().equals("")){
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Please enter a course code first!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            System.out.println("codeOfGetInput is: "+codeOfGetInput);

                            String[]prereqsAsArray = c.prerequisites.split(",");
                            System.out.println("prerequisites of "+c.courseCode+" are...");
                            for(String pre : prereqsAsArray){
                                System.out.println(pre);
                                if(pre.equals(codeOfGetInput)){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "\""+c.courseCode+"\" cannot be a prerequisite, since "+codeOfGetInput+" is a prerequisite of "+c.courseCode,
                                            Toast.LENGTH_SHORT).show();
                                    isHigherPrereq=true;
                                    b.setChecked(false);
                                }
                            }
                            if(!isHigherPrereq) {
                                String prereqStr[] = p.split(",");
                                System.out.println("p is currently: "+p+" b.getText() is currently "+b.getText());
                                System.out.println("(p is currently:) c.courseCode = "+c.courseCode);
                                System.out.println(Arrays.asList(prereqStr).indexOf(b.getText())>-1);
                                if(Arrays.asList(prereqStr).indexOf(b.getText())>-1) {
                                    System.out.println("(p is currently) if was reached");
                                    DeletePrerequisite(c);
                                }
                                else {
                                    System.out.println("(p is currently "+p+") else was reached");
                                    AddPrerequisite(c);
                                }
                            }
                        }
                    }
                    else{
                        System.out.println("p is currently "+p+". it has been unchecked");
                        DeletePrerequisite(c);
                    }
                }
            });


        }

        return rootView;
    }
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
        System.out.println("p is currently "+p);
        Toast.makeText(getActivity().getApplicationContext(),
                "\""+course.courseCode + "\" has been added as a prerequisite.",
                Toast.LENGTH_SHORT).show();
    }

    Button SaveInput;
    EditText GetInput;
    String name;
    String code;
    String codeOfGetInput;
    //listening for changes to the sessions available
    boolean sessionsOffered[] = {false, false, false};
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
    public void checkSessions(Course c){
        for(Course storedCourse : AdminViewModel.courses){
            if(storedCourse.courseCode.equals(c.courseCode)){
                ToggleButton sessionChips[] = {getActivity().findViewById(R.id.FallSessionChip),
                        getActivity().findViewById(R.id.WinterSessionChip),getActivity().findViewById(R.id.SummerSessionChip)};
                for(int i=0; i<3; i++){
                    System.out.println("Now we are checking session char "+storedCourse.sessions.charAt(i));
                    if(storedCourse.sessions.charAt(i)=='0') {
                        sessionChips[i].setChecked(false);
                        sessionsOffered[i]=false;
                    }
                    else {
                        sessionChips[i].setChecked(true);
                        sessionsOffered[i]=true;
                    }
                }
            }
        }
    }
    public void checkPrereqs(Course c){
        for(Course storedCourse : AdminViewModel.courses){
            if(storedCourse.courseCode.equals(c.courseCode)){
                String prereqStr[] = storedCourse.prerequisites.split(",");
                //loop through each item in the list prereqButtons
                //if the item appears in the split array prereqStr, then check it
                for(ToggleButton b : prereqButtons){
                    if(Arrays.asList(prereqStr).indexOf(b.getText())>-1){
                        b.setChecked(true);
                    }
                    else{
                        b.setChecked(false);
                    }
                }
            }
        }
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
                            p = storedCourse.prerequisites;
                            System.out.println("About to check sessions");
                            checkSessions(storedCourse);
                            checkPrereqs(storedCourse);
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

        ToggleButton fallChip = getActivity().findViewById(R.id.FallSessionChip);
        fallChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[0]==false) sessionsOffered[0] = true;
                else sessionsOffered[0] = false;
            }
        });

        ToggleButton winterChip = getActivity().findViewById(R.id.WinterSessionChip);
        winterChip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(sessionsOffered[1]==false) sessionsOffered[1] = true;
                else sessionsOffered[1] = false;
            }
        });

        ToggleButton summerChip = getActivity().findViewById(R.id.SummerSessionChip);
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