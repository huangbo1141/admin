<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="EditLineModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建生产线</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormEditLineModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">生产线名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">序号*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="sort">
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
<div id="NewLineModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建生产线</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormNewLineModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">生产线名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">序号*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="sort">
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

<div id="EditDanModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建工段</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormEditDanModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">工段名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">序号*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="sort">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">生产线*</label>
								<div class="col-sm-10">
									<select name="line" class="form-control inputstyle">
										<c:forEach items="${pageData.list_line}" var="line">
											<option value='${line.id}'>${line.name}</option>
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
<div id="NewDanModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">编辑工段</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormNewDanModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">工段名称*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="name">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">序号*</label>
								<div class="col-sm-10">
									<input type="text" class="form-control inputstyle" id=""
										placeholder="" name="sort">
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">生产线*</label>
								<div class="col-sm-10">
									<select name="line" class="form-control inputstyle">
										<c:forEach items="${pageData.list_line}" var="line">
											<option value='${line.id}'>${line.name}</option>
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

<div id="DeleteLineModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">Are you sure you want to delete?</h4>
			</div>
			<form id='FormDeleteLineModal'>
				<div class="col-md-12" style="display: none">
					<input type="text"
						class="form-control inputstyle" name="id" value="">
				</div>
			</form>
			<div class="modal-footer">
				<button type="button" class="okbtn modlbtn mdl-sv"
					data-dismiss="modal">保存</button>

				<button type="button" class="cancel modlbtn mdl-cancle"
					data-dismiss="modal">取消</button>
			</div>
		</div>

	</div>
</div>
<div id="DeleteDanModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/${rootdir}/img/modalclose.png" />
				</button>
				<h4 class="modal-title">Are you sure you want to delete?</h4>
			</div>
			<form id='FormDeleteDanModal'>
				<div class="col-md-12" style="display: none">
					<input type="text"
						class="form-control inputstyle" name="id" value="">
				</div>
			</form>
			<div class="modal-footer">
				<button type="button" class="okbtn modlbtn mdl-sv"
					data-dismiss="modal">保存</button>

				<button type="button" class="cancel modlbtn mdl-cancle"
					data-dismiss="modal">取消</button>
			</div>
		</div>

	</div>
</div>

