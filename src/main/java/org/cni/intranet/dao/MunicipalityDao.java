package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Municipality;

public interface MunicipalityDao {
	public Municipality addMunicipality(Municipality municipality);
	public Municipality deleteMunicipality(int id);
	public Municipality updateMunicipality(Municipality municipality);
	public Municipality getMunicipalityById(int id);
	public List<Municipality> getAllMunicipalitys();
}