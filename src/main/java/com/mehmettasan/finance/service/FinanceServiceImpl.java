package com.mehmettasan.finance.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.mehmettasan.finance.dao.AccountRepository;
import com.mehmettasan.finance.dao.UserRepository;
import com.mehmettasan.finance.exception.AccountNotFoundException;
import com.mehmettasan.finance.exception.InternalServerException;
import com.mehmettasan.finance.model.Account;
import com.mehmettasan.finance.model.User;

@Service
public class FinanceServiceImpl implements FinanceService {
	
	
	private UserRepository userRepository;
	
	private AccountRepository accountRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Account> findAccount() {
		return accountRepository.findAll();
	}

	@Override
	@Transactional
	public void createAccount(Account account) {
		if(checkAccountOpeningTime()) {//checkTime
			Long userid = account.getUser().getId();
			User user = userRepository.findById(userid);
			if (user !=null) {
				List<Account> listAccount = accountRepository.findByUserId(userid);
				if(listAccount.size()==0) {
					accountRepository.create(account);
				}else {
					throw new InternalServerException("User has already account " + listAccount.get(0).getName());
				}
			}else {
				throw new InternalServerException("There is not any user with userid " + userid);
			}
		}else {
			throw new InternalServerException("Accout may open between 08:00 and 17:00");
		}
	}
	
	private boolean checkAccountOpeningTime() {//check Account Opening Time
		boolean result = false;
		LocalTime now = LocalTime.now();
		int currentHour = now.getHour();
		if(currentHour>=8 && currentHour <17) {
			result = true;
		}
		return result;
	}

	@Override
	public Account findAccount(Long id) throws AccountNotFoundException {
		Account account = accountRepository.findById(id);
		if(account == null) {
			throw new AccountNotFoundException("Account not found with id :" + id);
		}
		return account;
	}

}
