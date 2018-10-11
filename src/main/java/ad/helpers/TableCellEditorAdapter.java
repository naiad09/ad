package ad.helpers;

import java.awt.Component;
import java.util.function.BiConsumer;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableCellEditorAdapter extends DefaultCellEditor {

	private BiConsumer<Integer, Integer> action;

	public TableCellEditorAdapter(BiConsumer<Integer, Integer> action) {
		super(new JCheckBox());
		this.action = action;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		action.accept(row, column);
		return null;
	}

}
