package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Gouvernorat;

public interface GouvernoratDao {
	public Gouvernorat addGouvernorat(Gouvernorat gouvernorat);
	public Gouvernorat deleteGouvernorat(int id);
	public Gouvernorat updateGouvernorat(Gouvernorat gouvernorat);
	public Gouvernorat getGouvernoratById(int id);
	public List<Gouvernorat> getAllGouvernorats();
}