<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.hgc.admin.database.model.AdminUser"%>
<div class="content-wrapper">
	<div class=row>
		<div class="col-md-12">
			<div class="col-md-2">
				<button type="button" name="new" class="NewBTN ac_new"
					data-toggle="modal">新建</button>
			</div>
		</div>

		<div class="col-md-12">
			<div class="tablediv">
				<ul class="nav dess-nav-tabs nav-tabs">
					<c:set var="test" value="${pageData.tabIndex}" />
					<%
						Integer index = 0;
						Integer tabIndex = (Integer) pageContext.getAttribute("test");
					%>
					<c:forEach items="${pageData.list_tab}" var="tab">
						<%
							String active = "";
								if (index == tabIndex) {
									active = "active";
								}
						%>
						<li class="<%=active%>"><a data-toggle="tab"
							href='#menu${tab.get("tab").id}'>${tab.get("tab").name}</a></li>
						<%
							index = index + 1;
						%>
					</c:forEach>
				</ul>

				<div class="tab-content">
					<%
						index = 0;
					%>
					<c:forEach items="${pageData.list_tab}" var="tab">
						<%
							String active = "";
								if (index == tabIndex) {
									active = "in active";
								}
						%>
						<div id='menu${tab.get("tab").id}'
							class="tab-pane fade <%=active%>">
							<table class="table custometable">
								<thead>
									<tr>
										<th>序号</th>
										<th>原因描述</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items='${tab.get("list")}' var='item'>
										<%
											Object item = pageContext.getAttribute("item");
													String json = "";
													try {
														ObjectMapper mapper = (ObjectMapper) request.getAttribute("mapper");
														json = mapper.writeValueAsString(item);
														//out.print(json);
													} catch (Exception ex) {
														//out.print(ex.toString());
													}
										%>
										<tr>
											<td>${item.name}</td>
											<td>${item.desc}</td>
											<td class="col-xs-2" data-data='<%=json%>' data-tab='${tab.get("tab").id}'>
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
						<%
							index = index + 1;
						%>
					</c:forEach>
				</div>


			</div>

		</div>
	</div>
</div>
<!-- .content-wrapper -->
