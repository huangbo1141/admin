<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	try {
%>
<jsp:include page="../common/${pageTerm}/dialog_${pageSubTerm}.jsp" />
<%
	} catch (Exception e) {
		//out.println("An exception occurred: " + e.getMessage());
	}
%>

<div id="DeleteModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<img src="/admin/img/modalclose.png" />
				</button>
				<h4 class="modal-title">Are you sure you want to delete?</h4>
			</div>
			<form id='FormDeleteModal'>
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