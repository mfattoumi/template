package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.SubType;

public interface SubTypeDao {
	public SubType addSubType(SubType subType);
	public SubType deleteSubType(int id);
	public SubType updateSubType(SubType subType);
	public SubType getSubTypeById(int id);
	public List<SubType> getAllSubTypes();
}