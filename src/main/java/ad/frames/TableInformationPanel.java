package ad.frames;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ad.Application;
import ad.domain.entities.BannerTask;
import ad.domain.entities.BaseEntity;
import ad.domain.entities.Forum;
import ad.domain.entities.task.PaidStatus;
import ad.domain.entities.task.RunStatus;
import ad.domain.entities.task.Task;
import ad.frames.components.JDatePicker;
import ad.frames.table.MyTable;
import ad.frames.table.TableColumnHelper;

public class TableInformationPanel extends JPanel {

	@SafeVarargs
	public <E extends BaseEntity> TableInformationPanel(String title, List<E> entities, TableColumnHelper<E>... helpers) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblTotalValue = new JLabel("Total: " + entities.size());
		lblTotalValue.setBorder(new EmptyBorder(0, 20, 0, 0));
		lblTotalValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalValue = new GridBagConstraints();
		gbc_lblTotalValue.anchor = GridBagConstraints.SOUTH;
		gbc_lblTotalValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalValue.gridx = 0;
		gbc_lblTotalValue.gridy = 0;
		add(lblTotalValue, gbc_lblTotalValue);

		JLabel lblTitle = MainFrame.buildHeader1(title);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 2;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);

		MyTable table = new MyTable(entities, helpers);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridwidth = 4;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		add(table, gbc_table);
	}

	public void addQuickFind() {
		JLabel lblQuickFind = new JLabel("Quick find:");
		GridBagConstraints gbc_lblQuickFind = new GridBagConstraints();
		gbc_lblQuickFind.anchor = GridBagConstraints.EAST;
		gbc_lblQuickFind.insets = new Insets(0, 0, 0, 5);
		gbc_lblQuickFind.gridx = 0;
		gbc_lblQuickFind.gridy = 2;
		add(lblQuickFind, gbc_lblQuickFind);

		JTextField textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(10);

		JButton clearQuickFindButton = new JButton("Clear");
		clearQuickFindButton.addActionListener(e -> textField.setText(null));
		GridBagConstraints gbc_clearQuickFindButton = new GridBagConstraints();
		gbc_clearQuickFindButton.anchor = GridBagConstraints.WEST;
		gbc_clearQuickFindButton.insets = new Insets(0, 0, 0, 5);
		gbc_clearQuickFindButton.gridx = 2;
		gbc_clearQuickFindButton.gridy = 2;
		add(clearQuickFindButton, gbc_clearQuickFindButton);

	}

	public TableInformationPanel addButton(String text, ActionListener buttonAction) {
		JButton btnSomeButton = new JButton(text);
		btnSomeButton.addActionListener(buttonAction);
		GridBagConstraints gbc_btnSomeButton = new GridBagConstraints();
		gbc_btnSomeButton.anchor = GridBagConstraints.SOUTH;
		gbc_btnSomeButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnSomeButton.gridx = 3;
		gbc_btnSomeButton.gridy = 0;
		add(btnSomeButton, gbc_btnSomeButton);

		return this;
	}

	public static boolean confirm(String message) {
		int dialogResult = JOptionPane.showConfirmDialog(Application.mainFrame(), message, "Warning",
		        JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}

	public static TableInformationPanel displayTasksListWithReport(LocalDate date, List<Task> tasks) {
		TableInformationPanel tableInformationPanel = displayTasksList(date, tasks);

		GridBagLayout gridBagLayout = (GridBagLayout) tableInformationPanel.getLayout();
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };

		JLabel lblTitle = MainFrame.buildHeader1("Report");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 2;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 2;
		tableInformationPanel.add(lblTitle, gbc_lblTitle);

		String buildReport = Application.taskController().buildReport(date);

		JTextArea textarea = MainFrame.buildTextarea(buildReport);
		GridBagConstraints gbc_lblQuickFind = new GridBagConstraints();
		gbc_lblQuickFind.insets = new Insets(0, 0, 0, 5);
		gbc_lblQuickFind.gridx = 0;
		gbc_lblQuickFind.gridy = 3;
		gbc_lblQuickFind.gridwidth = 4;
		gbc_lblQuickFind.fill = GridBagConstraints.BOTH;
		tableInformationPanel.add(new JScrollPane(textarea), gbc_lblQuickFind);

		JButton btnSomeButton = new JButton("Post on whitepr");
		btnSomeButton.addActionListener(e -> Application.taskController().postPRReport(buildReport));
		GridBagConstraints gbc_btnSomeButton = new GridBagConstraints();
		gbc_btnSomeButton.anchor = GridBagConstraints.SOUTH;
		gbc_btnSomeButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnSomeButton.gridx = 3;
		gbc_btnSomeButton.gridy = 2;
		tableInformationPanel.add(btnSomeButton, gbc_btnSomeButton);

		return tableInformationPanel;
	}

	public static TableInformationPanel displayTasksList(LocalDate date, List<Task> tasks) {
		TableInformationPanel tableInformationPanel = new TableInformationPanel("Tasks", tasks,
		        new TableColumnHelper<Task>("edit", 50, i -> Application.taskController().edit(i)),
		        new TableColumnHelper<Task>("del", 50, i -> {
			        if (confirm("Do you really want to delete #" + i + "?")) {
				        Application.taskController().delete(i);
			        }
		        }),
		        new TableColumnHelper<Task>("#", Task.class, Integer.class, Task::getId, 50),
		        new TableColumnHelper<Task>("Client", Task.class, String.class, t -> t.getClient().getUrl()),
		        new TableColumnHelper<Task>("Paid status", Task.class, PaidStatus.class, Task::getPaidStatus, 100),
		        new TableColumnHelper<Task>("Total", Task.class, Integer.class, Task::getTotal, 100),
		        new TableColumnHelper<Task>("Per day", Task.class, Integer.class, Task::getPerDay, 100),
		        new TableColumnHelper<Task>("Create day", Task.class, LocalDateTime.class, Task::getCreateDate),
		        new TableColumnHelper<Task>("Run status", Task.class, RunStatus.class, Task::getRunStatus, 100),
		        new TableColumnHelper<Task>("Done", Task.class, Long.class, Task::getDone, 100),
		        new TableColumnHelper<Task>("Today", Task.class, Long.class, t -> t.getPostsForDate(date), 100),
		        new TableColumnHelper<Task>("Rest", Task.class, Long.class, Task::getRest, 100),
		        new TableColumnHelper<Task>("%", Task.class, String.class, task -> task.getPercentageDone() + "%", 50),
		        new TableColumnHelper<Task>("Finished Today", Task.class, Boolean.class,
		                task -> task.getPerDay() <= task.getToday() || task.getRest() == 0, 50),
		        new TableColumnHelper<Task>("Ready", Task.class, Boolean.class, task -> task.isReady(), 50));

		tableInformationPanel.addButton(date.toString(), e -> {
			LocalDate newDate = JDatePicker.chooseDate(date);
			Application.taskController().displayAll(newDate, tasks);
		});

		return tableInformationPanel;
	}

	public static TableInformationPanel displayForumsList(String title, List<Forum> forums) {
		return new TableInformationPanel(title, forums,
		        new TableColumnHelper<Forum>("edit", 50, i -> Application.forumController().edit(i)),
		        new TableColumnHelper<Forum>("del", 50, i -> {
			        if (confirm("Do you really want to delete #" + i + "?")) {
				        Application.forumController().delete(i);
			        }
		        }),
		        new TableColumnHelper<Forum>("#", Forum.class, Integer.class, Forum::getId, 50),
		        new TableColumnHelper<Forum>("URL", Forum.class, String.class, Forum::getUrl),
		        new TableColumnHelper<Forum>("Name", Forum.class, String.class, Forum::getForumName),
		        new TableColumnHelper<Forum>("Topic", Forum.class, Integer.class, Forum::getTopic, 100),
		        new TableColumnHelper<Forum>("Update date", Forum.class, LocalDateTime.class, Forum::getUpdateDate),
		        new TableColumnHelper<Forum>("Topic title", Forum.class, String.class, Forum::getTopicTitle),
		        new TableColumnHelper<Forum>("Ready", Forum.class, Boolean.class, f -> f.isReady(), 50),
		        new TableColumnHelper<Forum>("Visitors", Forum.class, Integer.class, Forum::getVisitors, 100),
		        new TableColumnHelper<Forum>("Client", Forum.class, Boolean.class, Forum::isClient, 50));
	}

	public static TableInformationPanel displayBannersListWithCode(List<BannerTask> banners) {
		TableInformationPanel tableInformationPanel = displayBannersList(banners);

		GridBagLayout gridBagLayout = (GridBagLayout) tableInformationPanel.getLayout();
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };

		JLabel lblTitle = MainFrame.buildHeader1("Code");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 2;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 2;
		tableInformationPanel.add(lblTitle, gbc_lblTitle);

		String signature = Application.bannerController().buildSignature();
		JTextArea textarea = MainFrame.buildTextarea(signature);
		GridBagConstraints gbc_lblQuickFind = new GridBagConstraints();
		gbc_lblQuickFind.insets = new Insets(0, 0, 0, 5);
		gbc_lblQuickFind.gridx = 0;
		gbc_lblQuickFind.gridy = 3;
		gbc_lblQuickFind.gridwidth = 4;
		gbc_lblQuickFind.fill = GridBagConstraints.BOTH;
		tableInformationPanel.add(new JScrollPane(textarea), gbc_lblQuickFind);

		JButton btnSomeButton = new JButton("Update signatures");
		btnSomeButton.addActionListener(e -> Application.bannerController().updateSignatures(signature));
		GridBagConstraints gbc_btnSomeButton = new GridBagConstraints();
		gbc_btnSomeButton.anchor = GridBagConstraints.SOUTH;
		gbc_btnSomeButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnSomeButton.gridx = 3;
		gbc_btnSomeButton.gridy = 2;
		tableInformationPanel.add(btnSomeButton, gbc_btnSomeButton);

		return tableInformationPanel;
	}

	public static TableInformationPanel displayBannersList(List<BannerTask> banners) {
		TableInformationPanel tableInformationPanel = new TableInformationPanel("Banners", banners,
		        new TableColumnHelper<BannerTask>("edit", 50, i -> Application.bannerController().edit(i)),
		        new TableColumnHelper<BannerTask>("del", 50, i -> {
			        if (confirm("Do you really want to delete #" + i + "?")) {
				        Application.bannerController().delete(i);
			        }
		        }),
		        new TableColumnHelper<BannerTask>("#", BannerTask.class, Integer.class, BannerTask::getId, 50),
		        new TableColumnHelper<BannerTask>("Client", BannerTask.class, String.class, t -> t.getForum().getUrl()),
		        new TableColumnHelper<BannerTask>("Image", BannerTask.class, String.class, t -> t.getImage()),
		        new TableColumnHelper<BannerTask>("Till date", BannerTask.class, LocalDate.class,
		                BannerTask::getTillDate, 200),
		        new TableColumnHelper<BannerTask>("Paid status", BannerTask.class, PaidStatus.class,
		                BannerTask::getPaidStatus, 100),
		        new TableColumnHelper<BannerTask>("Run status", BannerTask.class, RunStatus.class,
		                BannerTask::getRunStatus, 100),
		        new TableColumnHelper<BannerTask>("Big", BannerTask.class, Boolean.class, task -> task.isBig(), 50));
		return tableInformationPanel;
	}
}