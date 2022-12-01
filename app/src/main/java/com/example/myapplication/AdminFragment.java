package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.AdminFragmentBinding;
import com.example.myapplication.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment{
    ////////////////////////////////
    //TESTING FUNCTIONS
    private ArrayList<Course> createDummy(){
        Course A48 = new Course("Intro to Comp Sci", "CSCA48", new boolean[]{false, true, true}, null);
        ArrayList<Course>prereqs = new ArrayList<Course>();
        prereqs.add(A48);
        Course B09 = new Course("Systems Programming", "CSCB09", new boolean[]{false, true, true}, null);

        //ArrayList<Course> prereqs = new ArrayList<Course>();
        prereqs.add(B09);
        Course C37 = new Course("Computational Mathematics", "CSCC37", new boolean[]{true, false, false}, prereqs);

        //public Course(String name, String code, boolean[] sessions, List<Course> prerequisites)
        //dummy data set includes, default course, empty course, course with prereq
        ArrayList<Course> dummy = new ArrayList<Course>();
        dummy.add(B09);
        dummy.add(new Course());
        dummy.add(C37);
        return dummy;
    }

    ArrayList<Course> dummy = createDummy();
    /////////////////////////////////

    //REAL FUNCTIONS
    private AdminFragmentBinding binding;
    //LinearLayout mylayout = (LinearLayout) findViewById(R.id.admin_fragment.xml);
    @Override
    public View onCreateView(
                             LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState
    ){
        System.out.println("DUMMY SIZE: "+dummy.size());
        Course.courseList = dummy;
        System.out.println("In AdminFragment");
        //render all the currently registered courses on screen
        binding = AdminFragmentBinding.inflate(inflater, container, false);
        for (int i = 0; i < dummy.size(); i++) {
            //i got this off a quora answer, doesnt work but the idea is there
            //oncreateview() is called when this frame is "opened" by the user
            /**
            Button btn = new Button (AdminFragment.context);
            btn.setWidth(40);
            btn.setHeight(20);
            btn.setText(arrylist.get(i).gettext());
            yourlayout.addView(btn);
             **/
        }
        binding.AdminCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdminFragment.this)
                        .navigate(R.id.action_AdminFragment_to_AdminCreate);
            }
        });
        binding.AdminEditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavHostFragment.findNavController(AdminFragment.this)
                        .navigate(R.id.action_AdminFragment_to_AdminEdit);
            }
        });
        return binding.getRoot();
    }

    public ArrayList<Course> addNewCourse(){
        return null; //placeholder
    }

}
