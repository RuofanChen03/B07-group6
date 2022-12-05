package Student;

import java.util.ArrayList;
import java.util.List;

public class Timeline {
    public List<String> sessions;
    public List<ArrayList<String>> courses;

    public Timeline(List<String> sessions, List<ArrayList<String>> courses) {
        this.sessions = sessions;
        this.courses = courses;
    }
}

