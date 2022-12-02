package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;

public class AdminEdit extends Fragment {

    private FragmentAdminEditBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("In AdminEdit");
        binding = FragmentAdminEditBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public static int getByCourseCode(String code){
        for(int i=0; i<Course.courseList.size(); i++){
            System.out.println(Course.courseList.get(i).courseCode);
            if(Course.courseList.get(i).courseCode.equals(code)) return i;
        }
        return -1;
    }

    Button SaveInput;
    EditText GetInput;
    String name;
    String code;
    String courseToEdit;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("In ADMIN EDIT");

        binding.SearchCourseForEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText GetInput = getActivity().findViewById(R.id.CourseToEditText);
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
                //System.out.println("REA");
                //Course c = new Course();
                //System.out.println("IS COURSE NULL: "+Course.courseList == null);

                //PLEASE NOTE THAT THIS DOESN'T CURRENTLY WORK. THIS IS BECAUSE OF HOW THE DATA
                // LIFECYCLE IN ANDROID WORKS, ESSENTIALLY KILLING THE USE OF THE STATIC LIST<COURSE>
                // ELEMENT. THIS WILL BE FIXED WHEN FIREBASE IS FULLY IMPLEMENTED
                System.out.println("Before call:"+Course.courseList.size());

                int indexOfCourseToEdit = getByCourseCode(courseToEdit);

                if(indexOfCourseToEdit>=0){
                    GetInput = getActivity().findViewById(R.id.EditNameInput);
                    name = GetInput.getText().toString();
                    GetInput = getActivity().findViewById(R.id.EditCodeInput);
                    code = GetInput.getText().toString();
                    Course updatedCourse = new Course(name, code, sessionsOffered, new ArrayList<Course>());
                    Course.courseList.add(updatedCourse);
                    System.out.println("After call:" + Course.courseList.size());
                    System.out.println("courseList.get(3) (fall)" + Course.courseList.get(3).sessions[0]);
                    System.out.println("courseList.get(3) (winter)" + Course.courseList.get(3).sessions[1]);
                    System.out.println("courseList.get(3) (summer)" + Course.courseList.get(3).sessions[2]);
                    Toast.makeText(getActivity().getApplicationContext(),
                            ("Course \"" + code + "\" has been successfully updated!"),
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