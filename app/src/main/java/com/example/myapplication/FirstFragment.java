package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentFirstBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    protected List<User> a;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        //Reading from a realtime database using a persistent listener
        DatabaseReference ref = FirebaseDatabase.
                getInstance("https://cscb36groupproject-default-rtdb.firebaseio.com/").
                getReference("users");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("user database", "data changed");
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    Log.i("user", user.toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled",
                        databaseError.toException());
            }
        };

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText usernameEditText = (EditText) getActivity().findViewById(R.id.edit_text_username);
                String usernameValue = usernameEditText.getText().toString();
                EditText passwordEditText = (EditText) getActivity().findViewById(R.id.edit_text_password);
                String passwordValue = passwordEditText.getText().toString();

                User input = new User(usernameValue, passwordValue);
                for(User user: a){
                    if (a.equals(input)){
                        NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                    }
                    else{

                    }
                }
                Context context = null;
                context = context.getApplicationContext();
                CharSequence text = "wrong pass";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}