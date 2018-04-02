package coreball.schedulestacker;

import coreball.schedulestacker.Glue.NamedCourse;
import coreball.schedulestacker.Tape.FinishedSchedule;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Main ScheduleStacker class. Controls most things
 * Created by Changyuan Lin on 22 Jan 2018.
 */
public class ScheduleStacker {

	// Some things
	private ScheduleStackerGUI gui;
	private JFileChooser fileChooser;
	private JButton findFileButton;
	private JButton loadFileButton;
	private JButton processButton;
	private JTextField filePathField;
	private JList<NamedCourse>[] typeListShells;
	private ArrayList<DefaultListModel<NamedCourse>> typeListInternals;
	private JTable resultsTable;
	private JLabel[] periodDescriptions;

	// Computational data structures
	private Glue allClasses;
	private Tape doneSchedules;
	private ArrayList<NamedCourse> wantedCourses;

	// User input
	private boolean[] offPeriodsDesired;

	public ScheduleStacker() {
		initComponents();
		initListeners();
	}

	public void showGUI() {
		gui.setVisible(true);
	}

	/**
	 * Initialize components of the ScheduleStacker
	 */
	private void initComponents() {
		gui = new ScheduleStackerGUI();
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Master Schedule CSV (*.csv)", "csv"));
		findFileButton = gui.getFindFileButton();
		loadFileButton = gui.getLoadFileButton();
		processButton = gui.getProcessButton();
		filePathField = gui.getFilePathField();
		typeListShells = gui.getTypeListArray();
		typeListInternals = new ArrayList<>();
		initTypeListInternals();

		doneSchedules = new Tape();
		wantedCourses = new ArrayList<>();

		resultsTable = gui.getResultsTable();
		initResultsTable();
		periodDescriptions = gui.getPeriodLblArray();
		gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Add the listeners for buttons used in ScheduleStacker
	 */
	private void initListeners() {
		findFileButton.addActionListener(new findFileButtonListener());
		loadFileButton.addActionListener(new loadFileButtonListener());
		processButton.addActionListener(new processButtonListener());
	}

	/**
	 * Init the list models for the JLists and assign them
	 */
	private void initTypeListInternals() {
		for(int i = 0; i < typeListShells.length; i++) {
			typeListInternals.add(new DefaultListModel<>());
			typeListShells[i].setModel(typeListInternals.get(i));
		}
	}

	/**
	 * Set the model for the table and stuff
	 */
	private void initResultsTable() {
		resultsTable.setModel(doneSchedules);
		resultsTable.getTableHeader().setReorderingAllowed(false);
		resultsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsTable.getSelectionModel().addListSelectionListener(new resultsTableListener());
	}

	/**
	 * Load a Master Schedule
	 * @param in file to be read
	 */
	private void loadFile(String in) {
		if(in.isEmpty()) { // Show an error if no file is selected
			JOptionPane.showMessageDialog(gui, "Select a file first!!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			Scanner sc = new Scanner(new File(in));
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(!line.isEmpty() && line.charAt(0) != '#') { // Ignore pound signs
					System.out.println(line);
					lineToGlue(line);
				}
			}
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(gui, "File not found!!", "Error", JOptionPane.ERROR_MESSAGE);
		} catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(gui, "File formatted incorrectly!!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Process a line in MS and add to allClasses
	 * @param line singular line ripped from Master Schedule
	 */
	private void lineToGlue(String line) {
		String[] parts = line.split(","); // MUCH EASIER THAN PROCESSING EVERY TIME
		try {
			int type = Integer.parseInt(parts[0]);
			String courseName = parts[1];
			String period = parts[2];
			int semester;
			switch(parts[3]) {
				case "S1": semester = 1; break;
				case "S2": semester = 2; break;
				default: semester = 0;
			}
			String teacherLast = parts[4];
			String teacherFirst = parts[5];
			String room = parts[6];
			allClasses.type(type).getNamedCourse(courseName).sem(semester).getTeachersForPeriod(period)
					.addTeacher(courseName, semester, period, teacherLast, teacherFirst, room);
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Update the list models controlling the JLists with new data from the file
	 */
	private void updateTypeLists() {
		for(int i = 1; i <= 8; i++) {
			DefaultListModel<NamedCourse> currentList = typeListInternals.get(i - 1);
			currentList.clear();
			ArrayList<String> temporary = new ArrayList<>(allClasses.type(i).getAllCourseNames());
			Collections.sort(temporary); // just dump in temp array to sort. better solution?
			for(String str : temporary) {
				currentList.addElement(allClasses.type(i).getNamedCourse(str));
			}
		}
	}

	/**
	 * Find what off periods the user wants
	 */
	private void findOffPeriods() {
		JCheckBox[] periods = gui.getCheckPeriodArray();
		offPeriodsDesired = new boolean[8];
		for(int i = 0; i < 8; i++) {
			offPeriodsDesired[i] = periods[i].isSelected();
		}
	}

	/**
	 * Find if user wants specific off period
	 * @param period desired
	 * @return if wants that off
	 */
	private boolean wantsOffPeriod(int period) {
		return offPeriodsDesired[period - 1];
	}

	/**
	 * Find the courses the user has selected and add them to an array
	 */
	private void findWantedCourses() {
		wantedCourses.clear();
		for(int type = 1; type <= 8; type++) {
			JList<NamedCourse> typeList = typeListShells[type - 1];
			wantedCourses.addAll(typeList.getSelectedValuesList()); // I simplified this!
		}
	}

	/**
	 * Compute the schedules and place them in doneSchedules
	 */
	private void computeSchedules() {
		doneSchedules.reset(); // Clean up table, reset() so we don't need to initialize table again
		computeForPeriod(new FinishedSchedule(), 1); // Ignite the fire.
	}

	/**
	 * Solve for the next period RECURSIVELY
	 * @param prevSchedule current schedule worked on and being added to
	 * @param period period we're looking to put a specific course in
	 */
	private void computeForPeriod(FinishedSchedule prevSchedule, int period) {

		// If schedule is done add
		if(period > 8) {
			// Check if all conditions satisfied? add to results.
			for(NamedCourse namedCourse : wantedCourses) {
				if(!prevSchedule.alreadyContains(namedCourse)) {
					return;
				}
			}
			doneSchedules.addFinishedSchedule(prevSchedule);
			return; // Do not do more computation for this specific schedule
		}

		// If we want this off period off then go directly to next period
		if(wantsOffPeriod(period)) {
			computeForPeriod(new FinishedSchedule(prevSchedule), period + 1);
			return; // Don't do the other things below
		}

		// Have this as a wildcard off period (off not required by user but user needs an off)
		computeForPeriod(new FinishedSchedule(prevSchedule), period + 1); // Does do the other things below

		// Worry about empty first semester ie if first semester could be an off period but not second
		solveSecondSemester(prevSchedule, period, null);

		// Loop through courses we want
		for(NamedCourse namedCourse : wantedCourses) {

			// If we don't have this namedCourse already
			if(!prevSchedule.alreadyContains(namedCourse)) {

				// If it's a yearlong course
				if(namedCourse.isYearlong()) {
					// Check if it's 1 period long and available this period (specific courses available > 0)
					if(namedCourse.sem(0).getTeachersForPeriod("" + period).getSpecificCourses().size() > 0) {
						// Go through all teachers' courses
						for(SpecificCourse specificCourse : namedCourse.sem(0).getTeachersForPeriod("" + period).getSpecificCourses()) {
							FinishedSchedule workingSchedule = new FinishedSchedule(prevSchedule);
							workingSchedule.addYearlong(period, specificCourse);
							computeForPeriod(workingSchedule, period + 1);
						}
					}
					// Else if it's a multiperiod course (hack for getting correct String for hashmap)
					else if(namedCourse.sem(0).getTeachersForPeriod(period + "-" + (period + 1)).getSpecificCourses().size() > 0
							&& !wantsOffPeriod(period + 1)) { // AND doesn't NEED off period that double period extends into
						// Go through all teachers' courses
						for(SpecificCourse specificCourse : namedCourse.sem(0).getTeachersForPeriod(period + "-" + (period + 1)).getSpecificCourses()) {
							FinishedSchedule workingSchedule = new FinishedSchedule(prevSchedule);
							workingSchedule.addYearlong(period, specificCourse);
							workingSchedule.addYearlong(period + 1, specificCourse); // SHOULDN'T ERROR since courses can be 7-8 (no more) at max
							computeForPeriod(workingSchedule, period + 2); // We check if period > 8 so this is ok, I think
						}
					}
				}

				// If it's a semester course
				else {
					// Test if it's available 1st semester this period (specific courses available > 0)
					if(namedCourse.sem(1).getTeachersForPeriod("" + period).getSpecificCourses().size() > 0) {
						// Go through all teachers' courses
						for(SpecificCourse s1 : namedCourse.sem(1).getTeachersForPeriod("" + period).getSpecificCourses()) {
							// Call to figure out second semester
							solveSecondSemester(prevSchedule, period, s1); // deals with recursion calls too
						}
					}
				}
			}

		}

	}

	/**
	 * Solve for the second semester, take in the first.
	 * @param prevSchedule current schedule worked on
	 * @param period wat period??
	 * @param s1 the first course we've already chosen
	 */
	private void solveSecondSemester(FinishedSchedule prevSchedule, int period, SpecificCourse s1) {
		// Embrace the possibility that this could be null ONLY IF s1 != null
		if(s1 != null) {
			FinishedSchedule workingSchedule = new FinishedSchedule(prevSchedule);
			workingSchedule.addSemesters(period, s1, null);
			computeForPeriod(workingSchedule, period + 1);
		}

		// Loop through courses
		for(NamedCourse namedCourse : wantedCourses) {
			// If we don't already have it AND it's available s2 AND it's not s1
			if(!prevSchedule.alreadyContains(namedCourse) && namedCourse.sem(2).getTeachersForPeriod("" + period).getSpecificCourses().size() > 0
					&& (s1 == null || /* in case s1 is null*/ !s1.getCourseName().equals(namedCourse.toString()))) {
				// Go through all teachers' second semester courses
				for(SpecificCourse s2 : namedCourse.sem(2).getTeachersForPeriod("" + period).getSpecificCourses()) {
					FinishedSchedule workingSchedule = new FinishedSchedule(prevSchedule);
					workingSchedule.addSemesters(period, s1, s2);
					computeForPeriod(workingSchedule, period + 1);
				}
			}
		}
	}

	private class findFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int reply = fileChooser.showOpenDialog(gui);
			if(reply == JFileChooser.APPROVE_OPTION) {
				filePathField.setText(fileChooser.getSelectedFile().toString());
			}
		}
	}

	private class loadFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			allClasses = new Glue(); // Clear out allClasses when loading a new file
			loadFile(filePathField.getText());
			updateTypeLists();
		}
	}

	private class processButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			findOffPeriods();
			findWantedCourses();
			System.out.println(wantedCourses);
			System.out.println(wantedCourses.size());
			if(wantedCourses.size() > 0) {
				// Process stuff
				computeSchedules();
				doneSchedules.fireTableDataChanged(); // This gives the table a usable scroll bar w/o resizing the window to trigger appearance
				if(doneSchedules.getRowCount() > 0) {
					JOptionPane.showMessageDialog(gui, doneSchedules.getRowCount() + " Schedules Found", "Done", JOptionPane.INFORMATION_MESSAGE);
					resultsTable.setRowSelectionInterval(0, 0); // Auto-select first schedule
				} else {
					JOptionPane.showMessageDialog(gui, "No schedules found!!", "Done", JOptionPane.INFORMATION_MESSAGE);
					periodDescriptions[0].setText("0/0");
					for(int period = 1; period < periodDescriptions.length; period++) {
						periodDescriptions[period].setText(""); // Clear up last run details
					}
				}
			} else {
				JOptionPane.showMessageDialog(gui, "No courses selected!!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			gui.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class resultsTableListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(resultsTable.getSelectedRow() < 0) { // Don't error when generating new schedules again and table is empty
				return;
			}
			FinishedSchedule selected = doneSchedules.getFinishedSchedule(resultsTable.getSelectedRow());
			periodDescriptions[0].setText((resultsTable.getSelectedRow() + 1) + "/" + doneSchedules.getRowCount());

			for(int period = 1; period <= 8; period++) { // Update the descriptions for each period
				ArrayList<SpecificCourse> thisPeriod = selected.getSpecificCoursesForPeriod(period);
				if(thisPeriod.size() == 1) {
					SpecificCourse yearlong = thisPeriod.get(0);
					periodDescriptions[period].setText("YR:   " + yearlong.getCourseName() + "  -  " + yearlong.getTeacherLast()
							+ ", " + yearlong.getTeacherFirst() + "  -  " + yearlong.getRoom());
					periodDescriptions[period + 8].setText(""); // MUST CLEAR
				} else if(thisPeriod.size() == 2) {
					SpecificCourse s1 = thisPeriod.get(0);
					SpecificCourse s2 = thisPeriod.get(1);
					if(s1 != null && s2 != null) {
						periodDescriptions[period].setText("S1:   " + s1.getCourseName() + "  -  " + s1.getTeacherLast()
								+ ", " + s1.getTeacherFirst() + "  -  " + s1.getRoom());
						periodDescriptions[period + 8].setText("S2:   " + s2.getCourseName() + s2.getTeacherLast()
								+ ", " + s2.getTeacherFirst() + "  -  " + s2.getRoom());
					} else if(s1 != null) {
						periodDescriptions[period].setText("S1:   " + s1.getCourseName() + "  -  " + s1.getTeacherLast()
								+ ", " + s1.getTeacherFirst() + "  -  " + s1.getRoom());
						periodDescriptions[period + 8].setText("S2:   OFF");
					} else {
						periodDescriptions[period].setText("S1:   OFF");
						periodDescriptions[period + 8].setText("S2:   " + s2.getCourseName() + "  -  " + s2.getTeacherLast()
								+ ", " + s2.getTeacherFirst() + "  -  " + s2.getRoom());
					}
				} else {
					periodDescriptions[period].setText("YR:   OFF");
				}
			}
		}
	}

}
