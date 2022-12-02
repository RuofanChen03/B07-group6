package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private LoginPresenter loginPresenter;
    private LoginModel loginModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        loginModel = new LoginModel();
        loginPresenter = new LoginPresenter(loginModel);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signUpNowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText usernameEditText = requireActivity().findViewById(R.id.edit_text_username_sign_up);
                String usernameValue = usernameEditText.getText().toString();
                EditText passwordEditText = requireActivity().findViewById(R.id.edit_text_password_sign_up);
                String passwordValue = passwordEditText.getText().toString();
                EditText confirmedEditText = requireActivity().findViewById(R.id.edit_text_confirm_sign_up);
                String confirmedValue = confirmedEditText.getText().toString();

                User input = new User(usernameValue, passwordValue);
//                int signUpState = loginPresenter.signUpAttempt(input, confirmedValue);

                Toast.makeText(getActivity(), loginPresenter.signUpAttempt(input, confirmedValue),
                        Toast.LENGTH_LONG).show();

//                if(signUpState == 0){
//                    Toast.makeText(getActivity(), "Cannot enter empty username or password!",
//                            Toast.LENGTH_LONG).show();
//                }
//                else if (signUpState == 1){
//                    Toast.makeText(getActivity(), "Inconsistency of entered password!",
//                            Toast.LENGTH_LONG).show();
//                }
//                else if (signUpState == 2){
//                    Toast.makeText(getActivity(), "Used username! Please choose another one!",
//                            Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(getActivity(), "Successfully signed up!!!",
//                            Toast.LENGTH_LONG).show();
//                }
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                loginPresenter.navigateToLogin(SignUpFragment.this);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}