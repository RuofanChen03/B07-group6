package project_student;

import java.util.ArrayList;

public class CourseList {
	
	String courseCode;
	String courseName;
	ArrayList<String> offeringSession;
	ArrayList<String> prerequisite;
	
	public CourseList(String code, String name, ArrayList<String> offeringSession, ArrayList<String> pre) {
		this.courseCode = code;
		this.courseName = name;
		this.offeringSession = offeringSession;
		this.prerequisite = pre;
	}
	
	@Override
	public int hashCode() {
		if (courseCode.charAt(3)=='A') {
			return 100+Integer.parseInt(courseCode.substring(5));
		}
		if (courseCode.charAt(3)=='B') {
			return 200+Integer.parseInt(courseCode.substring(5));
		}
		if (courseCode.charAt(3)=='C') {
			return 300+Integer.parseInt(courseCode.substring(5));
		}
		else {
			return 400+Integer.parseInt(courseCode.substring(5));
		}
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

