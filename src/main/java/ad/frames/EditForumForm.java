package ad.frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import ad.Application;
import ad.domain.entities.BannerTask;
import ad.domain.entities.Forum;

public class EditForumForm extends JPanel {
	private JFormattedTextField textFieldId;
	private JTextField textFieldUrl;
	private JTextField textFieldForumName;
	private JFormattedTextField textFieldTopicNum;
	private JFormattedTextField textFieldVisitors;
	private JTextField textFieldLogin;
	private JTextField textFieldPassword;
	private JTextField textFieldTopicTitle;
	private JTextArea textAreaCode;
	private JCheckBox clientCheckbox;

	private Forum forum;
	private JLabel titleLabel;

	public EditForumForm(Forum forum) {
		this();
		this.forum = forum;

		titleLabel.setText("Edit " + forum.getUrl());
		textFieldId.setText(forum.getId().toString());

		textFieldUrl.setText(forum.getUrl());
		textFieldForumName.setText(forum.getForumName());
		textFieldTopicNum.setValue(forum.getTopic());
		textFieldVisitors.setValue(forum.getVisitors());
		textFieldLogin.setText(forum.getLogin());
		textFieldPassword.setText(forum.getPassword());
		textFieldTopicTitle.setText(forum.getTopicTitle());
		textAreaCode.setText(forum.getCode());
		clientCheckbox.setSelected(forum.isClient());

		if (forum.isClient()) {
			TableInformationPanel tasksPanel = TableInformationPanel.displayTasksList(LocalDate.now(), forum.getTasks())
			        .addButton("Add task", e -> Application.taskController().newEntity(forum));
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.gridwidth = 3;
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.gridx = 0;
			gbc_btnNewButton.fill = GridBagConstraints.BOTH;
			gbc_btnNewButton.gridy = 12;
			add(tasksPanel, gbc_btnNewButton);

			List<BannerTask> asList = forum.getBanner() != null ? Arrays.asList(forum.getBanner()) : Arrays.asList();
			TableInformationPanel bannerPanel = TableInformationPanel
			        .displayBannersList(asList)
			        .addButton("Add banner", e -> Application.bannerController().newEntity(forum));

			gbc_btnNewButton.gridy = 13;
			add(bannerPanel, gbc_btnNewButton);
		}
	}

	/**
	 * @wbp.parser.constructor
	 */
	public EditForumForm() {
		forum = new Forum();

		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 100, 300, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0,
		        Double.MIN_VALUE };
		setLayout(gbl_panel_1);

		titleLabel = MainFrame.buildHeader1("New forum");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 3;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		add(titleLabel, gbc_lblNewLabel_1);

		JLabel lblForumSettings = new JLabel("Forum settings");
		lblForumSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblForumSettings = new GridBagConstraints();
		gbc_lblForumSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblForumSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblForumSettings.gridx = 1;
		gbc_lblForumSettings.gridy = 1;
		add(lblForumSettings, gbc_lblForumSettings);

		JLabel lblCode = new JLabel("Code");
		lblCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.insets = new Insets(0, 0, 5, 0);
		gbc_lblCode.gridx = 2;
		gbc_lblCode.gridy = 1;
		add(lblCode, gbc_lblCode);

