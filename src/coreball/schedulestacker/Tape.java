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

		private ArrayList<ArrayList<SpecificCourse>> finishedCourses; // List(Periods) of List(1 or 2 SpecificCourses)

		public FinishedSchedule() {
			finishedCourses = new ArrayList<>();
			for(int i = 1; i <=8; i++) {
				finishedCourses.add(new ArrayList<>());
			}
		}

		private String getCol(int col) {
			if(col >= finishedCourses.size()) { // TODO probably not needed
				return null;
			} else {
				if(finishedCourses.get(col).size() == 1) {
					SpecificCourse yearlong = finishedCourses.get(col).get(0);
					return yearlong.getCourseName() + " - " + yearlong.getTeacher();
				} else if(finishedCourses.get(col).size() == 2) {
					SpecificCourse s1 = finishedCourses.get(col).get(0);
					SpecificCourse s2 = finishedCourses.get(col).get(1);
					if(s1 != null && s2 != null) {
						return s1.getCourseName() + "/" + s2.getCourseName() + "; " + s1.getTeacher() + "/" + s2.getTeacher();
					} else if(s1 != null) {
						return s1.getCourseName() + "/" + "-" + "; " + s1.getTeacher() + "/" + "-";
					} else {
						return "-" + "/" + s2.getCourseName() + "; " + "-" + "/" + s2.getTeacher();
					} // TODO evaluate best way to convey that one semester has no classes
				} else {
					return "-";
				}
			}
		}

		public void addYearlong(int period, SpecificCourse course) { // For multiple periods call this twice
			finishedCourses.get(period).add(course);
		}

		public void addSemesters(int period, SpecificCourse s1, SpecificCourse s2) {
			finishedCourses.get(period).add(s1);
			finishedCourses.get(period).add(s2);
		}

	}

}
