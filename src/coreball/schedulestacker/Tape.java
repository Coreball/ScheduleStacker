package coreball.schedulestacker;

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
	 * Finished schedule of 8 periods
	 */
	public static class FinishedSchedule {

		private ArrayList<FinishedCourse> finishedCourses;

		public FinishedSchedule() {
			finishedCourses = new ArrayList<>();
		}

		private FinishedCourse getCol(int col) {
			if(col >= finishedCourses.size()) { // TODO probably not needed
				return null;
			} else {
				return finishedCourses.get(col);
			}
		}

		public void addYearlong(String courseName, String teacher) {
			finishedCourses.add(new FinishedCourse(courseName, teacher));
		}

		public void addSemesters(String sem1, String teach1, String sem2, String teach2) {
			finishedCourses.add(new FinishedCourse(sem1, teach1, sem2, teach2));
		}

	}

	/**
	 * Finished Course stored in Tape
	 */
	public static class FinishedCourse {

		private String courseA;
		private String teachA;
		private String courseB;
		private String teachB;

		public FinishedCourse(String courseName, String teacher) {
			courseA = courseName;
			teachA = teacher;
		}

		public FinishedCourse(String sem1, String teach1, String sem2, String teach2) {
			courseA = sem1;
			this.teachA = teach1;
			courseB = sem2;
			this.teachB = teach2;
		}

		public String toString() {
			if(courseB == null) { // This is probably a better solution than the one with the useless interface
				return courseA + " - " + teachA;
			} else {
				return courseA + "/" + courseB + " - " + teachA + "/" + teachB;
			}
		}

	}

}
