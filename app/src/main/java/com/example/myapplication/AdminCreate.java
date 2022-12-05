package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.myapplication.databinding.FragmentAdminCreateBinding;
import com.example.myapplication.databinding.AdminFragmentBinding;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class AdminCreate extends Fragment{

    private FragmentAdminCreateBinding binding;
    private View rootView;

    private DatabaseReference ref;
    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    protected HashSet<Course> courses = new HashSet<Course>();
    protected HashSet<Course> prereqs = new HashSet<Course>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("In AdminCreate");
        ref = FirebaseDatabase.getInstance(DATABASEURL).getReference("Courses");
        binding = FragmentAdminCreateBinding.inflate(inflater, container, false);
        rootView = inflater.inflate(R.layout.fragment_admin_create, container, false);
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.AdminCreateLinearLayout);

        int i=1000;
        //getActivity().setContentView(R.layout.fragment_admin_create);
        for(Course c : AdminViewModel.courses){
            Button b = new Button(getActivity());
            b.setText(c.courseCode);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            b.setId(i);
            i++;
            ll.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddPrerequisite(c);
                }
            });
        }

        return rootView;

    }
    String p = "";

    public void AddPrerequisite(Course course) {
        String[] prereqsAsArray = (p).split(",");
        for(String pre : prereqsAsArray){
            if(pre.equals(course.courseCode)){
                Toast.makeText(getActivity().getApplicationContext(),
                        "\""+course.courseCode+"\" has already been included as a prerequisite!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
        prereqsAsArray = course.prerequisites.split(",");
        for(String pre : prereqsAsArray){
            if(pre.equals(code)){
                Toast.makeText(getActivity().getApplicationContext(),
                        "\""+course.courseCode+"\" cannot be a prerequisite, since "+code+" is a prerequisite of "+course.courseCode,
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        p += course.courseCode+",";
        Toast.makeText(getActivity().getApplicationContext(),
                "\""+course.courseCode + "\" has been added as a prerequisite.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("ON ATTACH");
    }

    //checking if the course exists
    public boolean courseInDatabase(String c){
        for(Course storedCourse : AdminViewModel.courses){
            if (storedCourse.courseCode.equals(c)){
                return true;
            }
        }
        return false;
    }

    Button SaveInput;
    EditText GetInput;
    String name;
    String code;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        System.out.println("onviewcreated");
        super.onViewCreated(view, savedInstanceState);

        Button adminHomepage = getActivity().findViewById(R.id.AdminHome);
        adminHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdminCreate.this)
                        .navigate(R.id.action_AdminCreate_to_AdminFragment);
            }
        });

        SaveInput = getActivity().findViewById(R.id.submit);
        GetInput = getActivity().findViewById(R.id.CreateNameInput);
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
                System.out.println(sessionsOffered[1]);
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

        Button adminSubmit = getActivity().findViewById(R.id.submit);
        adminSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("REA");
                GetInput = getActivity().findViewById(R.id.CreateNameInput);
                name = GetInput.getText().toString();
                GetInput = getActivity().findViewById(R.id.CreateCodeInput);
                code = GetInput.getText().toString();
                String codeOfGetInput = GetInput.getText().toString();

                //check if course doesn't exist
                if(courseInDatabase(codeOfGetInput)){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Code \""+code + "\" is already in use. Please try a different code.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(code.equals("")){ //blank code
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Code cannot be empty!",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    String strSessionsOffered = "";
                    for(boolean offered : sessionsOffered){
                        if(offered==false)strSessionsOffered+=0;
                        else strSessionsOffered+=1;
                    }
                    Course createdCourse = new Course(name, code, strSessionsOffered, p);
                    for(Course storedCourse : courses){
                        if(storedCourse.courseCode.equals(createdCourse.courseCode)){
                            ref.child(""+storedCourse.hashCode()).child("prerequisites").setValue(createdCourse.prerequisites);
                        }
                    }
                    ref.child("" + createdCourse.hashCode()).setValue(createdCourse);
                    Log.i("course created", createdCourse.toString());

                    Toast.makeText(getActivity().getApplicationContext(),
                            ("Course \"" + code + "\" has been successfully created!"),
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