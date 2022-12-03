package Student;

import java.util.HashSet;

public class FinalAllCourses {
    public static HashSet<CourseList> CourseHashSet;

    public FinalAllCourses(HashSet<CourseList> CHS){
        System.out.println("CHS"+CHS);
        CourseHashSet = new HashSet<CourseList>();
        this.CourseHashSet = CHS;
        System.out.println("fianl"+CourseHashSet);


    }

}
