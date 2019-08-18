package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Type;

public interface TypeService {
	public Type addType(Type type);
	public Type deleteType(int id);
	public Type updateType(Type type);
	public Type getTypeById(int id);
	public List<Type> getAllTypes();
}
