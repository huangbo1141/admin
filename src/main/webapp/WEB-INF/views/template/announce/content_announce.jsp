<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="com.hgc.admin.database.model.*"%>
<div class="content-wrapper">
	<div class=row>
		<div class="col-md-12">
			<button type="button" name="new" class="NewBTN ac_new"
				data-toggle="modal">新建</button>
		</div>

		<div class="col-md-12">
			<div class="tablediv">
				<table class="table custometable">
					<thead>
						<tr>
							<th>时间</th>
							<th>内容</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageData.list_data}" var="row">
							<tr>
								<td>${row.time.substring(0,10)}</td>
								<td>${row.content}</td>
								<%
									String json = "";
										try {
											
											ObjectMapper mapper = (ObjectMapper) request.getAttribute("mapper");
											Announce row = (Announce)pageContext.getAttribute("row");
											if(mapper == null){
												out.print("mapper null");
											}
											if(row == null){
												out.print("row null");
											}
											//row.setContent("");
											json = mapper.writeValueAsString(row);
											pageContext.setAttribute("json", json);
											//out.print(json);
										} catch (Exception ex) {
											//out.print(ex.toString());
										}
								%>
								
								<td class="col-xs-2" data-data='<c:out value="${json}"/>'>
									<button type="submit" name="delete" class="ac_delete dltbtn"
										data-toggle="modal" >删除</button>
								</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
				
			</div>

		</div>
	</div>
</div>
