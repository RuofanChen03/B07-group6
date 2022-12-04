package com.example.myapplication;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

import Student.CourseList;

public class Student_View_Future_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_future);


        //测试课程数据 view
        String text="";
        //MainActivity.Student_Future_Courses = new Student.TestData("a"); //create the existed future course list
        HashSet<CourseList> TestCourses = Student_Operation.Student_Future_Courses.CourseHAshSet;

        for (CourseList Course: TestCourses) {
            text = text + Course.courseCode +": "+ Course.offeringSession + '\n'+ "                 " +
                    Course.prerequisite +'\n';
        }


        //更改数据显示
        TextView FCL = (TextView) findViewById(R.id.FutureCouseList);
        FCL.setText(text);

        //测试数据导入
        //AllCourses ChoiceList = new Student.TestData();
        //Creating the instance of ArrayAdapter containing list of names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Student_Operation.AllCoursesCode);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView Choices =  (AutoCompleteTextView)findViewById(R.id.add_future_Choice);
        Choices.setThreshold(1);//will start working from first character
        Choices.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        //Choices.setTextColor(Color.RED);

        Button add_future = (Button) findViewById(R.id.student_Add_Course);
        add_future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent( Student_View_Past_activity.this,Student_Add_Past.class);
                //startActivity(intent);
                //finish();
                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_future_Choice);
                String CC =  CCV.getText().toString();

                //CC.trim();
                //判断是否在可选的课程列表中
                if (!Student_Operation.AllCoursesCode.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this, "The Course is not available now. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Student_Operation.Student_Future_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this, "The Course has existed. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //再加一个判断是否在已经上过的past course里 如果上过的话不能再添加
                if(Student_Operation.Student_Past_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this, "The Course has been taken. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (CourseList C: Student_Operation.AllCourseHashSet){
                    //Toast.makeText(Student_View_Past_activity.this, C.courseCode+"\n"+CC, Toast.LENGTH_LONG).show();
                    if (C.courseCode.equals(CC)){
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        TestCourses.add(C);
                        Student_Operation.Student_Future_Courses.courseCodeList.add(CC);
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        break;
                    }
                }

                //update View
                String text="";
                //TestData Test = new Student.TestData(1);
                //TestCourses = Test.testCourse;

                for (CourseList Course: TestCourses) {
                    text = text + Course.courseCode +": "+ Course.offeringSession + '\n'+ "                 " +
                            Course.prerequisite +'\n';
                }
                //可以不用重新生成text，可以直接再最后加？
                FCL.setText(text);
            }
        });

        //delete course
        Button deleteC = (Button) findViewById(R.id.student_Delete_Course);
        deleteC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent( Student_Operation.this,MainActivity.class);
                //startActivity(intent);
                //finish();

                AutoCompleteTextView CCV = (AutoCompleteTextView) findViewById(R.id.add_future_Choice);
                String CC =  CCV.getText().toString();

                //CC.trim();
                //判断是否在已有的课程列表中
                if (!Student_Operation.Student_Future_Courses.courseCodeList.contains(CC)){
                    Toast.makeText(Student_View_Future_activity.this, "The Course is not existing. Please re-enter.", Toast.LENGTH_SHORT).show();
                    return;
                }



                for (CourseList C: TestCourses){
                    //Toast.makeText(Student_View_Past_activity.this, C.courseCode+"\n"+CC, Toast.LENGTH_LONG).show();
                    if (C.courseCode.equals(CC)){
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        TestCourses.remove(C);
                        Student_Operation.Student_Future_Courses.courseCodeList.remove(CC);
                        //Toast.makeText(Student_View_Past_activity.this, C.courseCode, Toast.LENGTH_LONG).show();
                        break;
                    }
                }

                // TestCourses.remove(CC);
                String text="";
                //TestData Test = new Student.TestData(1);
                //TestCourses = Test.testCourse;

                for (CourseList Course: TestCourses) {
                    text = text + Course.courseCode +": "+ Course.offeringSession + '\n'+ "                 " +
                            Course.prerequisite +'\n';
                }
                FCL.setText(text);


            }

        });


        Button generate_timeline = (Button) findViewById(R.id.Generate_TimeLine);
        generate_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Student_View_Future_activity.this,Student_Generate_Timeline.class);
                startActivity(intent);
                //finish();
            }

        });

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