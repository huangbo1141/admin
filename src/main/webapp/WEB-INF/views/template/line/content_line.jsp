<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.hgc.admin.database.model.AdminUser"%>
<div class="content-wrapper">
	<div class=row>
		<div class="col-md-12">
			<div class="col-md-4" data-modeltype='Line'>
				<button type="button" name="new" class="NewBTN ac_new"
					data-toggle="modal">新建生产线</button>
			</div>
			<div class="col-md-4" data-modeltype='Dan'>
				<button type="button" name="new" class="NewBTN ac_new"
					data-toggle="modal">新建工段</button>
			</div>
		</div>

		<div class="col-md-12">
			<div class="tablediv hgctab">
				<ul class="nav dess-nav-tabs nav-tabs">
					<c:set var="test" value="${pageData.tabIndex}" />
					<%-- 				<c:out value="${pageData.tabIndex}"/> --%>
					<%
						Integer tabIndex = (Integer) pageContext.getAttribute("test");

						String t1 = "active", t2 = "";
						if (tabIndex == 1) {
							t1 = "";
							t2 = "active";
						}
					%>
					<li class="<%=t1%>"><a data-toggle="tab" href="#menu1">生产线</a></li>
					<li class="<%=t2%>"><a data-toggle="tab" href="#menu2">工段</a></li>
				</ul>
				<%
					t1 = "in active";
					t2 = "";
					if (tabIndex == 1) {
						t1 = "";
						t2 = "in active";
					}
				%>
				<div class="tab-content">
					<div id="menu1" class="tab-pane fade <%=t1%>">
						<table class="table custometable">
							<thead>
								<tr>
									<th>#</th>
									<th>序号</th>
									<th>生产线名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageData.list_line}" var='item'>
									<%
										Object item = pageContext.getAttribute("item");
											String json = "";
											try {
												ObjectMapper mapper = (ObjectMapper) request.getAttribute("mapper");
												json = mapper.writeValueAsString(item);
												//out.print(json);
											} catch (Exception ex) {
												//out.print(ex.toString());
												json = "";
											}
									%>
									<tr>
										<td>${item.id}</td>
										<td>${item.sort}</td>
										<td>${item.name}</td>
										<td class="col-xs-2" data-data='<%=json%>'
											data-modeltype='Line'>
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
					<div id="menu2" class="tab-pane fade <%=t2%>">
						<table class="table custometable table-bordered">
							<thead>
								<tr>
									<th>#</th>
									<th>序号</th>
									<th>生产线</th>
									<th>工段</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageData.list_data}" var='row'>
									<%
										String json = "";
											String line = "";
											try {
												ObjectMapper mapper = (ObjectMapper) request.getAttribute("mapper");
												HashMap row = (HashMap) pageContext.getAttribute("row");
												if (mapper == null) {
													out.print("mapper null");
												}
												if (row == null) {
													out.print("row null");
												}
												json = mapper.writeValueAsString(row.get("dan"));
												line = mapper.writeValueAsString(row.get("line"));
												//out.print(json);
											} catch (Exception ex) {
												//out.print(ex.toString());
											}
									%>
									<tr>
										<td>${row.get("dan").id}</td>
										<td>${row.get("dan").sort}</td>
										<td>${row.get("line").name}</td>
										<td>${row.get("dan").name}</td>
										<td class="col-xs-2" data-data='<%=json%>'
											data-modeltype='Dan'>
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
	</div>
</div>
<!-- .content-wrapper -->
