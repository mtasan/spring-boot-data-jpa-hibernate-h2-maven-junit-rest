package com.mehmettasan.finance.dao;


import com.mehmettasan.finance.model.User;

public interface UserRepository {
	User findById(Long id);
}
