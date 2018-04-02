package coreball.schedulestacker;

import coreball.schedulestacker.Glue.NamedCourse;

import javax.swing.*;

/**
 * GUI shell for ScheduleStacker
 * Created by Changyuan Lin on 22 Jan 2018.
 */
public class ScheduleStackerGUI extends JFrame {

	private JTabbedPane tabbedPane;
	private JPanel mainPanel;
	private JPanel homePanel;
	private JPanel inputPanel;
	private JPanel outputPanel;
	private JLabel homeTitle;
	private JLabel homeDesc;
	private JButton findFileButton;
	private JButton loadFileButton;
	private JTextField filePathField;
	private JButton processButton;
	private JList<NamedCourse> type1;
	private JList<NamedCourse> type2;
	private JList<NamedCourse> type3;
	private JList<NamedCourse> type4;
	private JList<NamedCourse> type5;
	private JList<NamedCourse> type6;
	private JList<NamedCourse> type7;
	private JList<NamedCourse> type8;
	private JList<NamedCourse>[] typeListArray;
	private JCheckBox checkPeriod1;
	private JCheckBox checkPeriod2;
	private JCheckBox checkPeriod3;
	private JCheckBox checkPeriod4;
	private JCheckBox checkPeriod5;
	private JCheckBox checkPeriod6;
	private JCheckBox checkPeriod7;
	private JCheckBox checkPeriod8;
	private JCheckBox[] checkPeriodArray;
	private JTable resultsTable;
	private JLabel scheduleNumberLbl;
	private JLabel period1Lbl_1;
	private JLabel period2Lbl_1;
	private JLabel period3Lbl_1;
	private JLabel period4Lbl_1;
	private JLabel period5Lbl_1;
	private JLabel period6Lbl_1;
	private JLabel period7Lbl_1;
	private JLabel period8Lbl_1;
	private JLabel period1Lbl_2;
	private JLabel period2Lbl_2;
	private JLabel period3Lbl_2;
	private JLabel period4Lbl_2;
	private JLabel period5Lbl_2;
	private JLabel period6Lbl_2;
	private JLabel period7Lbl_2;
	private JLabel period8Lbl_2;
	private JLabel[] periodLblArray;

	public ScheduleStackerGUI() {
		setContentPane(mainPanel);
		setTitle("ScheduleStacker");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
			JOptionPane.showMessageDialog(this, "Unable to set look and feel for some reason");
		}
		SwingUtilities.updateComponentTreeUI(this);
		setSize(1200, 750);
		makeCheckPeriodArray();
		makeTypeListArray(); // FORGOT THIS
		makePeriodLblArray(); // FORGOT THIS TOO ON A DIFFERENT DAY
	}

	/**
	 * Prepare array of period check boxes
	 */
	private void makeCheckPeriodArray() {
		checkPeriodArray = new JCheckBox[8];
		checkPeriodArray[0] = checkPeriod1;
		checkPeriodArray[1] = checkPeriod2;
		checkPeriodArray[2] = checkPeriod3;
		checkPeriodArray[3] = checkPeriod4;
		checkPeriodArray[4] = checkPeriod5;
		checkPeriodArray[5] = checkPeriod6;
		checkPeriodArray[6] = checkPeriod7;
		checkPeriodArray[7] = checkPeriod8;
	}

	/**
	 * Prepare array of the type lists where selectable courses are sorted
	 */
	private void makeTypeListArray() {
		typeListArray = new JList[8];
		typeListArray[0] = type1;
		typeListArray[1] = type2;
		typeListArray[2] = type3;
		typeListArray[3] = type4;
		typeListArray[4] = type5;
		typeListArray[5] = type6;
		typeListArray[6] = type7;
		typeListArray[7] = type8;
	}

	/**
	 * Prepare array of the period labels aka the detailed period descriptions
	 */
	private void makePeriodLblArray() {
		periodLblArray = new JLabel[17];
		periodLblArray[0] = scheduleNumberLbl;
		periodLblArray[1] = period1Lbl_1;
		periodLblArray[2] = period2Lbl_1;
		periodLblArray[3] = period3Lbl_1;
		periodLblArray[4] = period4Lbl_1;
		periodLblArray[5] = period5Lbl_1;
		periodLblArray[6] = period6Lbl_1;
		periodLblArray[7] = period7Lbl_1;
		periodLblArray[8] = period8Lbl_1;
		periodLblArray[9] = period1Lbl_2; // Perhaps store in 2D array instead?
		periodLblArray[10] = period2Lbl_2;
		periodLblArray[11] = period3Lbl_2;
		periodLblArray[12] = period4Lbl_2;
		periodLblArray[13] = period5Lbl_2;
		periodLblArray[14] = period6Lbl_2;
		periodLblArray[15] = period7Lbl_2;
		periodLblArray[16] = period8Lbl_2;
	}

	public JButton getFindFileButton() {
		return findFileButton;
	}

	public JButton getLoadFileButton() {
		return loadFileButton;
	}

	public JButton getProcessButton() {
		return processButton;
	}

	public JTextField getFilePathField() {
		return filePathField;
	}

	public JCheckBox[] getCheckPeriodArray() {
		return checkPeriodArray;
	}

	public JList<NamedCourse>[] getTypeListArray() {
		return typeListArray;
	}

	public JLabel[] getPeriodLblArray() {
		return periodLblArray;
	}

	public JTable getResultsTable() {
		return resultsTable;
	}
}
