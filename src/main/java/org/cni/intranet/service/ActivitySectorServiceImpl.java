package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.ActivitySectorDao;
import org.cni.intranet.entities.ActivitySector;

@Transactional
public class ActivitySectorServiceImpl implements ActivitySectorService {

	@Autowired
	private ActivitySectorDao activitySectorDao;

	public void setActivitySectorDao(ActivitySectorDao activitySectorDao) {
		this.activitySectorDao = activitySectorDao;
	}
	
	@Override
	public ActivitySector addActivitySector(ActivitySector activitySector) {
		return activitySectorDao.addActivitySector(activitySector);
	}

	@Override
	public ActivitySector deleteActivitySector(int id) {
		return activitySectorDao.deleteActivitySector(id);
	}

	@Override
	public ActivitySector updateActivitySector(ActivitySector activitySector) {
		return activitySectorDao.updateActivitySector(activitySector);
	}

	@Override
	public ActivitySector getActivitySectorById(int id) {
		return activitySectorDao.getActivitySectorById(id);
	}

	@Override
	public List<ActivitySector> getAllActivitySectors() {
		return activitySectorDao.getAllActivitySectors();
	}

}
