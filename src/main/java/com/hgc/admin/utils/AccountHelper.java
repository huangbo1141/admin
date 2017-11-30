package com.hgc.admin.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgc.admin.database.model.*;
import com.hgc.admin.model.LeftMenu;

public class AccountHelper {

	private int id;
	private String username;
	private String password;
	private AdminUser account;
	private AdminRole adminRole;
	private HashMap<Integer, Menu> map_menu;
	private List<LeftMenu> list_lmenu;
	
	private HashMap<Integer, MenuAction> map_menu_action;
	private LeftMenu curMenu;
	private LeftMenu subMenu;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean hasLogin() {
		try {
			if (username.length() > 0 && password.length() > 0 && this.id > 0) {

				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public void logout() {
		this.username = null;
		this.password = null;
	}

	public boolean checkPermission(BaseHelperImpl baseHelper) {
		if (hasLogin()) {
			// Hibernate.initialize(this.account.getElement());
			try {
				try {
					if (this.account.getId() != this.id) {
						this.account = baseHelper.adminUserService.getAdminUserById(this.id);
					}
				} catch (Exception ex) {
					this.account = baseHelper.adminUserService.getAdminUserById(this.id);
				}
				try {
					if (this.adminRole.getId() != this.account.getRole()) {
						this.adminRole = baseHelper.adminRoleService.getAdminRoleById(this.account.getRole());
					}
				} catch (Exception ex) {
					
					this.adminRole = baseHelper.adminRoleService.getAdminRoleById(this.account.getRole());
				}
				System.out.println(this.account.getRole());
				this.map_menu = getAllMenu(baseHelper);
				this.map_menu_action = this.parseMenuActions(baseHelper);
				this.list_lmenu = parseRoles(this.adminRole,this.map_menu);

				return true;
			} catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	public String checkTerm(String term_sum) {
		Iterator it = map_menu.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			Integer key = (Integer) pair.getKey();
			Menu menu = (Menu) pair.getValue();
			String cterm = "";
			if (menu.getParent() == 0) {
				cterm = menu.getTerm()+"_"+menu.getTerm();
			} else {
				Menu parent_menu = map_menu.get(menu.getParent());
				cterm = parent_menu.getTerm() + "_" + menu.getTerm();
			}
			if (cterm.equals(term_sum)) {
				// allowed menu
				// in this case menu is the one for this term_sum
				// {term}/{subterm}
				for (int i = 0; i < this.list_lmenu.size(); i++) {
					LeftMenu lm = list_lmenu.get(i);
					if (lm.getMenu().getId() == menu.getId()) {
						// has permission
						this.curMenu = lm;
						this.subMenu = null;
						return "ok";
					} else {
						for (LeftMenu submenu : lm.getSubmenu()) {
							if (submenu.getMenu().getId() == menu.getId()) {
								// has permission
								this.curMenu = lm;
								this.subMenu = submenu;
								return "ok";
							}
						}
					}
				}
				return "no_permission";
			}
		}
		return "invalid";
	}

	public static HashMap<Integer, Menu> getAllMenu(BaseHelperImpl baseHelper) {
		HashMap<Integer, Menu> ret = new HashMap<Integer, Menu>();
		List<Menu> list = baseHelper.menuService.listMenus();
		
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				Menu mn = list.get(i);
				ret.put(mn.getId(), mn);
			}

		return ret;
	}
	

	public HashMap<Integer, MenuAction> parseMenuActions(BaseHelperImpl baseHelper) {
		HashMap<Integer, MenuAction> ret = new HashMap<Integer, MenuAction>();
		List<MenuAction> list = baseHelper.menuActionService.listMenuActions();
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				MenuAction mn = list.get(i);
				ret.put(mn.getId(), mn);
			}

		return ret;
	}

	public static List<LeftMenu> parseRoles(AdminRole role,HashMap<Integer, Menu> map_menu) {
		//
		int flag = -1;
		try {
			String role_str = role.getRoles();
			if (role_str.equals("0")) {
				// super admin
				flag = 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		List<LeftMenu> pm_menu_list = new ArrayList<LeftMenu>();
		try {
			if (flag == 0) {
				// super admin
				Iterator it = map_menu.entrySet().iterator();
				HashMap<Integer, LeftMenu> hash = new HashMap<Integer, LeftMenu>();
				while (it.hasNext()) {
					HashMap.Entry pair = (HashMap.Entry) it.next();
					Integer key = (Integer) pair.getKey();
					Menu menu = (Menu) pair.getValue();
					if (menu.getParent() != 0) {
						LeftMenu lm = new LeftMenu();
						lm.setMenu(menu);
						lm.setMid(menu.getId());
						Menu parent = map_menu.get(menu.getParent());

						if (hash.containsKey(parent.getId())) {
							LeftMenu lm_parent = hash.get(parent.getId());
							lm_parent.getSubmenu().add(lm);
						} else {
							LeftMenu lm_parent = new LeftMenu();
							lm_parent.setMenu(parent);
							lm_parent.setMid(parent.getId());
							lm_parent.getSubmenu().add(lm);
							hash.put(parent.getId(), lm_parent);
						}

						try {
							ObjectMapper objectMapper = new ObjectMapper();
							List<Integer> acs = objectMapper.readValue(menu.getAction(),
									new TypeReference<List<Integer>>() {
									});

							lm.setActions(acs);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				it = map_menu.entrySet().iterator();
				while (it.hasNext()) {
					HashMap.Entry pair = (HashMap.Entry) it.next();
					Integer key = (Integer) pair.getKey();
					Menu menu = (Menu) pair.getValue();
					if (menu.getParent() == 0) {
						LeftMenu lm = null;
						if (hash.containsKey(menu.getId())) {
							// inside hash key
							lm = hash.get(menu.getId());
						} else {
							lm = new LeftMenu();
							lm.setMenu( menu);
							lm.setMid(menu.getId());
						}
						try {
							ObjectMapper objectMapper = new ObjectMapper();
							List<Integer> acs = objectMapper.readValue(menu.getAction(),
									new TypeReference<List<Integer>>() {
									});

							lm.setActions(acs);
						} catch (Exception e) {
							e.printStackTrace();
						}
						pm_menu_list.add(lm);
					}
				}

			} else {
				// parsing the role

				try {
					ObjectMapper objectMapper = new ObjectMapper();
					List<LeftMenu> list_menu = objectMapper.readValue(role.getRoles(),
							new TypeReference<List<LeftMenu>>() {
							});
					HashMap<Integer, LeftMenu> hash = new HashMap<Integer, LeftMenu>();

					for (int i = 0; i < list_menu.size(); i++) {
						LeftMenu lm_menu = list_menu.get(i);
						Menu menu = map_menu.get(lm_menu.getMid());
						lm_menu.setMenu(menu);
						
						// check if list_menu has same as lm_menu
						
						if (menu.getParent() == 0) {
							// parent menu
							hash.put(menu.getId(), lm_menu);
						}
					}

					for (int i = 0; i < list_menu.size(); i++) {
						LeftMenu lm_menu = list_menu.get(i);
						Menu menu = map_menu.get(lm_menu.getMid());
						lm_menu.setMenu( menu);
						// check if list_menu has same as lm_menu

						if (menu.getParent() != 0) {
							// sub menu
							Menu parent_menu = map_menu.get(menu.getParent());
							LeftMenu i_lm = null;
							if (hash.containsKey(parent_menu.getId())) {
								i_lm = hash.get(parent_menu.getId());
								i_lm.getSubmenu().add(lm_menu);
							} else {
								i_lm = new LeftMenu();
								i_lm.setMenu(parent_menu);
								i_lm.setMid(parent_menu.getId());
								i_lm.getSubmenu().add(lm_menu);
								hash.put(parent_menu.getId(), i_lm);
							}
						}
					}
					pm_menu_list.addAll(hash.values());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {

		}

		// sort
		Collections.sort(pm_menu_list, new Comparator<LeftMenu>() {
			@Override
			public int compare(LeftMenu lhs, LeftMenu rhs) {
				// -1 - less than, 1 - greater than, 0 - equal, all inversed for
				// descending
				return rhs.getMenu().getSort() > lhs.getMenu().getSort() ? -1 : (rhs.getMenu().getSort() < lhs.getMenu().getSort()) ? 1 : 0;
			}
		});

		return pm_menu_list;

	}

	public AdminUser getAdminUser() {
		return account;
	}

	public void setAdminUser(AdminUser account) {
		this.account = account;
	}

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}

	public HashMap<Integer, Menu> getMap_menu() {
		return map_menu;
	}

	public void setMap_menu(HashMap<Integer, Menu> map_menu) {
		this.map_menu = map_menu;
	}

	public List<LeftMenu> getList_lmenu() {
		return list_lmenu;
	}

	public void setList_lmenu(List<LeftMenu> list_lmenu) {
		this.list_lmenu = list_lmenu;
	}

	public HashMap<Integer, MenuAction> getMap_menu_action() {
		return map_menu_action;
	}

	public void setMap_menu_action(HashMap<Integer, MenuAction> map_menu_action) {
		this.map_menu_action = map_menu_action;
	}

	public LeftMenu getCurMenu() {
		return curMenu;
	}

	public void setCurMenu(LeftMenu curMenu) {
		this.curMenu = curMenu;
	}

	public AdminUser getAccount() {
		return account;
	}

	public void setAccount(AdminUser account) {
		this.account = account;
	}

	public LeftMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(LeftMenu subMenu) {
		this.subMenu = subMenu;
	}

	
}
