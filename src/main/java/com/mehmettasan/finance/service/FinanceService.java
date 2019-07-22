package com.mehmettasan.finance.service;

import java.util.List;

import com.mehmettasan.finance.exception.AccountNotFoundException;
import com.mehmettasan.finance.model.Account;
public interface FinanceService {
	List<Account> findAccount();
	void createAccount(Account account);
	Account findAccount(Long id) throws AccountNotFoundException;

}
