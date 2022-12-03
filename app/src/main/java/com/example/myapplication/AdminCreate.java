package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

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

public class AdminCreate extends Fragment{

    private FragmentAdminCreateBinding binding;

    private DatabaseReference ref;
    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    protected HashSet<Course> courses = new HashSet<Course>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("In AdminCreate");
        ref = FirebaseDatabase.getInstance(DATABASEURL).getReference("Courses");
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
        ref = FirebaseDatabase.getInstance(DATABASEURL).getReference("Courses");
        binding = FragmentAdminCreateBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("ON ATTACH");
    }

    //checking if the course exists
    public boolean courseInDatabase(String c){
        for(Course storedCourse : courses){
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
        super.onViewCreated(view, savedInstanceState);

        binding.AdminHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdminCreate.this)
                        .navigate(R.id.action_AdminCreate_to_AdminFragment);
            }
        });

        SaveInput = getActivity().findViewById(R.id.submit);
        GetInput = getActivity().findViewById(R.id.CreateNameInput);
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
                else {

                    String strSessionsOffered = "";
                    for(boolean offered : sessionsOffered){
                        if(offered==false)strSessionsOffered+=0;
                        else strSessionsOffered+=1;
                    }
                    Course createdCourse = new Course(name, code, strSessionsOffered, "");
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