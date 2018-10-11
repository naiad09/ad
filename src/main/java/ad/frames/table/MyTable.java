package ad.frames.table;

import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ad.domain.entities.BaseEntity;
import ad.helpers.TableCellEditorAdapter;

public class MyTable extends JScrollPane {

	private JTable table = new JTable();
	private List<TableColumnHelper<?>> columnHelpers;

	@SafeVarargs
	public <E extends BaseEntity> MyTable(List<E> entities, TableColumnHelper<E>... helpers) {
		setViewportView(table);

		columnHelpers = Arrays.asList(helpers);

		setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setRowHeight(22);

		table.setAutoCreateRowSorter(true);
		table.getTableHeader().setReorderingAllowed(false);

		Object[][] data = !entities.isEmpty()
		        ? entities.stream()
		                .map(e -> Stream.of(helpers).map(h -> h.getFunction().apply(e)).toArray())
		                .toArray(Object[][]::new)
		        : new Object[][] { { "", "", "", "No data to display" } };
		Object[] columnNames = columnHelpers.stream().map(t -> t.getButtonAction() == null ? t.getName() : "")
		        .toArray();

		table.setModel(new DefaultTableModel(data, columnNames) {
			@Override
			public Class<?> getColumnClass(int column) {
				return columnHelpers.get(column).getOutputClass();
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnHelpers.get(column).isEditable();
			}
		});

		IntStream.range(0, helpers.length).forEach(i -> {
			TableColumnHelper<?> columnHelper = columnHelpers.get(i);
			TableColumn tableColumn = table.getColumnModel().getColumn(i);
			if (columnHelper.getWidth() != null) {
				table.getColumnModel().getColumn(i).setMaxWidth(columnHelper.getWidth());
			}
			if (!entities.isEmpty() && columnHelper.getButtonAction() != null) {
				tableColumn.setCellRenderer(
				        (table2, value, isSelected, hasFocus, row, column) -> new JButton(columnHelper.getName()));
				tableColumn.setCellEditor(new TableCellEditorAdapter((row, column) -> {
					Integer forumId = Integer.valueOf(table.getValueAt(row, i).toString());
					columnHelper.getButtonAction().accept(forumId);
				}));
			}
		});
	}
}