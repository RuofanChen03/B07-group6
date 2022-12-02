package Student;

public class FutureCourse extends Operation{
    public void addCourse(CourseList course) {
        newList.add(course);
    }

    public void deleteCourse(CourseList course) {

        newList.remove(course);
    }
}