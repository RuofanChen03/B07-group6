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

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        loginModel = new LoginModel();
        loginPresenter = new LoginPresenter(loginModel);

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
                        loginPresenter.loginAttempt(
                                input, adminSwitch.isChecked(), LoginFragment.this),
                        Toast.LENGTH_LONG).show();

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