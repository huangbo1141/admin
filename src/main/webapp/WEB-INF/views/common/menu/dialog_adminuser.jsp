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
				<h4 class="modal-title">新建角色</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormEditModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">角色名称*</label>
								<div class="col-sm-10">
									<select name="role" class="form-control inputstyle">
										<c:forEach items="${pageData.list_role}" var="role">
											<option value='${role.id}'>${role.name}</option>
										</c:forEach>
									</select>
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
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建角色</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormNewModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">用户名*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id="username"
										placeholder="" name="username">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">密码*</label>
								<div class="col-sm-10">
									<input type="password" class="form-control inputstyle" id=""
										placeholder="" name="password">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">角色名称*</label>
								<div class="col-sm-10">
									<select name="role" class="form-control inputstyle">
										<c:forEach items="${pageData.list_role}" var="role">
											<option value='${role.id}'>${role.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">是否启用*</label>
								<div class="col-sm-5">
									<input class="non_clear" type="radio" name="deleted" value="0"> 启用<br>
									<input class="non_clear" type="radio" name="deleted" value="1">禁用<br>
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
<div id="EnableModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建角色</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormEnableModal'>
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
