package project_student;

import java.util.ArrayList;
import java.util.List;

public class Timeline {
	List<String> sessions;
	List<ArrayList<String>> courses;
	
	public Timeline(List<String> sessions, List<ArrayList<String>> courses) {
		this.sessions = sessions;
		this.courses = courses;
	}
}
