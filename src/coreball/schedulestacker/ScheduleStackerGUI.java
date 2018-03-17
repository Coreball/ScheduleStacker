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
	private JTable resultsTable;
	private JCheckBox[] checkPeriodArray;

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

	public JTable getResultsTable() {
		return resultsTable;
	}
}
