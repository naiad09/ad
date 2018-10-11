package ad.frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import ad.Application;
import ad.domain.entities.Forum;
import ad.domain.entities.task.PaidStatus;
import ad.domain.entities.task.RunStatus;
import ad.domain.entities.task.Task;

public class EditTaskForm extends JPanel {
	private JTextField textFieldId;

	private Task task;
	private JButton clientField;
	private JFormattedTextField paidField;
	private JFormattedTextField giftField;
	private JFormattedTextField perDayField;
	private JComboBox<RunStatus> activeBox;
	private JComboBox<PaidStatus> paidBox;

	private JLabel titleLabel;

	public EditTaskForm(Task task) {
		this(task.getClient());
		this.task = task;

		titleLabel.setText("Edit task of " + task.getClient().getUrl());
		textFieldId.setText(task.getId().toString());

		paidField.setValue(task.getPaid());
		giftField.setValue(task.getGift());
		perDayField.setValue(task.getPerDay());

		activeBox.setSelectedItem(task.getRunStatus());
		paidBox.setSelectedItem(task.getPaidStatus());
	}

	/**
	 * @wbp.parser.constructor
	 */
	public EditTaskForm(Forum forum) {
		task = new Task();
		task.setClient(forum);

		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setMinimum(0);

		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 100, 150, 100, 150, 100, 150, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
		        Double.MIN_VALUE };
		setLayout(gbl_panel_1);

		titleLabel = MainFrame.buildHeader1("New task of " + forum.getUrl());
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 7;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(titleLabel, gbc_lblNewLabel);

