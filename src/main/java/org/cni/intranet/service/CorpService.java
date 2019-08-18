package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Corp;

public interface CorpService {
	public Corp addCorp(Corp corp);
	public Corp deleteCorp(int id);
	public Corp updateCorp(Corp corp);
	public Corp getCorpById(int id);
	public List<Corp> getAllCorps();
}
