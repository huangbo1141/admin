<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div id="EditModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="resources/img/modalclose.png" />
				</button>
				<h4 class="modal-title">添加菜单</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">菜单名称*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" id=""
									placeholder="" name="">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">父菜单*</label>
							<div class="col-sm-10">
								<div class="row">
									<div class="col-md-6">
										<select name="ParentMenu" class="form-control inputstyle">
											<option>选择菜单</option>
											<option>系统管理</option>
											<option>注册管理</option>
											<option>客户档案</option>
											<option>油柜档案</option>
										</select>
									</div>
									<div class="col-md-6">
										<button class="setbtn">设置为顶级菜单</button>
									</div>
								</div>


							</div>
						</div>

					</div>

					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">URL*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2" for="email">序号*</label>
							<div class="col-sm-10">
								<input type="text" style="margin-bottom: 0;"
									class="form-control inputstyle">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="modlbtn mdl-sv" data-dismiss="modal">保存</button>

				<button type="button" class="modlbtn mdl-cancle"
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
					<img src="resources/img/modalclose.png" />
				</button>
				<h4 class="modal-title">添加菜单</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">菜单名称*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" id=""
									placeholder="" name="">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">父菜单*</label>
							<div class="col-sm-10">
								<div class="row">
									<div class="col-md-6">
										<select name="ParentMenu" class="form-control inputstyle">
											<option>选择菜单</option>
											<option>系统管理</option>
											<option>注册管理</option>
											<option>客户档案</option>
											<option>油柜档案</option>
										</select>
									</div>
									<div class="col-md-6">
										<button class="setbtn">设置为顶级菜单</button>
									</div>
								</div>


							</div>
						</div>

					</div>

					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">URL*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2" for="email">序号*</label>
							<div class="col-sm-10">
								<input type="text" style="margin-bottom: 0;"
									class="form-control inputstyle">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="modlbtn mdl-sv" data-dismiss="modal">保存</button>

				<button type="button" class="modlbtn mdl-cancle"
					data-dismiss="modal">取消</button>
			</div>
		</div>

	</div>
</div>
<div id="DeleteModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="resources/img/modalclose.png" />
				</button>
				<h4 class="modal-title">Are you sure you want to delete?</h4>
			</div>

			<div class="modal-footer">
				<button type="button" class="modlbtn mdl-sv" data-dismiss="modal">保存</button>

				<button type="button" class="modlbtn mdl-cancle"
					data-dismiss="modal">取消</button>
			</div>
		</div>

	</div>
</div>