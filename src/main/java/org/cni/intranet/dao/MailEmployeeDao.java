package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.MailEmployee;

public interface MailEmployeeDao {
	public MailEmployee addMailEmployee(MailEmployee mailEmployee);
	public MailEmployee deleteMailEmployee(int id);
	public MailEmployee updateMailEmployee(MailEmployee mailEmployee);
	public MailEmployee getMailEmployeeById(int id);
	public List<MailEmployee> getAllMailEmployees();
}