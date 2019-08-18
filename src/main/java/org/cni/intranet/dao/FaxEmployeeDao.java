package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.FaxEmployee;

public interface FaxEmployeeDao {
	public FaxEmployee addFaxEmployee(FaxEmployee faxEmployee);
	public FaxEmployee deleteFaxEmployee(int id);
	public FaxEmployee updateFaxEmployee(FaxEmployee faxEmployee);
	public FaxEmployee getFaxEmployeeById(int id);
	public List<FaxEmployee> getAllFaxEmployees();
}