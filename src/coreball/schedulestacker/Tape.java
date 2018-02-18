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

		public void addYearlong(String name, String teacher) {
			finishedCourses.add(new FinishedYearlong(name, teacher));
		}

		public void addSemesters(String sem1, String teachA, String sem2, String teachB) {
			finishedCourses.add(new FinishedSemester(sem1, teachA, sem2, teachB));
		}

	}

	/**
	 * Honestly idk what to do about this right now it isn't really good for being an interface
	 */
	public interface FinishedCourse {
		// empty??!? TODO
	}

	/**
	 * Finished yearlong course
	 */
	public static class FinishedYearlong implements FinishedCourse {

		private String name;
		private String teacher;

		public FinishedYearlong(String name, String teacher) {
			this.name = name;
			this.teacher = teacher;
		}

		public String toString() {
			return name + " - " + teacher;
		}

	}

	/**
	 * Finished course that has two semesters inside
	 */
	public static class FinishedSemester implements FinishedCourse {

		private String sem1;
		private String teachA;
		private String sem2;
		private String teachB;

		public FinishedSemester(String sem1, String teachA, String sem2, String teachB) {
			this.sem1 = sem1;
			this.teachA = teachA;
			this.sem2 = sem2;
			this.teachB = teachB;
		}

		public String toString() {
			return sem1 + "/" + sem2 + " - " + teachA + "/" + teachB;
		}

	}

}
