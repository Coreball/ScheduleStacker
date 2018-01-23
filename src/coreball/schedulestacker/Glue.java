package coreball.schedulestacker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Coreball on 22 Jan 2018.
 */
public class Glue {

	// Primary data structure for storing values
	private ArrayList<ClassList> sticky;
	// It's actually ArrayList<HashMap<String, HashMap<String, ArrayList<String>>>> but that's hard to understand

	/*
	 * Intended use: type(1).getPeriodsFor("AP LANG").getTeachersFor("1");
	 */

	public Glue() {
		sticky = new ArrayList<ClassList>();
		for(int i = 0; i < 8; i++) {
			sticky.add(new ClassList());
		}
	}

	/**
	 * Get all classes of a certain type
	 * @param type the class type
	 * @return inner class list
	 */
	public ClassList type(int type) {
		return sticky.get(type - 1);
	}

	/**
	 * Class representing the different school classes of a type
	 */
	public class ClassList extends HashMap<String, PeriodList> {

		public PeriodList getPeriodsFor(String className) {
			if(!containsKey(className)) { // TODO Might remove later, not sure if necessary based on use
				addClass(className);
			}
			return get(className);
		}

		public void addClass(String className) {
			put(className, new PeriodList()); // Warning kills the previous PeriodList if there was one
		}

	}

	/**
	 * Represents all periods a particular school class is offered
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
	 * Represents all teachers teaching a particular school class
	 */
	public class TeacherList extends ArrayList<String> {
		public void addTeacher(String name) {
			if(!contains(name)) { // Probably won't ever be false
				add(name);
			}
		}
	}

}
