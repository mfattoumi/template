package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Domain;

public interface DomainDao {
	public Domain addDomain(Domain domain);
	public Domain deleteDomain(int id);
	public Domain updateDomain(Domain domain);
	public Domain getDomainById(int id);
	public List<Domain> getAllDomains();
}