package ad.frames.table;

import java.util.function.Consumer;
import java.util.function.Function;

import ad.domain.entities.BaseEntity;

public class TableColumnHelper<E extends BaseEntity> {
	private String name;
	private Class<?> outputClass;
	private Function<E, ?> function;
	private Integer width;
	private boolean editable;
	private Consumer<Integer> buttonAction;

	public <T> TableColumnHelper(String name, Class<E> entityClass, Class<T> outputClass, Function<E, T> function) {
		this.name = name;
		this.outputClass = outputClass;
		this.function = function;
	}

	public <T> TableColumnHelper(String name, Class<E> entityClass, Class<T> outputClass, Function<E, T> function, int widgth) {
		this.name = name;
		this.outputClass = outputClass;
		this.function = function;
		this.width = widgth;
	}

	public <T> TableColumnHelper(String name, Class<E> entityClass, Class<T> outputClass, Function<E, T> function, int widgth, Boolean editable) {
		this.name = name;
		this.outputClass = outputClass;
		this.function = function;
		this.width = widgth;
		this.editable = editable;
	}

	public TableColumnHelper(String name, int width, Consumer<Integer> buttonAction) {
		this.name = name;
		this.outputClass = Integer.class;
		this.function = BaseEntity::getId;
		this.width = width;
		this.buttonAction = buttonAction;
		editable = true;
	}

	public String getName() {
		return name;
	}

	public Class<?> getOutputClass() {
		return outputClass;
	}

	public Function<E, ?> getFunction() {
		return function;
	}

	public Integer getWidth() {
		return width;
	}

	public boolean isEditable() {
		return editable;
	}

	public Consumer<Integer> getButtonAction() {
		return buttonAction;
	}
}
