package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Local;
import org.cni.intranet.entities.Structure;


public interface StructureService {

	Structure addStructure(Structure structure);

	Structure deleteStructure(int id);

	Structure updateStructure(Structure structure);

	Structure getStructureById(int id);

	List<Structure> getAllStructures();

	List<Structure> searchForStructure(String searchText);

	void indexStructures();

	List<Structure> searchAdvForStructure(String searchText, String searchText1, String searchText2, String searchText3, String searchText4);

	List<Structure> searchRapidForStructure(String searchText, String searchText1, String searchText2);

	List<Local> getLocalsByStructure(int idStructure);
}
