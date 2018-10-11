package ad.domain.dao.util;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Параметризированные расширение {@link MinimalGenericDAO}. Содержит также
 * публичные методы сохранения, обновления и удаления сущности.
 */
public class GenericDAO<E> {

	private final Class<E> daoType;

	public GenericDAO(Class<E> type) {
		this.daoType = type;
	}

	protected Session currentSession() {
		return HibernateUtil.getSession();
		// return HibernateUtil.getSessionFactory().openSession();
	}

	protected Criteria getCriteriaDaoType() {
		return currentSession().createCriteria(daoType);
	}

	public E get(Serializable key) {
		Session currentSession = currentSession();
		Transaction transaction = currentSession.beginTransaction();
		E e = currentSession.get(daoType, key);
		transaction.commit();
		return e;
	}

	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		return getCriteriaDaoType().list();
	}

	public void saveOrUpdate(E entity) {
		Session currentSession = currentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.saveOrUpdate(entity);
		transaction.commit();
	}

	public void delete(E entity) {
		Session currentSession = currentSession();
		Transaction transaction = currentSession.beginTransaction();
		currentSession.delete(entity);
		transaction.commit();
	}

}