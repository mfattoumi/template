package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.ActivitySector;

@Repository
public class ActivitySectorDaoImpl implements ActivitySectorDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public ActivitySector addActivitySector(ActivitySector activitySector) {
		em.persist(activitySector);
		return activitySector;
	}

	@Transactional
	public ActivitySector deleteActivitySector(int id) {
		ActivitySector activitySector = getActivitySectorById(id);
		em.remove(activitySector);
		return activitySector;
	}

	@Transactional
	public ActivitySector updateActivitySector(ActivitySector activitySector) {
		em.merge(activitySector);
		return activitySector;
	}

	@Transactional
	public ActivitySector getActivitySectorById(int id) {
		ActivitySector as = em.find(ActivitySector.class, id);
		if(as==null) throw new RuntimeException("ActivitySector introuvable");
		return as;
	}

	@Transactional
	public List<ActivitySector> getAllActivitySectors() {
		Query req = em.createQuery("from ActivitySector");
		return req.getResultList();
	}

}
