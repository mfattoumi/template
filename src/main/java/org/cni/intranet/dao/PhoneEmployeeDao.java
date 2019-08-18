package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.PhoneEmployee;

public interface PhoneEmployeeDao {
	public PhoneEmployee addPhoneEmployee(PhoneEmployee phoneEmployee);
	public PhoneEmployee deletePhoneEmployee(int id);
	public PhoneEmployee updatePhoneEmployee(PhoneEmployee phoneEmployee);
	public PhoneEmployee getPhoneEmployeeById(int id);
	public List<PhoneEmployee> getAllPhoneEmployees();
}