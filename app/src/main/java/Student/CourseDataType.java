package Student;

import java.util.*;

public class CourseDataType {
    public HashSet<CourseList> CourseHAshSet;
    public ArrayList<String> courseCodeList;

    public CourseDataType() {
        CourseHAshSet = new HashSet<CourseList>();
        courseCodeList = new ArrayList<String>();
    }
}