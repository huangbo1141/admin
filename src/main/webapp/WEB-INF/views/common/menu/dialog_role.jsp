<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="EditModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/admin/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建角色</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormEditModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">角色名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">是否启用*</label>
								<div class="col-sm-5">
									<input type="radio" name="deleted" value="0"> 启用<br>
									<input type="radio" name="deleted" value="1">禁用<br>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="display: none">
							<input type="text" class="form-control inputstyle" name="id"
								value="">
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="okbtn modlbtn mdl-sv">保存</button>

				<button type="button" class="cancel modlbtn mdl-cancle"
					data-dismiss="modal">取消</button>
			</div>
		</div>

	</div>
</div>
<div id="NewModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/admin/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建角色</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormNewModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">角色名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">是否启用*</label>
								<div class="col-sm-5">
									<input type="radio" name="deleted" value="0"> 启用<br>
									<input type="radio" name="deleted" value="1">禁用<br>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="display: none"></div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="okbtn modlbtn mdl-sv">保存</button>

				<button type="button" class="cancel modlbtn mdl-cancle"
					data-dismiss="modal">取消</button>
			</div>
		</div>

	</div>
</div>
<div id="AuthModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/admin/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建角色</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormAuthModal'>
						<div class="col-md-12">
							<div class="form-group">
								<div class="col-sm-12">
									<c:forEach items="${pageData.list_lmenu}" var="lm">
										<div class="row">
											<div class="col-md-6">
												<input type="checkbox" name="lm_${lm.menu.id}"> <label
													for="menu_level">${lm.menu.name}</label>
											</div>
										</div>
										<c:if test="${not empty lm.submenu}">
											<c:forEach items="${lm.submenu}" var="lm_s">
												<div class="row">
													<div class="col-md-6 hgc-ml-1">
														<input type="checkbox" name="lm_${lm_s.menu.id}">
														<label for="menu_level">${lm_s.menu.name}</label>
													</div>
												</div>
												<c:forEach items="${lm_s.actions}" var="actionID">
													<div class="row">
														<div class="col-md-6 hgc-ml-2">
															<input type="checkbox"
																name="role_${lm_s.menu.id}_${currentUser.map_menu_action[actionID].id}">
															<label for="menu_level">${currentUser.map_menu_action[actionID].name}</label>
														</div>
													</div>
												</c:forEach>
											</c:forEach>
										</c:if>
										<c:if test="${empty lm.submenu}">
											<c:forEach items="${lm.actions}" var="actionID">
												<div class="row">
													<div class="col-md-6 hgc-ml-1">
														<input type="checkbox"
															name="role_${lm.menu.id}_${currentUser.map_menu_action[actionID].id}">
														<label for="menu_level">${currentUser.map_menu_action[actionID].name}</label>
													</div>
												</div>
											</c:forEach>
										</c:if>
									</c:forEach>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="display: none">
							<input type="text" class="form-control inputstyle" name="id"
									value="">
						</div>
					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="okbtn modlbtn mdl-sv">保存</button>

				<button type="button" class="cancel modlbtn mdl-cancle"
					data-dismiss="modal">取消</button>
			</div>
		</div>

	</div>
</div>
