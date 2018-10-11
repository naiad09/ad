package ad.domain.dao.util;

import java.util.List;

import ad.domain.entities.PrPost;

public class PrPostDao extends GenericDAO<PrPost> {

	public PrPostDao() {
		super(PrPost.class);
	}

	@Override
	public void saveOrUpdate(PrPost entity) {
		List<PrPost> posts = entity.getForum().getPosts();
		if (!posts.contains(entity)) {
			posts.add(entity);
		}
		super.saveOrUpdate(entity);
	}
}
