package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.GouvernoratDao;
import org.cni.intranet.entities.Gouvernorat;

@Transactional
public class GouvernoratServiceImpl implements GouvernoratService {

	@Autowired
	private GouvernoratDao gouvernoratDao;

	public void setGouvernoratDao(GouvernoratDao gouvernoratDao) {
		this.gouvernoratDao = gouvernoratDao;
	}
	
	@Override
	public Gouvernorat addGouvernorat(Gouvernorat gouvernorat) {
		return gouvernoratDao.addGouvernorat(gouvernorat);
	}

	@Override
	public Gouvernorat deleteGouvernorat(int id) {
		return gouvernoratDao.deleteGouvernorat(id);
	}

	@Override
	public Gouvernorat updateGouvernorat(Gouvernorat gouvernorat) {
		return gouvernoratDao.updateGouvernorat(gouvernorat);
	}

	@Override
	public Gouvernorat getGouvernoratById(int id) {
		return gouvernoratDao.getGouvernoratById(id);
	}

	@Override
	public List<Gouvernorat> getAllGouvernorats() {
		return gouvernoratDao.getAllGouvernorats();
	}

}
