package Student;

import java.util.HashSet;

public abstract class Operation {
    HashSet<CourseList> newList;

    public abstract void addCourse(CourseList course);

    public abstract void deleteCourse(CourseList course);

    public void viewCourses () {
        for (CourseList c : newList) {
            System.out.println(c);
        }
    }
}