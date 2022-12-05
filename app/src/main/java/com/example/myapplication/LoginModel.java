package com.example.myapplication;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;

/**
 * This is the login model class, according to the MVP model.
 */
public class LoginModel {
    private DatabaseReference ref1;
    private DatabaseReference ref2;
    private String DATABASEURL = "https://b07project-943e2-default-rtdb.firebaseio.com/";
    protected HashSet<User> students = new HashSet<User>();
    protected HashSet<User> admins = new HashSet<User>();

    public LoginModel(){
        // Default constructor that sets the firebase references to the default database
        // Then, a persistent listener is added to the program to ensure data changes are recorded
        // Use this constructor unless using a new firebase realtime database link
        ref1 = FirebaseDatabase.getInstance(DATABASEURL).getReference("students");
        ref2 = FirebaseDatabase.getInstance(DATABASEURL).getReference("admins");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    students.clear();
                    Log.i("user database", "data changed");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        User user = child.getValue(User.class);
                        // Due to hashset properties, if changes are made to the accounts, the old
                        // data would be automatically overwritten.
                        students.add(user);
                        Log.i("student added; username", user.toString());
                    }
                }
                catch(Exception e){
                    Log.w("warning","error with persistent listener", e);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        });
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    admins.clear();
                    Log.i("user database", "data changed");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        User user = child.getValue(User.class);
                        // Due to hashset properties, if changes are made to the accounts, the old
                        // data would be automatically overwritten.
                        admins.add(user);
                        Log.i("admin added; username", user.toString());
                    }
                }
                catch(Exception e){
                    Log.w("warning","error with persistent listener", e);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        });
    }

    public void addStudentToDatabase(User input){
        // Assuming that the student login data would not be modified directly through firebase
        ref1.child("" + input.hashCode()).setValue(input);
        Log.i("student created", input.toString());
    }

    public boolean studentInDatabase(User input){
        // Input != null verified in presenter
        for(User storedStudent: students){
            if (storedStudent.equals(input)){
                return true;
            }
        }
        return false;
    }
    public boolean adminInDatabase(User input){
        // Input != null verified in presenter
        for(User storedAdmin: admins){
            if (storedAdmin.equals(input)){
                return true;
            }
        }
        return false;
    }
    public boolean usernameInStudentDatabase(User input){
        // Input != null verified in presenter
        for(User storedStudent: students){
            if (storedStudent.getUsername().equals(input.getUsername())){
                return true;
            }
        }
        return false;
    }

}
