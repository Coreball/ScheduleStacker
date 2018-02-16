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
	 * Intended use: type(3).getSemestersFor("DS&A").sem(1).getTeachersFor("6").addTeacher("Nguyen-Reed");
	 * New: type(3).getCourse("DS&A").sem(1).getTeachersForPeriod("6").addTeacher("Nguyen-Reed");
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

		private HashMap<String, Course> courseHashMap;

		private CourseList() {
			courseHashMap = new HashMap<>();
		}

		public Course getCourse(String courseName) {
			if(!courseHashMap.containsKey(courseName)) { // Might remove later, not sure if necessary based on use
				addCourse(courseName);
			}
			return courseHashMap.get(courseName);
		}

		public void addCourse(String courseName) {
			courseHashMap.put(courseName, new Course()); // Warning kills the previous SemesterList if there was one
		}

		public Set<String> getAllCourseNames() {
			return courseHashMap.keySet();
		}

	}

	/**
	 * Represents a course!
	 */
	public class Course {

		private ArrayList<PeriodList> periodLists;

		private Course() {
			periodLists = new ArrayList<>();
			for(int i = 0; i < 3; i++) {
				periodLists.add(new PeriodList());
			}
		}

		public boolean isYearlong() {
			return !periodLists.get(0).isEmpty(); // If the yearlong list of periods is not empty then it's yearlong
		}

		public PeriodList sem(int semester) {
			return periodLists.get(semester);
		}

	}

	/**
	 * Represents all periods a particular school course is offered
	 */
	public class PeriodList {

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

		public ArrayList<String> getTeachers() {
			return teachers;
		}
	}

}
