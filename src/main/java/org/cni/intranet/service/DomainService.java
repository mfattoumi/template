package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Domain;

public interface DomainService {
	public Domain addDomain(Domain domain);
	public Domain deleteDomain(int id);
	public Domain updateDomain(Domain domain);
	public Domain getDomainById(int id);
	public List<Domain> getAllDomains();
}
