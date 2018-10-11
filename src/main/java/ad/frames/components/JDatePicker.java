package ad.frames.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ad.Application;

public class JDatePicker extends JDialog {
	private JTable table;
	private LocalDate localDate;
	private JLabel monthLabel;

	public JDatePicker(LocalDate date) {
		setLocationRelativeTo(Application.mainFrame());
		setModal(true);
		setResizable(false);
		setTitle("Choose date");
		setSize(new Dimension(250, 200));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		localDate = date;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 17, 100, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		monthLabel = new JLabel();
		monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		monthLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblChooseDate = new GridBagConstraints();
		gbc_lblChooseDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblChooseDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseDate.gridx = 1;
		gbc_lblChooseDate.gridy = 0;
		getContentPane().add(monthLabel, gbc_lblChooseDate);

		JButton prev = new JButton("<<");
		prev.addActionListener(e -> {
			localDate = localDate.minusMonths(1);
			displayDate();
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		getContentPane().add(prev, gbc_button);

		JButton next = new JButton(">>");
		next.addActionListener(e -> {
			localDate = localDate.plusMonths(1);
			displayDate();
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 0;
		getContentPane().add(next, gbc_button_1);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				Object value = table.getValueAt(row, col);
				if (value != null) {
					localDate = localDate.withDayOfMonth(Integer.valueOf(value.toString()));
					dispose();
				}
			}
		});

		displayDate();
		setVisible(true);
	}

	public void displayDate() {
		monthLabel.setText(localDate.getMonth() + " " + localDate.getYear());

		ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
		Object[] week = new Object[7];
		arrayList.add(week);
		for (LocalDate day = localDate.withDayOfMonth(1); day.getMonth() == localDate.getMonth(); day = day
		        .plusDays(1)) {
			if (day.getDayOfWeek() == DayOfWeek.MONDAY && day.getDayOfMonth() > 1) {
				week = new Object[7];
				arrayList.add(week);
			}
			week[day.getDayOfWeek().ordinal()] = day.getDayOfMonth();
		}
		table.setModel(new DefaultTableModel(
		        arrayList.toArray(new Object[0][0]),
		        new String[] {
		                "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"
		        }));
		table.changeSelection(
		        (localDate.getDayOfMonth() + localDate.withDayOfMonth(1).getDayOfWeek().ordinal() - 1) / 7,
		        localDate.getDayOfWeek().ordinal(), false, false);

		LocalDate now = LocalDate.now();
		if (now.getMonth() == localDate.getMonth()) {
			table.getColumnModel().getColumn(now.getDayOfWeek().ordinal())
			        .setCellRenderer(new DefaultTableCellRenderer() {
				        @Override
				        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				                boolean hasFocus, int row, int column) {
					        Component superRenderer = super.getTableCellRendererComponent(table, value, isSelected,
					                hasFocus, row, column);
					        if (new Integer(now.getDayOfMonth()).equals(table.getValueAt(row, column))) {
						        Font newFont = superRenderer.getFont().deriveFont(Font.BOLD);
						        superRenderer.setFont(newFont);
					        }
					        return superRenderer;
				        }
			        });
		}
	}

	public static LocalDate chooseDate(LocalDate startDate) {
		return new JDatePicker(startDate).localDate;
	}

}
