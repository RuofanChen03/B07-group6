package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Student_View_Future_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_future);


        TextView FCL = (TextView) findViewById(R.id.FutureCouseList);
        String text="";
        for (int i=1;i<30;i=i+1){
            text=text+"Show this\n";
        }
        FCL.setText(text);


        Button go_back = (Button) findViewById(R.id.future_go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent( Student_Operation.this,MainActivity.class);
                //startActivity(intent);
                finish();
            }

        });


    }


}