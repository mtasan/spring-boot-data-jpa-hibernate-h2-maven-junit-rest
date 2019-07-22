package com.mehmettasan.finance.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mehmettasan.finance.exception.AccountNotFoundException;
import com.mehmettasan.finance.model.Account;
import com.mehmettasan.finance.service.FinanceService;

@RestController
@RequestMapping("/rest")
public class FinanceRestController {
	
	@Autowired
	private FinanceService financeService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/accounts")
	public ResponseEntity<List<Account>> getAccounts() {
		List<Account> accounts = financeService.findAccount();
		return ResponseEntity.ok(accounts);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/account")
	public ResponseEntity<String> createAccount(@RequestBody Account account) {
		try {
			financeService.createAccount(account);
			Long id = account.getId();
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/account/{id}",produces="application/json")
	public ResponseEntity<?> getAccountAsHateoasResource(@PathVariable("id") Long id) {
		try {
			Account account = financeService.findAccount(id);
			Link self = ControllerLinkBuilder.linkTo(FinanceRestController.class).slash("/account/" + id).withSelfRel();
			Link create = ControllerLinkBuilder.linkTo(FinanceRestController.class).slash("/account").withRel("create");
			Link update = ControllerLinkBuilder.linkTo(FinanceRestController.class).slash("/account/" + id).withRel("update");
			Link delete = ControllerLinkBuilder.linkTo(FinanceRestController.class).slash("/owaccountner/" + id).withRel("delete");			
			Resource<Account> resource = new Resource<Account>(account, self,create,update,delete);
			return ResponseEntity.ok(resource);
		} catch (AccountNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}
	}

}
