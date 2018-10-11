package ad.frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.time.LocalDate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import ad.Application;
import ad.domain.entities.BannerTask;
import ad.domain.entities.Forum;
import ad.domain.entities.task.PaidStatus;
import ad.domain.entities.task.RunStatus;
import ad.frames.components.JDatePicker;

public class EditBannerForm extends JPanel {
	private JTextField textFieldId;

	private BannerTask banner;
	private JButton clientField;
	private JComboBox<RunStatus> activeBox;
	private JComboBox<PaidStatus> paidBox;

	private JLabel titleLabel;
	private JTextField imgField;

	private JCheckBox isBigCheckbox;
	private JTextField tillDateField;
	private LocalDate date;

	public EditBannerForm(BannerTask banner) {
		this(banner.getForum());
		this.banner = banner;

		titleLabel.setText("Edit banner of " + banner.getForum().getUrl());
		textFieldId.setText(banner.getId().toString());

		activeBox.setSelectedItem(banner.getRunStatus());
		paidBox.setSelectedItem(banner.getPaidStatus());

		imgField.setText(banner.getImage());
		isBigCheckbox.setSelected(banner.isBig());

		setDate(banner.getTillDate());
	}

	public void setDate(LocalDate date) {
		this.date = date;
		tillDateField.setText(date.toString());
	}

	/**
	 * @wbp.parser.constructor
	 */
	public EditBannerForm(Forum forum) {
		banner = new BannerTask();
		banner.setForum(forum);

		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setMinimum(0);

		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 100, 150, 100, 150, 150, 150, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
		        Double.MIN_VALUE };
		setLayout(gbl_panel_1);

		titleLabel = MainFrame.buildHeader1("New task of " + forum.getUrl());
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.gridwidth = 7;
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.gridx = 0;
		gbc_datePicker.gridy = 0;
		add(titleLabel, gbc_datePicker);

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

		clientField = new JButton(banner.getForum().getUrl());
		clientField.addActionListener(e -> Application.forumController().edit(forum));
		GridBagConstraints gbc_clientField = new GridBagConstraints();
		gbc_clientField.gridwidth = 2;
		gbc_clientField.fill = GridBagConstraints.HORIZONTAL;
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
		GridBagConstraints gbc_paidBox = new GridBagConstraints();
		gbc_paidBox.anchor = GridBagConstraints.WEST;
		gbc_paidBox.insets = new Insets(0, 0, 5, 5);
		gbc_paidBox.gridx = 1;
		gbc_paidBox.gridy = 4;
		add(paidBox, gbc_paidBox);

		JLabel lblTotal = new JLabel("Image");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 2;
		gbc_lblTotal.gridy = 4;
		add(lblTotal, gbc_lblTotal);

		imgField = new JTextField();
		GridBagConstraints gbc_imgField = new GridBagConstraints();
		gbc_imgField.insets = new Insets(0, 0, 5, 5);
		gbc_imgField.fill = GridBagConstraints.HORIZONTAL;
		gbc_imgField.gridx = 3;
		gbc_imgField.gridy = 4;
		add(imgField, gbc_imgField);
		imgField.setColumns(10);

		isBigCheckbox = new JCheckBox("Is big");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 4;
		gbc_chckbxNewCheckBox.gridy = 4;
		add(isBigCheckbox, gbc_chckbxNewCheckBox);

		JLabel lblActiveStatus = new JLabel("Active status");
		GridBagConstraints gbc_lblActiveStatus = new GridBagConstraints();
		gbc_lblActiveStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblActiveStatus.gridx = 0;
		gbc_lblActiveStatus.gridy = 5;
		add(lblActiveStatus, gbc_lblActiveStatus);

		JButton updateButton = new JButton("Update");
		updateButton.setPreferredSize(new Dimension(100, 28));
		updateButton.addActionListener(e -> Application.bannerController().save(getBannerTask()));
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 0;
		gbc_btnUpdate.gridy = 6;
		add(updateButton, gbc_btnUpdate);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(100, 28));
		cancelButton.addActionListener(e -> Application.bannerController().displayAll());
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.anchor = GridBagConstraints.WEST;
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 6;
		add(cancelButton, gbc_button_1);

		activeBox = new JComboBox<RunStatus>();
		activeBox.setPreferredSize(new Dimension(100, 26));
		activeBox.setModel(new DefaultComboBoxModel<RunStatus>(RunStatus.values()));
		GridBagConstraints gbc_activeBox = new GridBagConstraints();
		gbc_activeBox.anchor = GridBagConstraints.WEST;
		gbc_activeBox.insets = new Insets(0, 0, 5, 5);
		gbc_activeBox.gridx = 1;
		gbc_activeBox.gridy = 5;
		add(activeBox, gbc_activeBox);

		JLabel lblTillDate = new JLabel("Till date");
		lblTillDate.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTillDate = new GridBagConstraints();
		gbc_lblTillDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblTillDate.gridx = 2;
		gbc_lblTillDate.gridy = 5;
		add(lblTillDate, gbc_lblTillDate);

		tillDateField = new JTextField();
		setDate(LocalDate.now().plusMonths(1));
		tillDateField.setEditable(false);
		tillDateField.setColumns(10);
		GridBagConstraints gbc_tillDateField = new GridBagConstraints();
		gbc_tillDateField.insets = new Insets(0, 0, 5, 5);
		gbc_tillDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_tillDateField.gridx = 3;
		gbc_tillDateField.gridy = 5;
		add(tillDateField, gbc_tillDateField);

		JButton btnSelect = new JButton("Select date");
		btnSelect.addActionListener(e -> setDate(JDatePicker.chooseDate(date)));
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.anchor = GridBagConstraints.WEST;
		gbc_btnSelect.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelect.gridx = 4;
		gbc_btnSelect.gridy = 5;
		add(btnSelect, gbc_btnSelect);
	}

	private BannerTask getBannerTask() {
		banner.setRunStatus((RunStatus) activeBox.getSelectedItem());
		banner.setPaidStatus((PaidStatus) paidBox.getSelectedItem());
		banner.setImage(imgField.getText());
		banner.setBig(isBigCheckbox.isSelected());
		banner.setTillDate(LocalDate.parse(tillDateField.getText()));
		return banner;
	}
}
