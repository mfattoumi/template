package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.SubPosition;

public interface SubPositionService {
	public SubPosition addSubPosition(SubPosition subPosition);
	public SubPosition deleteSubPosition(int id);
	public SubPosition updateSubPosition(SubPosition subPosition);
	public SubPosition getSubPositionById(int id);
	public List<SubPosition> getAllSubPositions();
}
