package coreball.schedulestacker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Contains the data structure for storing the courses
 * Created by Coreball on 22 Jan 2018.
 */
public class Glue {

	// Primary data structure for storing values
	private ArrayList<CourseList> sticky;

	/*
	 * Types of Courses
	 * Course Names
	 * Semester?
	 * Periods
	 * Teachers
	 * Intended use: type(3).getSemestersFor("DS&A").sem(1).getTeachersFor("6").addTeacher("Nguyen-Reed");
	 */

	public Glue() {
		sticky = new ArrayList<CourseList>();
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
	public class CourseList {

		private HashMap<String, SemesterList> semesterListHashMap;

		private CourseList() {
			semesterListHashMap = new HashMap<>();
		}

		public SemesterList getSemestersFor(String courseName) {
			if(!semesterListHashMap.containsKey(courseName)) { // Might remove later, not sure if necessary based on use
				addCourse(courseName);
			}
			return semesterListHashMap.get(courseName);
		}

		public void addCourse(String courseName) {
			semesterListHashMap.put(courseName, new SemesterList()); // Warning kills the previous SemesterList if there was one
		}

		public Set<String> getAllCourseNames() {
			return semesterListHashMap.keySet();
		}

	}

	/**
	 * Represents the semesters a course is available
	 * 0 - Year-Round
	 * 1 - Semester 1
	 * 2 - Semester 2
	 */
	public class SemesterList {

		private ArrayList<PeriodList> periodLists;

		private SemesterList() {
			periodLists = new ArrayList<>();
			for(int i = 0; i < 3; i++) {
				periodLists.add(new PeriodList());
			}
		}

		public PeriodList sem(int semester) {
			return periodLists.get(semester);
		}

	}

	/**
	 * Represents all periods a particular school course is offered
	 */
	public class PeriodList {

		private HashMap<String, TeacherList> teacherListHashMap;

		private PeriodList() {
			teacherListHashMap = new HashMap<>();
		}

		public TeacherList getTeachersFor(String period) {
			if(!teacherListHashMap.containsKey(period)) {
				teacherListHashMap.put(period, new TeacherList());
			}
			return teacherListHashMap.get(period);
		}

	}

	/**
	 * Represents all teachers teaching a particular school course
	 */
	public class TeacherList {

		private ArrayList<String> teachers;

		private TeacherList() {
			teachers = new ArrayList<>();
		}

		public void addTeacher(String name) {
			if(!teachers.contains(name)) { // Probably won't ever be false
				teachers.add(name);
			}
		}

	}

}
