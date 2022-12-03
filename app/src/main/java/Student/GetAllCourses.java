package Student;

import android.util.Log;

import com.example.myapplication.Student_Operation;
import com.example.myapplication.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GetAllCourses {

    public static HashSet<CourseList> CourseHashSet;

    private String DS = "https://b07project-943e2-default-rtdb.firebaseio.com/";

    public GetAllCourses(){
        CourseHashSet = new HashSet<CourseList>();
        DatabaseReference CLref = FirebaseDatabase.getInstance(DS).getReference("Courses_Test");

        // Read from the database
        CLref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {

                    CourseHashSet.clear();
                    Log.i("Get All Courses", "getting all courses");

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        //转换 prerequisites
                        String prepre = child.child("prerequisites").getValue(String.class);
                        List<String> prerequisite = Arrays.asList(prepre.split(","));

                        ArrayList<String> pre1=new ArrayList<String>();
                        //change list to arraylist
                        for(String s1:prerequisite){
                            pre1.add(s1);
                        }


                        //转换offeringSession
                        ArrayList<String> offeringSession = new ArrayList<String>();
                        String s=child.child("sessions").getValue(String.class);

                        if (s.length()==3){
                            if(s.charAt(0)==('1')) offeringSession.add("F");
                            if(s.charAt(1)=='1') offeringSession.add("W");
                            if(s.charAt(2)=='1') offeringSession.add("S");
                        }

                        CourseList value = new CourseList(child.child("courseCode").getValue(String.class),
                                child.child("courseName").getValue(String.class),offeringSession,pre1);

                        CourseHashSet.add(value);
                        //System.out.println(CourseHashSet);

                        Log.d("Read all courses list", "CourseCode is: " + value.courseCode);
                    }
                    //System.out.println(CourseHashSet);
                    //GetCoursesValue(CourseHashSet);
                    Student_Operation.CourseHashSet = CourseHashSet;

                } catch (Exception e) {
                    Log.w("warning", "error with persistent listener", e);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read courses", "Failed to read value.", error.toException());
            }
        });



    }

    public void GetCoursesValue(HashSet<CourseList> CHS){

        this.CourseHashSet = CHS;
        System.out.println(CourseHashSet);


    }




}
