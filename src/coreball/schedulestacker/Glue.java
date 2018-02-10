package coreball.schedulestacker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains the data structure for storing the courses
 * Created by Coreball on 22 Jan 2018.
 */
public class Glue {

	// Primary data structure for storing values
	private ArrayList<CourseList> sticky;
	// It's actually ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> but that's hard to understand

	/*
	 * Types of Courses
	 * Course Names
	 * Semester?
	 * Periods
	 * Teachers
	 */

	/*
	 * Intended use: type(1).getPeriodsFor("AP LANG").getTeachersFor("1");
	 * Intended use: type(3).getSemestersFor("DS&A").sem(1).getTeachersFor("6");
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
	public class CourseList extends HashMap<String, SemesterList> {

		public SemesterList getSemestersFor(String courseName) {
			if(!containsKey(courseName)) { // TODO Might remove later, not sure if necessary based on use
				addClass(courseName);
			}
			return get(courseName);
		}

		public void addClass(String courseName) {
			put(courseName, new SemesterList()); // Warning kills the previous SemesterList if there was one
		}

	}

	/**
	 * Represents the semesters a class is available as an ArrayList
	 * 0 - Year-Round
	 * 1 - Semester 1
	 * 2 - Semester 2
	 */
	public class SemesterList extends ArrayList<PeriodList> {
		public SemesterList() {
			for(int i = 0; i < 3; i++) {
				add(new PeriodList());
			}
		}
		public PeriodList sem(int semester) {
			return get(semester);
		}
	}

	/**
	 * Represents all periods a particular school course is offered
	 */
	public class PeriodList extends HashMap<String, TeacherList> {
		public TeacherList getTeachersFor(String period) {
			if(!containsKey(period)) {
				put(period, new TeacherList());
			}
			return get(period);
		}
	}

	/**
	 * Represents all teachers teaching a particular school course
	 */
	public class TeacherList extends ArrayList<String> {
		public void addTeacher(String name) {
			if(!contains(name)) { // Probably won't ever be false
				add(name);
			}
		}
	}

}
