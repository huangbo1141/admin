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
				<h4 class="modal-title">新建</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
				<form id='FormEditModal'>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">时间*</label>
							<div class="col-sm-10">
								<input type="text" class="inputstyle datetimepicker" 
									placeholder="" name="time">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">内容</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" id=""
									placeholder="" name="content">
							</div>
						</div>
					</div>
					<div class="col-md-12" style="display:none">
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
					<img src="/admin/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
				<form id='FormNewModal'>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">时间*</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle datetimepicker" 
									placeholder="" name="time">
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-sm-2">内容</label>
							<div class="col-sm-10">
								<input type="text" class="form-control inputstyle" id=""
									placeholder="" name="content">
							</div>
						</div>
					</div>
					<div class="col-md-12" style="display:none">				
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
