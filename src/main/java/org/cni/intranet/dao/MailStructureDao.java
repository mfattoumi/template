package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.MailStructure;

public interface MailStructureDao {
	public MailStructure addMailStructure(MailStructure mailStructure);
	public MailStructure deleteMailStructure(int id);
	public MailStructure updateMailStructure(MailStructure mailStructure);
	public MailStructure getMailStructureById(int id);
	public List<MailStructure> getAllMailStructures();
}