<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.util.HashMap"%>
<div class="content-wrapper">
	<div class=row></div>
	<div class=row>
		<div class="col-md-12">
			<form id='FormSearchModal' method='post'>
				<div class="form-group">
					<input type="text" class="" id=""	placeholder="Search Key" name="s" value="${pageData.s}">
					<button type="submit" name="submit" class="">Search</button>
				</div>
			</form>
		</div>
		<div class="col-md-2">
			<button type="button" name="new" class="NewBTN ac_new"
				data-toggle="modal">新建</button>
		</div>
		<div class="col-md-12">
			<div class="tablediv">
				<table class="table custometable">
					<thead>
						<tr>
							<th>姓名</th>
							<th>工号</th>
							<th>平台权限</th>
							<th>部门</th>
							<th>生产线</th>
							<th>工段</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageData.list_data}" var="row">
							<tr>
								<td>${row.get("model").name}</td>
								<td>${row.get("model").serial}</td>
								<td>${row.get("userrole").name}</td>
								<td>${row.get("userpart").name}</td>
								<td>${row.get("line").name}</td>
								<td>${row.get("dan").name}</td>
								<%
									String json = "";
										String roles = "";
										try {
											ObjectMapper mapper = (ObjectMapper) request.getAttribute("mapper");
											HashMap row = (HashMap) pageContext.getAttribute("row");
											if (mapper == null) {
												out.print("mapper null");
											}
											if (row == null) {
												out.print("row null");
											}
											json = mapper.writeValueAsString(row.get("model"));
											//out.print(json);
										} catch (Exception ex) {
											//out.print(ex.toString());
										}
								%>

								<td class="col-xs-2" data-data='<%=json%>'>
									<button type="submit" name="edit" class="ac_modify editbtn"
										data-toggle="modal">编辑</button>
									<button type="submit" name="delete" class="ac_delete dltbtn"
										data-toggle="modal">删除</button>
								</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>

			</div>

		</div>
	</div>
</div>
