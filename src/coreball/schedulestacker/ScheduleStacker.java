package coreball.schedulestacker;

import coreball.schedulestacker.Glue.NamedCourse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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
	private JProgressBar progressBar;
	private JList<NamedCourse>[] typeListShells;
	private ArrayList<DefaultListModel<NamedCourse>> typeListInternals; // Hmm... do I really have to switch from Course to String and back?
	private JTable resultsTable;

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
		fileChooser = new JFileChooser(); // TODO Add filter for only .txt
		findFileButton = gui.getFindFileButton();
		loadFileButton = gui.getLoadFileButton();
		processButton = gui.getProcessButton();
		filePathField = gui.getFilePathField();
		progressBar = gui.getProgressBar();
		typeListShells = gui.getTypeListArray();
		typeListInternals = new ArrayList<>();
		initTypeListInternals();

		allClasses = new Glue();
		doneSchedules = new Tape();
		wantedCourses = new ArrayList<>();

		resultsTable = gui.getResultsTable();
		initResultsTable();
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
		Tape.FinishedSchedule testSchedule = new Tape.FinishedSchedule();
		// TODO delete this test code...
		testSchedule.addYearlong("yearlong", "mr. always");
		testSchedule.addSemesters("sem1", "mr. first", "sem2", "ms. second");
		doneSchedules.addFinishedSchedule(testSchedule);
	}

	/**
	 * Load a Master Schedule
	 * @param in file to be read
	 */
	private void loadFile(File in) {
		try {
			Scanner sc = new Scanner(in);
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
		if(!Character.isDigit(line.charAt(0))) { // Die if not a number, warn user something is wrong
			throw new IllegalArgumentException();
		}
		try {
			int type = Character.getNumericValue(line.charAt(0));
			String firstBit = line.substring(0, line.indexOf("(A-"));
			String secondBit = line.substring(line.lastIndexOf(')') + 1).trim(); // last index b/c (Teamed) is a course name
			int semester = 0;
			if(secondBit.charAt(0) == 'S') { // Set specific semester if isn't year-round
				semester = Character.getNumericValue(secondBit.charAt(1));
			}
			secondBit = secondBit.substring(secondBit.indexOf(' ') + 1); // Advance to teacher's last name
			String courseName = firstBit.substring(2, firstBit.lastIndexOf(" "));
			String period = firstBit.substring(firstBit.lastIndexOf(' ') + 1);
			String teacher = secondBit.substring(0, secondBit.indexOf(' ') - 1);
			allClasses.type(type).getNamedCourse(courseName).sem(semester).getTeachersForPeriod(period).addTeacher(courseName, semester, period, teacher);
		} catch(Exception e) {
			throw new IllegalArgumentException(); // TODO test this
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
			File file = new File(filePathField.getText());
			loadFile(file);
			updateTypeLists();
		}
	}

	private class processButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			progressBar.setIndeterminate(true);
			findOffPeriods();
			findWantedCourses();
			// Process stuff
			System.out.println(wantedCourses);
			progressBar.setIndeterminate(false);
		}
	}

}
