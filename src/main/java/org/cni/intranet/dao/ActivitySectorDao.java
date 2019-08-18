package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.ActivitySector;

public interface ActivitySectorDao {
	public ActivitySector addActivitySector(ActivitySector activitySector);
	public ActivitySector deleteActivitySector(int id);
	public ActivitySector updateActivitySector(ActivitySector activitySector);
	public ActivitySector getActivitySectorById(int id);
	public List<ActivitySector> getAllActivitySectors();
}