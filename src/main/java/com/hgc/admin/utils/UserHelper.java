package com.hgc.admin.utils;

import org.springframework.stereotype.Component;

@Component
public class UserHelper extends BaseHelper {
	
	@Override
	public boolean fakeLogin() {
		return false;
	}
	
}
