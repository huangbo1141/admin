package com.hgc.admin.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hgc.admin.database.service.AccountService;

@Component
public class UserHelper extends BaseHelper {
	
	@Override
	public boolean fakeLogin() {
		return false;
	}
	
}
