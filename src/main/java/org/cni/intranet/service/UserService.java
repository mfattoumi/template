package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.User;

public interface UserService {
	public User addUser(User user);
	public User deleteUser(int id);
	public User updateUser(User user);
	public User getUserById(int id);
	public List<User> getAllUser();
}
