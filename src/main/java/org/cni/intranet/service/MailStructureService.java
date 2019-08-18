package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.MailStructure;

public interface MailStructureService {
	public MailStructure addMailStructure(MailStructure mailStructure);
	public MailStructure deleteMailStructure(int id);
	public MailStructure updateMailStructure(MailStructure mailStructure);
	public MailStructure getMailStructureById(int id);
	public List<MailStructure> getAllMailStructures();
}
