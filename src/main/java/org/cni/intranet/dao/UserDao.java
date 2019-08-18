package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.User;

public interface UserDao {
	public User addUser(User user);
	public User deleteUser(int id);
	public User updateUser(User user);
	public User getUserById(int id);
	public List<User> getAllUser();
}