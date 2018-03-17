package coreball.schedulestacker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Contains the data structure for storing the courses
 * Created by Changyuan Lin on 22 Jan 2018.
 */
public class Glue {

	// Primary data structure for storing values
	private ArrayList<CourseList> sticky;

	/*
	 * Types of Courses
	 * All Course Names
	 * Course
	 * Semesters
	 * Periods
	 * Teachers
	 * Use: type(3).getNamedCourse("DS&A").sem(1).getTeachersForPeriod("6").addTeacher("Nguyen-Reed");
	 */

	public Glue() {
		sticky = new ArrayList<>();
		for(int i = 0; i < 8; i++) {
			sticky.add(new CourseList());
		}
	}

	/**
	 * Get all classes of a certain type
	 * @param type the class type
	 * @return inner class list
	 */
	public CourseList type(int type) {
		return sticky.get(type - 1);
	}

	/**
	 * Class representing the different school course of a type
	 */
	public static class CourseList {

		private HashMap<String, NamedCourse> courseHashMap;

		private CourseList() {
			courseHashMap = new HashMap<>();
		}

		public NamedCourse getNamedCourse(String courseName) {
			if(!courseHashMap.containsKey(courseName)) { // Might remove later, not sure if necessary based on use
				addCourse(courseName);
			}
			return courseHashMap.get(courseName);
		}

		public void addCourse(String courseName) {
			courseHashMap.put(courseName, new NamedCourse(courseName)); // Warning kills the previous SemesterList if there was one
		}

		public Set<String> getAllCourseNames() {
			return courseHashMap.keySet();
		}

	}

	/**
	 * Represents a named course (not the specific instance of the course)
	 */
	public static class NamedCourse {

		private ArrayList<PeriodList> periodLists;
		private String originalName;

		private NamedCourse(String originalName) {
			periodLists = new ArrayList<>();
			for(int i = 0; i < 3; i++) {
				periodLists.add(new PeriodList());
			}
			this.originalName = originalName;
		}

		public boolean isYearlong() {
			return !periodLists.get(0).isEmpty(); // If the yearlong list of periods is not empty then it's yearlong
		}

		public PeriodList sem(int semester) {
			return periodLists.get(semester);
		}

		public String toString() {
			return originalName;
		}

	}

	/**
	 * Represents all periods a particular school course is offered
	 */
	public static class PeriodList {

		private HashMap<String, TeacherList> teacherListHashMap; // STRING REPRESENTS THE PERIOD.

		private PeriodList() {
			teacherListHashMap = new HashMap<>();
		}

		public TeacherList getTeachersForPeriod(String period) {
			if(!teacherListHashMap.containsKey(period)) {
				teacherListHashMap.put(period, new TeacherList());
			}
			return teacherListHashMap.get(period);
		}

		public boolean isEmpty() {
			return teacherListHashMap.isEmpty();
		}

	}

	/**
	 * Represents all teachers teaching a particular school course
	 */
	public static class TeacherList {

		private ArrayList<SpecificCourse> specificCourses;

		private TeacherList() {
			specificCourses = new ArrayList<>();
		}

		/**
		 * Adds specific course with specific teacher if needed
		 * @param courseName name of course
		 * @param teacher the teacher
		 */
		public void addTeacher(String courseName, int semester, String period, String teacher) {
			for(SpecificCourse specificCourse : specificCourses) {
				if(specificCourse.getTeacher().equals(teacher)) {
					return;
				}
			}
			specificCourses.add(new SpecificCourse(courseName, semester, period, teacher));
		}

		public ArrayList<SpecificCourse> getSpecificCourses() {
			return specificCourses;
		}

	}

}
