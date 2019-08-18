package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Gouvernorat;

public interface GouvernoratService {
	public Gouvernorat addGouvernorat(Gouvernorat gouvernorat);
	public Gouvernorat deleteGouvernorat(int id);
	public Gouvernorat updateGouvernorat(Gouvernorat gouvernorat);
	public Gouvernorat getGouvernoratById(int id);
	public List<Gouvernorat> getAllGouvernorats();
}
