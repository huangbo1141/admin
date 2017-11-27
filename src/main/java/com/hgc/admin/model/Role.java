package com.hgc.admin.model;

import java.util.ArrayList;
import java.util.List;

public class Role {
	private Integer mid;
	private List<Integer> actions = new ArrayList<Integer>();
	
	
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public List<Integer> getActions() {
		return actions;
	}
	public void setActions(List<Integer> actions) {
		this.actions = actions;
	}
	
	
}
