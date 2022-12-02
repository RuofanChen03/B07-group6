package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

                Toast.makeText(getActivity(), loginPresenter.signUpAttempt(input, confirmedValue),
                        Toast.LENGTH_LONG).show();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                NavHostFragment.findNavController(SignUpFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}