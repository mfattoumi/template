package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.MunicipalityDao;
import org.cni.intranet.entities.Municipality;

@Transactional
public class MunicipalityServiceImpl implements MunicipalityService {

	@Autowired
	private MunicipalityDao municipalityDao;

	public void setMunicipalityDao(MunicipalityDao municipalityDao) {
		this.municipalityDao = municipalityDao;
	}
	
	@Override
	public Municipality addMunicipality(Municipality municipality) {
		return municipalityDao.addMunicipality(municipality);
	}

	@Override
	public Municipality deleteMunicipality(int id) {
		return municipalityDao.deleteMunicipality(id);
	}

	@Override
	public Municipality updateMunicipality(Municipality municipality) {
		return municipalityDao.updateMunicipality(municipality);
	}

	@Override
	public Municipality getMunicipalityById(int id) {
		return municipalityDao.getMunicipalityById(id);
	}

	@Override
	public List<Municipality> getAllMunicipalitys() {
		return municipalityDao.getAllMunicipalitys();
	}

}
