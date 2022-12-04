package com.example.myapplication;

import static java.util.Arrays.asList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Student.CourseList;
import Student.Timeline;
import Student.operationPP;

public class Student_Generate_Timeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_generate_timeline);

        //测试数据导入 Sessions
        List<String> Sessions_Choices = asList("Fall","Winter","Summer");

        //Creating the instance of ArrayAdapter containing list of names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Sessions_Choices);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView Choices =  (AutoCompleteTextView)findViewById(R.id.Session_Choices);
        Choices.setThreshold(1);//will start working from first character
        Choices.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //Choices.setTextColor(Color.RED);


        //测试数据导入 years
        List<Integer> year_Choices = new ArrayList<Integer>();
        int year=2022;
        for(int i=0;i<100;i++){
            year_Choices.add(year);
            year+=1;
        }
        //Creating the instance of ArrayAdapter containing list of names
        ArrayAdapter<Integer> adapter_year = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, year_Choices);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView Choices_Year =  (AutoCompleteTextView)findViewById(R.id.Year_Choice);
        Choices_Year.setThreshold(1);//will start working from first character
        Choices_Year.setAdapter(adapter_year);//setting the adapter data into the AutoCompleteTextView
        //Choices.setTextColor(Color.RED);

        Button generate_real = (Button) findViewById(R.id.GenerateR);
        generate_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AutoCompleteTextView YC = (AutoCompleteTextView) findViewById(R.id.Year_Choice);
                if (YC.getText().toString().length() == 0 || YC.getText().toString().length()>=6) {
                    Toast.makeText(Student_Generate_Timeline.this, "The Year is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int year =  Integer.parseInt(YC.getText().toString());

                if (!year_Choices.contains(year)){
                    Toast.makeText(Student_Generate_Timeline.this, "The Year is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AutoCompleteTextView SSC = (AutoCompleteTextView) findViewById(R.id.Session_Choices);
                String session =  SSC.getText().toString();

                //判断是否在可选session中
                if (!Sessions_Choices.contains(session)){
                    Toast.makeText(Student_Generate_Timeline.this, "Please re-enter the session name in \"\"Fall\",\"Winter\",\"Summer\".", Toast.LENGTH_SHORT).show();
                    return;
                }

                //理论上这个页面的现有代码不用改 你看着搞

                //past course hashset 的名字是 Student_Operation.Student_Past_Courses.CourseHAshSet
                //future course hashset 的名字是 Student_Operation.Student_Future_Courses.CourseHAshSet
                //all courses hashset的名字是 Student_Operation.AllCourseHashSet
                //startyear 的名字是 year
                //startsession的名字是 session
                //名字就是这么长，直接作为你的参数复制黏贴名字就行

                //Toast.makeText(Student_Generate_Timeline.this, "The Year is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                //上面这一行代码的作用是让屏幕弹一个下面的那个提示，你想用的话直接改这个string的文字，然后放在你想触发的地方就行，其他不用改

                //在这里加你的timeline的method!!
                operationPP op = new operationPP();
                Timeline result = op.generateTimeline(Student_Operation.Student_Past_Courses.CourseHAshSet,
                        Student_Operation.Student_Future_Courses.CourseHAshSet,
                        Student_Operation.AllCourseHashSet, year, session);



                String text="";
                List<String> s = result.sessions;
                List<ArrayList<String>> c = result.courses;

                for (int i=0; i<s.size(); i++) {
                    text = text + s.get(i) + ":" + "\n";
                    for (int j=0; j<c.get(i).size(); j++) {
                        text = text + c.get(i).get(j) + "\n";
                    }
                }



                //生成一会儿会在屏幕上显示的text 这里改成你要在屏幕上显示的文字 你自己改
                //text 这个string就是一会儿会在屏幕上显示的东西
                /*

                 */
                //上面这个for循环的例子就是让text的内容是输出所有课程内容

                //更改数据显示
                TextView TLV = (TextView) findViewById(R.id.Timeline_view);
                TLV.setText(text);

                //到这就结束了



            }

        });



        Button go_back = (Button) findViewById(R.id.GT_Go_Back);
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