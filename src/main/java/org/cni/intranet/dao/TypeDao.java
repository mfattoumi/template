package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Type;

public interface TypeDao {
	public Type addType(Type type);
	public Type deleteType(int id);
	public Type updateType(Type type);
	public Type getTypeById(int id);
	public List<Type> getAllTypes();
}