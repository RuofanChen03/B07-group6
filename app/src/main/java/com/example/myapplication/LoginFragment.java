package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginPresenter loginPresenter;
    private LoginModel loginModel;
//    protected static DatabaseReference ref1 = FirebaseDatabase.
//            getInstance("https://b07project-943e2-default-rtdb.firebaseio.com/").
//            getReference("students");
//    protected static DatabaseReference ref2 = FirebaseDatabase.
//            getInstance("https://b07project-943e2-default-rtdb.firebaseio.com/").
//            getReference("admins");
//    protected static HashSet<User> students = new HashSet<User>();
//    protected static HashSet<User> admins = new HashSet<User>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        loginModel = new LoginModel();
        loginPresenter = new LoginPresenter(loginModel);

//
//        //Reading from a realtime database using a persistent listener
//        ref1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                try{
//                    Log.i("student user database", "data changed");
//                    for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    User user = child.getValue(User.class);
//                        students.add(user);
//                        Log.i("student ", user.toString());
//                    }
//                }
//                catch(Exception e){}
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("warning", "loadPost:onCancelled",
//                        databaseError.toException());
//            }
//        });
//        ref2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i("admin user database", "data changed");
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    User user = child.getValue(User.class);
//                    admins.add(user);
//                    Log.i("admin ", user.toString());
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("warning", "loadPost:onCancelled",
//                        databaseError.toException());
//            }
//        });
//
//


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText usernameEditText = requireActivity().findViewById(R.id.edit_text_username);
                String usernameValue = usernameEditText.getText().toString();
                EditText passwordEditText = requireActivity().findViewById(R.id.edit_text_password);
                String passwordValue = passwordEditText.getText().toString();
                Switch adminSwitch = requireActivity().findViewById(R.id.switch_admin);

                User input = new User(usernameValue, passwordValue);


                Toast.makeText(getActivity(),
                        loginPresenter.loginAttempt(input, adminSwitch.isChecked()),
                        Toast.LENGTH_LONG).show();

//                if (adminSwitch.isChecked()){
//                    for(User storedAdmin: admins){
//                        if (storedAdmin.equals(input)){
//
//                            ok = true;
//                            ref1 = null;
//                            ref2 = null;
//                            students = null;
//                            admins = null;
//
////                        NavHostFragment.findNavController(FirstFragment.this)
////                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                        }
//                    }
//                }
//                else {
//                    for (User storedStu : students) {
//                        if (storedStu.equals(input)) {
//                            Toast.makeText(getActivity(), "Navigation!",
//                                    Toast.LENGTH_LONG).show();
////                        NavHostFragment.findNavController(FirstFragment.this)
////                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                        }
//                    }
//                }
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.navigateToSignUp(LoginFragment.this);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}