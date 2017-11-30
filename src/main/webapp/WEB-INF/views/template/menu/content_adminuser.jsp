<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.hgc.admin.database.model.AdminUser"%>
<div class="content-wrapper">
	<div class=row>
		<div class="col-md-12">
			<button type="button" name="new" class="ac_new NewBTN">新建</button>
		</div>
		<div class="col-md-12">
			<select id="admin_role" name="admin_role" class="form-control inputstyle">
				<c:forEach items="${pageData.list_role}" var="row">
					<c:choose>
						<c:when test="${row.id == pageData.model_role.id}">
							<option value='${row.id}' selected>${row.name}</option>
						</c:when>
						<c:otherwise>
							<option value='${row.id}'>${row.name}</option>
						</c:otherwise>
					</c:choose>

				</c:forEach>
			</select>
		</div>

		<div class="col-md-12">
			<div class="tablediv" style="margin-top: 8px">
				<table class="table custometable">
					<thead>
						<tr>
							<th>姓名</th>
							<th>联系电话</th>
							<th>最近登录时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageData.list_adminuser}" var='item'>
							<%
								AdminUser user = (AdminUser) pageContext.getAttribute("item");
									String t1, t2 = "";
									if (user.getDeleted() == 0) {
										t1 = "disabled";
										t2 = "";
									} else {
										t1 = "";
										t2 = "disabled";
									}
									String json = "";
									try {
										ObjectMapper mapper = (ObjectMapper) request.getAttribute("mapper");
										json = mapper.writeValueAsString(user);
										//out.print(json);
									} catch (Exception ex) {
										//out.print(ex.toString());
									}
							%>
							<tr>
								<td>${item.name}</td>
								<td>${item.phone}</td>
								<td>${item.log_time}</td>
								<td class="col-xs-2" data-data='<%=json%>'>
									<button type="submit" name="edit" class="editbtn ac_enable"
										data-toggle="modal" <%=t1%>>启用</button>
									<button type="submit" name="edit" class="editbtn ac_disable"
										data-toggle="modal" <%=t2%>>停用</button>
									<button type="submit" name="delete" class="dltbtn ac_delete"
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
<!-- .content-wrapper -->
