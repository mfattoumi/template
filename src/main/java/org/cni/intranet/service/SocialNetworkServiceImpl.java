package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.SocialNetworkDao;
import org.cni.intranet.entities.SocialNetwork;

@Transactional
public class SocialNetworkServiceImpl implements SocialNetworkService {

	@Autowired
	private SocialNetworkDao socialNetworkDao;

	public void setSocialNetworkDao(SocialNetworkDao socialNetworkDao) {
		this.socialNetworkDao = socialNetworkDao;
	}
	
	@Override
	public SocialNetwork addSocialNetwork(SocialNetwork socialNetwork) {
		return socialNetworkDao.addSocialNetwork(socialNetwork);
	}

	@Override
	public SocialNetwork deleteSocialNetwork(int id) {
		return socialNetworkDao.deleteSocialNetwork(id);
	}

	@Override
	public SocialNetwork updateSocialNetwork(SocialNetwork socialNetwork) {
		return socialNetworkDao.updateSocialNetwork(socialNetwork);
	}

	@Override
	public SocialNetwork getSocialNetworkById(int id) {
		return socialNetworkDao.getSocialNetworkById(id);
	}

	@Override
	public List<SocialNetwork> getAllSocialNetworks() {
		return socialNetworkDao.getAllSocialNetworks();
	}

}
