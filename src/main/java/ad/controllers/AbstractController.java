package ad.controllers;

import java.util.List;

import ad.Application;
import ad.domain.dao.util.GenericDAO;
import ad.frames.MainFrame;

public abstract class AbstractController<E> {

	protected final GenericDAO<E> dao;

	public AbstractController(Class<E> type) {
		dao = new GenericDAO<>(type);
	}

	public AbstractController(GenericDAO<E> dao) {
		this.dao = dao;
	}

	public MainFrame mainFrame() {
		return Application.mainFrame();
	}

	public void edit(int id) {
		edit(dao.get(id));
	}

	abstract public void edit(E entity);

	public void save(E entity) {
		dao.saveOrUpdate(entity);
		displayAll();
	}

	public void displayAll() {
		displayAll(dao.getAll());
	}

	abstract public void displayAll(List<E> entities);

	public void delete(int id) {
		E entity = dao.get(id);
		delete(entity);
	}

	public void delete(E entity) {
		dao.delete(entity);
		displayAll();
	}

}
