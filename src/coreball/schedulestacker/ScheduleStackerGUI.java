package coreball.schedulestacker;

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
	private JList<String> type1;
	private JList<String> type2;
	private JList<String> type3;
	private JList<String> type4;
	private JList<String> type5;
	private JList<String> type6;
	private JList<String> type7;
	private JList<String> type8;
	private JList<String>[] typeListArray;
	private JCheckBox checkPeriod1;
	private JCheckBox checkPeriod2;
	private JCheckBox checkPeriod3;
	private JCheckBox checkPeriod4;
	private JCheckBox checkPeriod5;
	private JCheckBox checkPeriod6;
	private JCheckBox checkPeriod7;
	private JCheckBox checkPeriod8;
	private JProgressBar progressBar1;
	private JTable table1;
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

	public JList<String>[] getTypeListArray() {
		return typeListArray;
	}

}