		JLabel lblId = new JLabel("Id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 2;
		add(lblId, gbc_lblId);

		textFieldId = new JFormattedTextField();
		GridBagConstraints gbc_textFieldId = new GridBagConstraints();
		gbc_textFieldId.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldId.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldId.gridx = 1;
		gbc_textFieldId.gridy = 2;
		add(textFieldId, gbc_textFieldId);
		textFieldId.setColumns(10);
		textFieldId.setEnabled(false);

		textAreaCode = MainFrame.buildTextarea("");
		GridBagConstraints gbc_textAreaCode = new GridBagConstraints();
		gbc_textAreaCode.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaCode.gridheight = 9;
		gbc_textAreaCode.fill = GridBagConstraints.BOTH;
		gbc_textAreaCode.gridx = 2;
		gbc_textAreaCode.gridy = 2;
		add(new JScrollPane(textAreaCode), gbc_textAreaCode);

		JLabel lblNewLabel = new JLabel("URL");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		add(lblNewLabel, gbc_lblNewLabel);

		textFieldUrl = new JTextField();
		GridBagConstraints gbc_textFieldUrl = new GridBagConstraints();
		gbc_textFieldUrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUrl.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUrl.gridx = 1;
		gbc_textFieldUrl.gridy = 3;
		add(textFieldUrl, gbc_textFieldUrl);
		textFieldUrl.setColumns(10);

		JLabel lblForumUrl = new JLabel("Forum name");
		GridBagConstraints gbc_lblForumUrl = new GridBagConstraints();
		gbc_lblForumUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblForumUrl.gridx = 0;
		gbc_lblForumUrl.gridy = 4;
		add(lblForumUrl, gbc_lblForumUrl);

		textFieldForumName = new JTextField();
		GridBagConstraints gbc_textFieldForumName = new GridBagConstraints();
		gbc_textFieldForumName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldForumName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldForumName.gridx = 1;
		gbc_textFieldForumName.gridy = 4;
		add(textFieldForumName, gbc_textFieldForumName);
		textFieldForumName.setColumns(10);

		JLabel lblTopic = new JLabel("Topic");
		GridBagConstraints gbc_lblTopic = new GridBagConstraints();
		gbc_lblTopic.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopic.gridx = 0;
		gbc_lblTopic.gridy = 5;
		add(lblTopic, gbc_lblTopic);

		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setMinimum(0);

		textFieldTopicNum = new JFormattedTextField(numberFormatter);
		GridBagConstraints gbc_textFieldTopicNum = new GridBagConstraints();
		gbc_textFieldTopicNum.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTopicNum.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTopicNum.gridx = 1;
		gbc_textFieldTopicNum.gridy = 5;
		add(textFieldTopicNum, gbc_textFieldTopicNum);
		textFieldTopicNum.setColumns(10);
		textFieldTopicNum.setValue(0);

		JLabel lblTopicTitle = new JLabel("Topic title");
		GridBagConstraints gbc_lblTopicTitle = new GridBagConstraints();
		gbc_lblTopicTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTopicTitle.gridx = 0;
		gbc_lblTopicTitle.gridy = 6;
		add(lblTopicTitle, gbc_lblTopicTitle);

		textFieldTopicTitle = new JTextField();
		GridBagConstraints gbc_textFieldTopicTitle = new GridBagConstraints();
		gbc_textFieldTopicTitle.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTopicTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTopicTitle.gridx = 1;
		gbc_textFieldTopicTitle.gridy = 6;
		add(textFieldTopicTitle, gbc_textFieldTopicTitle);
		textFieldTopicTitle.setColumns(10);

		JLabel lblVisitors = new JLabel("Visitors");
		GridBagConstraints gbc_lblVisitors = new GridBagConstraints();
		gbc_lblVisitors.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisitors.gridx = 0;
		gbc_lblVisitors.gridy = 7;
		add(lblVisitors, gbc_lblVisitors);

		textFieldVisitors = new JFormattedTextField(numberFormatter);
		GridBagConstraints gbc_textFieldVisitors = new GridBagConstraints();
		gbc_textFieldVisitors.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldVisitors.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldVisitors.gridx = 1;
		gbc_textFieldVisitors.gridy = 7;
		add(textFieldVisitors, gbc_textFieldVisitors);
		textFieldVisitors.setColumns(10);
		textFieldVisitors.setValue(0);

		JLabel lblAccount = new JLabel("Account");
		lblAccount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAccount = new GridBagConstraints();
		gbc_lblAccount.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAccount.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccount.gridx = 1;
		gbc_lblAccount.gridy = 8;
		add(lblAccount, gbc_lblAccount);

		JLabel lblLogin = new JLabel("Login");
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 9;
		add(lblLogin, gbc_lblLogin);

		textFieldLogin = new JTextField();
		GridBagConstraints gbc_textFieldLogin = new GridBagConstraints();
		gbc_textFieldLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLogin.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLogin.gridx = 1;
		gbc_textFieldLogin.gridy = 9;
		add(textFieldLogin, gbc_textFieldLogin);
		textFieldLogin.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 10;
		add(lblPassword, gbc_lblPassword);

		textFieldPassword = new JTextField();
		GridBagConstraints gbc_textFieldPassword = new GridBagConstraints();
		gbc_textFieldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPassword.gridx = 1;
		gbc_textFieldPassword.gridy = 10;
		add(textFieldPassword, gbc_textFieldPassword);
		textFieldPassword.setColumns(10);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setPreferredSize(new Dimension(100, 28));
		btnUpdate.addActionListener(e -> Application.forumController().save(getForum()));
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUpdate.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdate.gridx = 0;
		gbc_btnUpdate.gridy = 11;
		add(btnUpdate, gbc_btnUpdate);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(new Dimension(100, 28));
		btnCancel.addActionListener(e -> Application.forumController().displayAll());
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 11;
		add(btnCancel, gbc_btnCancel);

		clientCheckbox = new JCheckBox("Client");
		GridBagConstraints gbc_clientCheckbox = new GridBagConstraints();
		gbc_clientCheckbox.insets = new Insets(0, 0, 5, 0);
		gbc_clientCheckbox.anchor = GridBagConstraints.WEST;
		gbc_clientCheckbox.gridx = 2;
		gbc_clientCheckbox.gridy = 11;
		add(clientCheckbox, gbc_clientCheckbox);
	}

	private Forum getForum() {
		forum.setUrl(textFieldUrl.getText());
		forum.setForumName(textFieldForumName.getText());

		forum.setTopic((Integer) (textFieldTopicNum.getValue()));
		forum.setVisitors((Integer) (textFieldVisitors.getValue()));
		forum.setTopicTitle(textFieldTopicTitle.getText());

		forum.setLogin(textFieldLogin.getText());
		forum.setPassword(textFieldPassword.getText());

		forum.setCode(textAreaCode.getText());
		forum.setClient(clientCheckbox.isSelected());
		return forum;
	}
}
