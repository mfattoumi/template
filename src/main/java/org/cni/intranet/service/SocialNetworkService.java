package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.SocialNetwork;

public interface SocialNetworkService {
	public SocialNetwork addSocialNetwork(SocialNetwork socialNetwork);
	public SocialNetwork deleteSocialNetwork(int id);
	public SocialNetwork updateSocialNetwork(SocialNetwork socialNetwork);
	public SocialNetwork getSocialNetworkById(int id);
	public List<SocialNetwork> getAllSocialNetworks();
}
