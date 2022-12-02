package com.example.myapplication;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

/**
 * This is the login presenter class, according to the MVP model.
 * It helps both the Login and Sign Up sections of the application.
 */
public class LoginPresenter {
    private LoginModel loginModel;

    public LoginPresenter(LoginModel loginModel){
        this.loginModel = loginModel;
    }

    public void navigateToSignUp(Fragment frag){
        NavHostFragment.findNavController(frag)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }

    public void navigateToLogin(Fragment frag){
        NavHostFragment.findNavController(frag)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    public void navigateToAdmin(Fragment frag){
        NavHostFragment.findNavController(frag)
                .navigate(R.id.action_LoginFragment_to_AdminFragment);
    }

    public String loginAttempt(User input, boolean isCheckedAdminSwitch, Fragment frag){
        String message;
        if (input == null)
            message = "Error! Unexpected Null User!";
        else{
            if (isCheckedAdminSwitch){
                if(loginModel.adminInDatabase(input)){
                    this.navigateToAdmin(frag);
                    message = "Login successful!";
                }
                else
                    message = "Wrong username or password!";
            }
            else {
                if(loginModel.studentInDatabase(input)){
                    //NAVIGATION 2
                    message = "Login successful!";
                }
                else
                    message = "Wrong username or password!";
            }
        }
        return message;
    }

    public String signUpAttempt(User input, String strConfirm){
        String message;
        if (input == null)
            message = "Error! Unexpected Null User!";
        else{
            if(input.getUsername().equals("") || input.getPassword().equals("")){
                // If any of the fields are empty, the attempt will fail.
                message = "Cannot enter empty username or password!";
            }
            else if (!(input.getPassword().equals(strConfirm))){
                // If the 2 entered passwords are inconsistent, the attempt will fail.
                message = "Inconsistency of entered password!";
            }
            else {
                // If the entered username is already used, the attempt will fail.

                if(loginModel.usernameInStudentDatabase(input)){
                    message = "Used username! Please choose another one!";
                }
                else{
                    message = "Successfully signed up!!!";
                    loginModel.addStudentToDatabase(input);
                }
            }
        }
        return message;
    }
}
