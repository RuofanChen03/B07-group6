package com.example.myapplication;

import android.content.Context;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import androidx.navigation.fragment.NavHostFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


//import static org.mockito.Mockito.when;

/**
 * Login module local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {
    LoginModel mockLoginModel;
    LoginFragment mockLoginFragment;
    SignUpFragment mockSignUpFragment;
    NavHostFragment mockNavHostFragment;
    @Test
    public void login_null_student_account_test() {
        // Creating a null user
        User input = null;
        // Creating a mock object for LoginModel
        mockLoginModel = Mockito.mock(LoginModel.class);
        when(mockLoginModel.studentInDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);
        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, false),
                "Error! Unexpected Null User!");
    }
    @Test
    public void login_null_admin_account_test() {
        // Creating a null user
        User input = null;
        // Creating a mock object for LoginModel
        mockLoginModel = Mockito.mock(LoginModel.class);
        when(mockLoginModel.adminInDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);
        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, true),
                "Error! Unexpected Null User!");
    }
    @Test
    public void login_basic_student_account_test() {
        // Creating a student user that has empty strings as username & password
        // this user would not be in the database
        User input = new User("", "");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.studentInDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, false),
                "Wrong username or password!");
    }
    @Test
    public void login_basic_admin_account_test() {
        // Creating an admin user that has empty strings as username & password
        // this user would not be in the database
        User input = new User("", "");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.adminInDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, true),
                "Wrong username or password!");
    }
    @Test
    public void login_nonexistent_student_account_test() {
        // Creating a random student user that is definitely not in the data
        User input = new User("randomStudent", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.studentInDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, false),
                "Wrong username or password!");
    }
    @Test
    public void login_nonexistent_admin_account_test() {
        // Creating a random admin user that is definitely not in the data
        User input = new User("randomAdmin", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.adminInDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, true),
                "Wrong username or password!");
    }
    @Test
    public void login_existent_student_account_test() {
        // Creating a random student user that is definitely not in the data
        User input = new User("user1", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.studentInDatabase(input)).thenReturn(true);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, false),
                "Login successful!");
    }
    @Test
    public void navigation_login_to_sign_up() {
        // Creating mock object for LoginFragment
        mockLoginFragment = Mockito.mock(LoginFragment.class);
        when(mockLoginFragment.isVisible()).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        test.navigateToSignUp(mockLoginFragment);
        // Checking if the login attempt is successful
        assertFalse(mockLoginFragment.isVisible());
    }

    @Test
    public void login_existent_admin_account_test() {
        // Creating a random admin user that is definitely not in the data
        User input = new User("admin1", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.adminInDatabase(input)).thenReturn(true);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, true),
                "Login successful!");
    }
}