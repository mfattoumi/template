package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public User addUser(User user) {
		em.persist(user);
		return user;
	}

	@Transactional
	public User deleteUser(int id) {
		User user = getUserById(id);
		em.remove(user);
		return user;
	}

	@Transactional
	public User updateUser(User user) {
		em.merge(user);
		return user;
	}

	@Transactional
	public User getUserById(int id) {
		User as = em.find(User.class, id);
		if(as==null) throw new RuntimeException("User introuvable");
		return as;
	}

	@Transactional
	public List<User> getAllUser() {
		Query req = em.createQuery("select x from User");
		return req.getResultList();
	}

}
