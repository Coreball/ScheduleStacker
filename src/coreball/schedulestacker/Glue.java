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
	 * Intended use: type(1).getPeriodsFor("AP LANG").getTeachersFor("1");
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
	public class CourseList extends HashMap<String, PeriodList> {

		public PeriodList getPeriodsFor(String courseName) {
			if(!containsKey(courseName)) { // TODO Might remove later, not sure if necessary based on use
				addClass(courseName);
			}
			return get(courseName);
		}

		public void addClass(String courseName) {
			put(courseName, new PeriodList()); // Warning kills the previous PeriodList if there was one
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
