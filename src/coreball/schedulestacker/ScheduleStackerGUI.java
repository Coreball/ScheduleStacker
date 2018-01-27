package coreball.schedulestacker;

import javax.swing.*;

/**
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
	private JList list1;
	private JList list2;
	private JList list3;
	private JList list4;
	private JList list5;
	private JList list6;
	private JList list7;
	private JList list8;
	private JCheckBox checkPeriod1;
	private JCheckBox checkPeriod2;
	private JCheckBox checkPeriod3;
	private JCheckBox checkPeriod4;
	private JCheckBox checkPeriod5;
	private JCheckBox checkPeriod6;
	private JCheckBox checkPeriod7;
	private JCheckBox checkPeriod8;
	private JCheckBox[] periods;

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
	}

	private void makeCheckPeriodArray() {
		periods = new JCheckBox[8];
		periods[0] = checkPeriod1;
		periods[1] = checkPeriod2;
		periods[2] = checkPeriod3;
		periods[3] = checkPeriod4;
		periods[4] = checkPeriod5;
		periods[5] = checkPeriod6;
		periods[6] = checkPeriod7;
		periods[7] = checkPeriod8;
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

	public JCheckBox[] getCheckPeriods() {
		return periods;
	}
}
