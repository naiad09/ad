package ad.domain.dao.util;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.hibernate.criterion.Restrictions;

import ad.domain.entities.Forum;

public class ForumDao extends GenericDAO<Forum> {

	public ForumDao() {
		super(Forum.class);
	}

	@Override
	public void saveOrUpdate(Forum entity) {
		if (entity.getAccount() != null) {
			entity.getAccount().setForum(entity);
		}
		super.saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Forum> getAllClients() {
		return getCriteriaDaoType()
		        .add(Restrictions.eq("isClient", true))
		        .list();
	}

	public List<Forum> getAllShuffled() {
		LinkedList<Forum> linkedList = getAll().stream()
		        .sorted((o1, o2) -> {
			        return o2.getVisitors() - o1.getVisitors();
		        }).collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
		int shuffleSize = linkedList.size() / 3;
		List<Forum> collect = LongStream
		        .generate(() -> Math.round(Math.random() * Math.min(shuffleSize, linkedList.size() - 1)))
		        .mapToInt(l -> new Long(l).intValue())
		        .mapToObj(i -> linkedList.remove(i))
		        .limit(linkedList.size())
		        .collect(Collectors.toList());
		return collect;
	}
}
