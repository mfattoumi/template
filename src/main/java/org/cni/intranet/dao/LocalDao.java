package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Local;

public interface LocalDao {
	public Local addLocal(Local local);
	public Local deleteLocal(int id);
	public Local updateLocal(Local local);
	public Local getLocalById(int id);
	public List<Local> getAllLocals();
}