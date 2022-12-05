package com.example.myapplication;

/**
 * This is the login presenter class, according to the MVP model.
 * It helps both the Login and Sign Up sections of the application.
 */
public class LoginPresenter {
    private LoginModel loginModel;

    public LoginPresenter(LoginModel loginModel){
        this.loginModel = loginModel;
    }

    public String loginAttempt(User input, boolean isCheckedAdminSwitch){
        String message;
        if (input == null)
            message = "Error! Unexpected Null User!";
        else{
            if (isCheckedAdminSwitch){
                if(loginModel.adminInDatabase(input)){
                    message = "Admin login successful!";
                }
                else
                    message = "Wrong username or password!";
            }
            else {
                if(loginModel.studentInDatabase(input)){
                    message = "Student login successful!";
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
