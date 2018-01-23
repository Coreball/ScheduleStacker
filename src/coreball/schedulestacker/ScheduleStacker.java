package coreball.schedulestacker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Changyuan Lin on 22 Jan 2018.
 */
public class ScheduleStacker {

	private ScheduleStackerGUI gui;
	private JFileChooser fileChooser;
	private JButton findFileButton;
	private JTextField scheduleField;

	private Glue allClasses;

	public ScheduleStacker() {
		initComponents();
		initListeners();
	}

	public void showGUI() {
		gui.setVisible(true);
	}

	private void initComponents() {
		gui = new ScheduleStackerGUI();
		fileChooser = new JFileChooser();
		findFileButton = gui.getFindFileButton();
		scheduleField = gui.getScheduleField();
		gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void initListeners() {
		findFileButton.addActionListener(new findFileButtonListener());
	}

	private class findFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fileChooser.showOpenDialog(gui);
			scheduleField.setText(fileChooser.getSelectedFile().toString());
		}
	}

}
