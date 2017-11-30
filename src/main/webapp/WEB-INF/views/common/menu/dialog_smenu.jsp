<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="EditModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">添加菜单</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
				<form id='FormEditModal'>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">菜单名称*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" id=""
									placeholder="" name="name">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">父菜单*</label>
							<div class="col-sm-10">
								<div class="row">
									<div class="col-md-6">
										<select name="parent" class="form-control inputstyle">
											<c:forEach items="${currentUser.list_lmenu}" var="lm">
												<option value='${lm.menu.id}'>${lm.menu.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-6">
										<input type="checkbox" name="menu_level" disabled>
										<label for="menu_level">设置为顶级菜单</label>
									</div>
								</div>


							</div>
						</div>

					</div>

					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">URL*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" name="term">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2" for="email">序号*</label>
							<div class="col-sm-10">
								<input type="text" style="margin-bottom: 0;"
									class="form-control inputstyle" name="sort">
							</div>
						</div>
					</div>
					<div class="col-md-12" style="display:none">
						<input type="text" 	class="form-control inputstyle non_clear" name="action" value="[1,2,3]">
						<input type="text" 	class="form-control inputstyle" name="id" value="">					
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
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">添加菜单</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
				<form id='FormNewModal'>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">菜单名称*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" id=""
									placeholder="" name="name">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">父菜单*</label>
							<div class="col-sm-10">
								<div class="row">
									<div class="col-md-6">
										<select name="parent" class="form-control inputstyle">
											<c:forEach items="${currentUser.list_lmenu}" var="lm">
												<option value='${lm.menu.id}'>${lm.menu.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-6">
										<input type="checkbox" name="menu_level">
										<label for="menu_level">设置为顶级菜单</label>
									</div>
								</div>


							</div>
						</div>

					</div>

					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">URL*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" name="term">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2" for="email">序号*</label>
							<div class="col-sm-10">
								<input type="text" style="margin-bottom: 0;"
									class="form-control inputstyle" name="sort">
							</div>
						</div>
					</div>
					<div class="col-md-12" style="display:none">
						<div class="form-group">
							<div class="col-sm-10">
								<input type="text" style="margin-bottom: 0;"
									class="form-control inputstyle non_clear" name="action" value="[1,2,3]">
							</div>
						</div>
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
