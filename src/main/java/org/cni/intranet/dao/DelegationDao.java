package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Delegation;

public interface DelegationDao {
	public Delegation addDelegation(Delegation delegation);
	public Delegation deleteDelegation(int id);
	public Delegation updateDelegation(Delegation delegation);
	public Delegation getDelegationById(int id);
	public List<Delegation> getAllDelegations();
}