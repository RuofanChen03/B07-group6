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

import com.example.myapplication.Course;
import com.example.myapplication.databinding.AdminFragmentBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AdminFragment extends Fragment{
    private AdminFragmentBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ){
        binding = AdminFragmentBinding.inflate(inflater, container, false);
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

        binding.AdminViewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavHostFragment.findNavController(AdminFragment.this)
                        .navigate(R.id.action_AdminFragment_to_adminViewFragment);
            }
        });
        return binding.getRoot();
    }

}