		JLabel lblId = new JLabel("Task Id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 2;
		add(lblId, gbc_lblId);

		textFieldId = new JTextField();
		GridBagConstraints gbc_textFieldId = new GridBagConstraints();
		gbc_textFieldId.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldId.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldId.gridx = 1;
		gbc_textFieldId.gridy = 2;
		add(textFieldId, gbc_textFieldId);
		textFieldId.setColumns(10);
		textFieldId.setEnabled(false);

		JLabel lblClient = new JLabel("Client");
		GridBagConstraints gbc_lblClient = new GridBagConstraints();
		gbc_lblClient.insets = new Insets(0, 0, 5, 5);
		gbc_lblClient.gridx = 2;
		gbc_lblClient.gridy = 2;
		add(lblClient, gbc_lblClient);

		clientField = new JButton(task.getClient().getUrl());
		clientField.addActionListener(e -> Application.forumController().edit(forum));
		GridBagConstraints gbc_clientField = new GridBagConstraints();
		gbc_clientField.fill = GridBagConstraints.HORIZONTAL;
		gbc_clientField.gridwidth = 2;
		gbc_clientField.insets = new Insets(0, 0, 5, 5);
		gbc_clientField.gridx = 3;
		gbc_clientField.gridy = 2;
		add(clientField, gbc_clientField);

		JLabel lblTaskSettings = new JLabel("Task settings");
		lblTaskSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTaskSettings = new GridBagConstraints();
		gbc_lblTaskSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaskSettings.anchor = GridBagConstraints.WEST;
		gbc_lblTaskSettings.gridx = 1;
		gbc_lblTaskSettings.gridy = 3;
		add(lblTaskSettings, gbc_lblTaskSettings);

		JLabel lblPaidStatus = new JLabel("Paid status");
		GridBagConstraints gbc_lblPaidStatus = new GridBagConstraints();
		gbc_lblPaidStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaidStatus.gridx = 0;
		gbc_lblPaidStatus.gridy = 4;
		add(lblPaidStatus, gbc_lblPaidStatus);

		paidBox = new JComboBox<PaidStatus>();
		paidBox.setPreferredSize(new Dimension(100, 26));
		paidBox.setModel(new DefaultComboBoxModel<PaidStatus>(PaidStatus.values()));
		paidBox.setSelectedItem(PaidStatus.FREE);
		GridBagConstraints gbc_paidBox = new GridBagConstraints();
		gbc_paidBox.anchor = GridBagConstraints.WEST;
		gbc_paidBox.insets = new Insets(0, 0, 5, 5);
		gbc_paidBox.gridx = 1;
		gbc_paidBox.gridy = 4;
		add(paidBox, gbc_paidBox);

		JLabel lblTotal = new JLabel("Total");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 2;
		gbc_lblTotal.gridy = 4;
		add(lblTotal, gbc_lblTotal);

		paidField = new JFormattedTextField(numberFormatter);
		paidField.setValue(50);
		paidField.setColumns(10);
		GridBagConstraints gbc_paidField = new GridBagConstraints();
		gbc_paidField.insets = new Insets(0, 0, 5, 5);
		gbc_paidField.fill = GridBagConstraints.HORIZONTAL;
		gbc_paidField.gridx = 3;
		gbc_paidField.gridy = 4;
		add(paidField, gbc_paidField);

		JLabel lblActiveStatus = new JLabel("Active status");
		GridBagConstraints gbc_lblActiveStatus = new GridBagConstraints();
		gbc_lblActiveStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblActiveStatus.gridx = 0;
		gbc_lblActiveStatus.gridy = 5;
		add(lblActiveStatus, gbc_lblActiveStatus);

		JButton updateButton = new JButton("Update");
		updateButton.setPreferredSize(new Dimension(100, 28));
		updateButton.addActionListener(e -> Application.taskController().save(getTask()));

		activeBox = new JComboBox<RunStatus>();
		activeBox.setPreferredSize(new Dimension(100, 26));
		activeBox.setModel(new DefaultComboBoxModel<RunStatus>(RunStatus.values()));
		GridBagConstraints gbc_activeBox = new GridBagConstraints();
		gbc_activeBox.anchor = GridBagConstraints.WEST;
		gbc_activeBox.insets = new Insets(0, 0, 5, 5);
		gbc_activeBox.gridx = 1;
		gbc_activeBox.gridy = 5;
		add(activeBox, gbc_activeBox);

		JLabel lblGift = new JLabel("Gift");
		GridBagConstraints gbc_lblGift = new GridBagConstraints();
		gbc_lblGift.insets = new Insets(0, 0, 5, 5);
		gbc_lblGift.gridx = 2;
		gbc_lblGift.gridy = 5;
		add(lblGift, gbc_lblGift);

		giftField = new JFormattedTextField(numberFormatter);
		giftField.setValue(0);
		giftField.setColumns(10);
		GridBagConstraints gbc_giftField = new GridBagConstraints();
		gbc_giftField.insets = new Insets(0, 0, 5, 5);
		gbc_giftField.fill = GridBagConstraints.HORIZONTAL;
		gbc_giftField.gridx = 3;
		gbc_giftField.gridy = 5;
		add(giftField, gbc_giftField);

		JLabel lblPerDay = new JLabel("Per day");
		GridBagConstraints gbc_lblPerDay = new GridBagConstraints();
		gbc_lblPerDay.insets = new Insets(0, 0, 5, 5);
		gbc_lblPerDay.gridx = 4;
		gbc_lblPerDay.gridy = 5;
		add(lblPerDay, gbc_lblPerDay);

		perDayField = new JFormattedTextField(numberFormatter);
		perDayField.setPreferredSize(new Dimension(150, 28));
		perDayField.setValue(50);
		perDayField.setColumns(10);
		GridBagConstraints gbc_perDayField = new GridBagConstraints();
		gbc_perDayField.anchor = GridBagConstraints.WEST;
		gbc_perDayField.insets = new Insets(0, 0, 5, 0);
		gbc_perDayField.gridx = 5;
		gbc_perDayField.gridy = 5;
		add(perDayField, gbc_perDayField);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 6;
		add(updateButton, gbc_button);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(100, 28));
		cancelButton.addActionListener(e -> Application.taskController().displayAll());
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.anchor = GridBagConstraints.WEST;
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 6;
		add(cancelButton, gbc_button_1);
	}

	private Task getTask() {

		task.setPaid((Integer) paidField.getValue());
		task.setGift((Integer) giftField.getValue());
		task.setPerDay((Integer) (perDayField.getValue()));

		task.setRunStatus((RunStatus) activeBox.getSelectedItem());
		task.setPaidStatus((PaidStatus) paidBox.getSelectedItem());

		return task;
	}
}
