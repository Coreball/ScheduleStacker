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
	private JTextField scheduleField;

	public ScheduleStackerGUI() {
		setContentPane(mainPanel);
		setTitle("ScheduleStacker");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(InstantiationException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		pack();
	}

	public JButton getFindFileButton() {
		return findFileButton;
	}

	public JTextField getScheduleField() {
		return scheduleField;
	}
}
