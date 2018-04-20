package coreball.schedulestacker;

import coreball.schedulestacker.Glue.NamedCourse;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Model holding finished schedules
 * Created by Changyuan Lin on 17 Feb 2018.
 */
public class Tape extends AbstractTableModel {

	// Important
	private ArrayList<FinishedSchedule> finishedSchedules;

	/*
	 * Planning or something
	 * make FinishedSchedule called thing
	 * do some math to find what can add
	 * thing.addYearlong("course", "mr. teacher")
	 * thing.addSemesters("sem1", "mr. first", "sem2", "ms. second")
	 * repeat.
	 * addFinishedSchedule(thing);
	 */

	public Tape() {
		System.out.println("Initializing Tape");
		finishedSchedules = new ArrayList<>();
	}

	@Override
	public int getRowCount() {
		return finishedSchedules.size();
	}

	@Override
	public int getColumnCount() {
		return 8; // EIGHT PERIODS DO YOU HEAR ME
	}

	@Override
	public String getColumnName(int index) {
		return "" + (index + 1); // 1, 2, 3, ... 8
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return finishedSchedules.get(rowIndex).getCol(columnIndex);
	}

	/**
	 * Add a finished schedule
	 * @param schedule schedule to be added
	 */
	public void addFinishedSchedule(FinishedSchedule schedule) {
		finishedSchedules.add(schedule);
	}

	/**
	 * Delete everything
	 */
	public void reset() {
		finishedSchedules = new ArrayList<>();
	}

	/**
	 * Get a specific finished schedule
	 * @param row which FinishedSchedule to fetch
	 * @return the finished schedule
	 */
	public FinishedSchedule getFinishedSchedule(int row) {
		return finishedSchedules.get(row);
	}

	/**
	 * Finished schedule of 8 periods
	 */
	public static class FinishedSchedule {

		private ArrayList<ArrayList<SpecificCourse>> finishedCourses; // List(Periods) of List(1 or 2 SpecificCourses)

		public FinishedSchedule() {
			finishedCourses = new ArrayList<>();
			for(int i = 1; i <= 8; i++) {
				finishedCourses.add(new ArrayList<>());
			}
		}

		public FinishedSchedule(FinishedSchedule copy) {
			finishedCourses = new ArrayList<>();
			for(int i = 1; i <= 8; i++) {
				finishedCourses.add(new ArrayList<>());
				for(SpecificCourse s : copy.finishedCourses.get(i - 1)) {
					finishedCourses.get(i - 1).add(s);
				}
			}
		}

		/**
		 * Get the column, for use in the table view
		 * @param col the column
		 * @return string representing course(s) that period
		 */
		private String getCol(int col) {
			if(col >= finishedCourses.size()) { // Probably not needed but safeguards are cool
				return null;
			} else {
				if(finishedCourses.get(col).size() == 1) {
					SpecificCourse yearlong = finishedCourses.get(col).get(0);
					return yearlong.getCourseName() + " - " + yearlong.getTeacherLast();
				} else if(finishedCourses.get(col).size() == 2) {
					SpecificCourse s1 = finishedCourses.get(col).get(0);
					SpecificCourse s2 = finishedCourses.get(col).get(1);
					if(s1 != null && s2 != null) {
						return s1.getCourseName() + "/" + s2.getCourseName() + "; " + s1.getTeacherLast() + "/" + s2.getTeacherLast();
					} else if(s1 != null) {
						return s1.getCourseName() + "/" + "-" + "; " + s1.getTeacherLast() + "/" + "-";
					} else {
						return "-" + "/" + s2.getCourseName() + "; " + "-" + "/" + s2.getTeacherLast();
					} // TODO evaluate best way to convey that one semester has no classes
				} else {
					return "-";
				}
			}
		}

		/**
		 * Get the raw list of specific courses in a period
		 * @param period the period
		 * @return a list containing either 0 (off), 1 (year), or 2 (semesters) specific courses
		 */
		public ArrayList<SpecificCourse> getSpecificCoursesForPeriod(int period) {
			return finishedCourses.get(period - 1);
		}

		/**
		 * See if this course is already contained inside this schedule
		 * @param namedCourse course
		 * @return true if already contained
		 */
		public boolean alreadyContains(NamedCourse namedCourse) {
			for(ArrayList<SpecificCourse> periods : finishedCourses) {
				for(SpecificCourse course : periods) {
					if(course != null && course.getCourseName().equals(namedCourse.toString())) {
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * Returns if this schedule contains this teacher in any period
		 * @param teacher teacher's last name
		 * @return true if has teacher
		 */
		public boolean hasTeacher(String teacher) {
			for(ArrayList<SpecificCourse> periods : finishedCourses) {
				for(SpecificCourse course : periods) {
					if(course != null && course.getTeacherLast().equalsIgnoreCase(teacher)) {
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * Returns if this schedule contains all of the teachers given in any period
		 * @param teachers array of teachers
		 * @return true if all teachers appear
		 */
		public boolean hasTeachers(String[] teachers) {
			for(String teacher : teachers) {
				if(!hasTeacher(teacher)) {
					return false;
				}
			}
			return true;
		}

		/**
		 * Returns if this schedule does not contain any of the teachers given
		 * @param teachers array of teachers
		 * @return true if no teacher appears
		 */
		public boolean noHasTeachers(String[] teachers) {
			for(String teacher : teachers) {
				if(hasTeacher(teacher)) {
					return false;
				}
			}
			return true;
		}

		/**
		 * Add a yearlong course
		 * @param period period to add
		 * @param course specific course to add
		 */
		public void addYearlong(int period, SpecificCourse course) { // For multiple periods call this twice
			finishedCourses.get(period - 1).add(course);
		}

		/**
		 * Add two semester-long courses
		 * @param period period to add
		 * @param s1 first semester
		 * @param s2 second semester
		 */
		public void addSemesters(int period, SpecificCourse s1, SpecificCourse s2) {
			finishedCourses.get(period - 1).add(s1);
			finishedCourses.get(period - 1).add(s2);
		}

		public String toString() {
			String entireSchedule = "";
			for(int i = 0; i < finishedCourses.size(); i++) {
				entireSchedule += (i == 0 ? "" : "\t") + getCol(i);
			}
			return entireSchedule;
		}

	}

}
