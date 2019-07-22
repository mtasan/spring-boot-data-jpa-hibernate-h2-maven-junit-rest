package com.mehmettasan.finance.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="t_account")
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="financeSeqGen")
	@SequenceGenerator(name="financeSeqGen",sequenceName="finance_sequence")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="balance")
	private BigDecimal balance;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", balance=" + balance + ", user=" + user + "]";
	}
	
	
}
