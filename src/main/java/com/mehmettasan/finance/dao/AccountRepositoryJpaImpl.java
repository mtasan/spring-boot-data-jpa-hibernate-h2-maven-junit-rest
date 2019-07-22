package com.mehmettasan.finance.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import com.mehmettasan.finance.model.Account;
@Repository("accountRepository")
public class AccountRepositoryJpaImpl implements AccountRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Account> findAll() {
		return entityManager.createQuery("from Account", Account.class).getResultList();
	}

	@Override
	public List<Account> findByUserId(Long userId) {
		return entityManager.createQuery("from Account where user.id = :userid", Account.class)
				.setParameter("userid", userId).getResultList();
	}

	@Override
	public void create(Account account) {
		entityManager.persist(account);

	}

	@Override
	public Account findById(Long id) {
		return entityManager.find(Account.class, id);
	}

}
