package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

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

    void removeCourse(ArrayList<Course> courses, Course course, LinearLayout ll)
    {
        courses.remove(course);
        renderCourses(Course.courseList, ll);
    }
    void addCourse(ArrayList<Course> courses, Course course, LinearLayout ll)
    {
        courses.add(course);
        renderCourses(Course.courseList, ll);
    }

    void renderCourses(ArrayList<Course> courses, LinearLayout ll) {

        ll.removeAllViews();
        for (int i = 0; i < courses.size(); i++) {
            Button b = new Button(getActivity());
            Course c = courses.get(i);

            b.setText(c.courseCode);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            b.setId(i);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getBaseContext(), b.getText(), Toast.LENGTH_SHORT).show();
                    if (ll != null){
                        removeCourse(courses, c, ll);
                    }

                }

            });

            ll.addView(b);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_view, container, false);
        //test.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        //test.setId(2);

        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.linearlayout);
        renderCourses(Course.courseList, ll);
        return rootView;
    }
}