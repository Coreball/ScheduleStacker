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

	public ScheduleStackerGUI() {
		setContentPane(mainPanel);
		setTitle("ScheduleStacker");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
			JOptionPane.showMessageDialog(this, "Unable to set look and feel for some reason");
		}
		SwingUtilities.updateComponentTreeUI(this);
		pack();
	}

	public JButton getFindFileButton() {
		return findFileButton;
	}

	public JButton getLoadFileButton() {
		return loadFileButton;
	}

	public JTextField getFilePathField() {
		return filePathField;
	}
}
