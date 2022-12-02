package Student;

import java.util.ArrayList;

public class CourseList {

    public String courseCode;
    public String courseName;
    public ArrayList<String> offeringSession;
    public ArrayList<String> prerequisite;

    public CourseList(String code, String name, ArrayList<String> offeringSession, ArrayList<String> pre) {
        this.courseCode = code;
        this.courseName = name;
        this.offeringSession = offeringSession;
        this.prerequisite = pre;
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CourseList other = (CourseList) obj;
        return other.courseCode==courseCode;
    }

    @Override
    public String toString() {
        return "Course:" + courseCode + " Oferring Session:" + offeringSession +
                " Prerequisite:" + prerequisite;
    }

}