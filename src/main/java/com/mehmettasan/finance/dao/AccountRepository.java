package com.mehmettasan.finance.dao;

import java.util.List;
import com.mehmettasan.finance.model.Account;

public interface AccountRepository {
	List<Account> findAll();
	Account findById(Long id);
	List<Account> findByUserId(Long ownerId);
	void create(Account account);
}
