package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Function;

public interface FunctionDao {
	public Function addFunction(Function function);
	public Function deleteFunction(int id);
	public Function updateFunction(Function function);
	public Function getFunctionById(int id);
	public List<Function> getAllFunctions();
}