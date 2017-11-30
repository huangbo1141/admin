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
				<h4 class="modal-title">新建工位</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormEditModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">开始时间*</label>
								<div class="col-sm-10">
									<input type="text" class="datepicker2 form-control inputstyle" id=""
										placeholder="" name="start_t">
								</div>
							</div>
						</div>	
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">结束时间*</label>
								<div class="col-sm-10">
									<input type="text" class="datepicker2 form-control inputstyle" id=""
										placeholder="" name="end_t">
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

<div id="ViewModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/admin/img/modalclose.png" />
				</button>
				<h4 class="modal-title">新建工位</h4>
			</div>
			<div class="modal-body">
				<div class="row dlg_param" data-url="sssss" data-params="ddddd">
					<form id='FormViewModal'>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">工单日期</label>
								<label class="control-label col-sm-2" >2017-10-16</label>
							</div>
						</div>	
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">工位</label>
								<label class="control-label col-sm-2" >P-CH-120.2</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">记录人</label>
								<label class="control-label col-sm-2" >XXX</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">记录时间</label>
								<label class="control-label col-sm-2" >6:30</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">开始时间</label>
								<label class="control-label col-sm-2" >6:00</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">结束时间</label>
								<label class="control-label col-sm-2" >30MIN</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">损失时间</label>
								<label class="control-label col-sm-2" >A 设备原因</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">故障原因</label>
								<label class="control-label col-sm-2" >不知道怎么了就是不运行了</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">问题描述</label>
								<label class="control-label col-sm-2" ></label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">原因描述</label>
								<label class="control-label col-sm-2" >改造后造成的停机 (Downtime caused by modification)</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">反馈</label>
								<label class="control-label col-sm-2" >改造后造成的停机 (Downtime caused by modification)</label>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-sm-2">XXX指派工单给JAC</label>
								<label class="control-label col-sm-2" >2017-10-24 6:30</label>
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