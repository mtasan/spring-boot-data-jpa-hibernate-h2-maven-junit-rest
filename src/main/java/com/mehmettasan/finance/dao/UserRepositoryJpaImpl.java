package com.mehmettasan.finance.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mehmettasan.finance.model.User;
@Repository("userRepository")
public class UserRepositoryJpaImpl implements UserRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User findById(Long id) {
		return entityManager.find(User.class, id);
	}

}
