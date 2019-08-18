package org.cni.intranet.service;
/**/
import java.util.List;

import org.cni.intranet.entities.Delegation;

public interface DelegationService {
	public Delegation addDelegation(Delegation delegation);
	public Delegation deleteDelegation(int id);
	public Delegation updateDelegation(Delegation delegation);
	public Delegation getDelegationById(int id);
	public List<Delegation> getAllDelegations();
}