package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Structure;

public interface StructureDao {
	public Structure addStructure(Structure structure);
	public Structure deleteStructure(int id);
	public Structure updateStructure(Structure structure);
	public Structure getStructureById(int id);
	public List<Structure> getAllStructures();
}