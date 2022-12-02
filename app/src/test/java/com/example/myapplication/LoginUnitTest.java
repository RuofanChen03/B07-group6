package com.example.myapplication;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;


/**
 * Login module local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {
    LoginModel mockLoginModel;
    LoginFragment mockLoginFragment;
    @Test
    public void login_null_student_account_test() {
        // Creating a null user
        User input = null;
        // Creating a mock object for LoginModel
        mockLoginModel = Mockito.mock(LoginModel.class);
        mockLoginFragment = Mockito.mock(LoginFragment.class);
        when(mockLoginModel.studentInDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);
        // Checking if the login attempt is successful
        assertEquals(test.loginAttempt(input, false, mockLoginFragment),
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
        assertEquals(test.loginAttempt(input, true, mockLoginFragment),
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
        assertEquals(test.loginAttempt(input, false, mockLoginFragment),
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
        assertEquals(test.loginAttempt(input, true, mockLoginFragment),
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
        assertEquals(test.loginAttempt(input, false, mockLoginFragment),
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
        assertEquals(test.loginAttempt(input, true, mockLoginFragment),
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
        assertEquals(test.loginAttempt(input, false, mockLoginFragment),
                "Login successful!");
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
        assertEquals(test.loginAttempt(input, true, mockLoginFragment),
                "Login successful!");
    }


    @Test
    public void sign_up_null_user_test() {
        // Creating a null user
        User input = null;
        // Creating a mock object for LoginModel
        mockLoginModel = Mockito.mock(LoginModel.class);
        when(mockLoginModel.usernameInStudentDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);
        // Checking if the sign up attempt is successful
        assertEquals(test.signUpAttempt(input, ""),
                "Error! Unexpected Null User!");
    }
    @Test
    public void sign_up_username_and_password_empty_test() {
        // Creating a student user that has empty strings as username & password
        // this would not be a valid user
        User input = new User("", "");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.usernameInStudentDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the sign in attempt is successful
        assertEquals(test.signUpAttempt(input, ""),
                "Cannot enter empty username or password!");
    }
    @Test
    public void sign_up_username_empty_test() {
        // Creating a student user that has empty strings as username & password
        // this would not be a valid user
        User input = new User("", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.usernameInStudentDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the sign in attempt is successful
        assertEquals(test.signUpAttempt(input, "password"),
                "Cannot enter empty username or password!");
    }
    @Test
    public void sign_up_password_empty_test() {
        // Creating a student user that has empty strings as username & password
        // this would not be a valid user
        User input = new User("admin1", "");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.usernameInStudentDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the sign in attempt is successful
        assertEquals(test.signUpAttempt(input, "password"),
                "Cannot enter empty username or password!");
    }
    @Test
    public void sign_up_inconsistent_passwords_test() {
        // Creating a student user that has empty strings as username & password
        // this would not be a valid user
        User input = new User("admin1", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.usernameInStudentDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the sign in attempt is successful
        assertEquals(test.signUpAttempt(input, "password1"),
                "Inconsistency of entered password!");
    }
    @Test
    public void sign_up_existent_username_test() {
        // Creating a student user that has empty strings as username & password
        // this would not be a valid user
        User input = new User("admin1", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.usernameInStudentDatabase(input)).thenReturn(true);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the sign in attempt is successful
        assertEquals(test.signUpAttempt(input, "password"),
                "Used username! Please choose another one!");
    }
    @Test
    public void sign_up_nonexistent_username_test() {
        // Creating a student user that has empty strings as username & password
        // this would not be a valid user
        User input = new User("admin1", "password");

        // Creating a mock object for LoginModel
        mockLoginModel= Mockito.mock(LoginModel.class);
        when(mockLoginModel.usernameInStudentDatabase(input)).thenReturn(false);
        // Testing the instantiation
        LoginPresenter test = new LoginPresenter(mockLoginModel);

        // Checking if the sign in attempt is successful
        assertEquals(test.signUpAttempt(input, "password"),
                "Successfully signed up!!!");
    }
}