<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.util.HashMap"%>
<div class="content-wrapper">
	<div class=row>
		<div class="col-sm-12">
			<div class="col-sm-3">
				<input class="datepicker1" type="text" id="start_day" data-value="${pageData.start_day}" />
			</div>
			<div class="col-sm-3">
				<input class="datepicker1" type="text" id="end_day" data-value="${pageData.end_day}"/>
			</div>
			<div class="col-sm-3">
				<select id="line_input" name="l" class="form-control inputstyle">
					<c:forEach items="${pageData.list_line}" var="row">
						<c:choose>
							<c:when test="${row.id == pageData.model_line.id}">
								<option value='${row.id}' selected>${row.name}</option>
							</c:when>
							<c:otherwise>
								<option value='${row.id}'>${row.name}</option>
							</c:otherwise>
						</c:choose>

					</c:forEach>
				</select>
			</div>
		</div>
		<div class="col-md-12">
			<button type="button" name="new" class="NewBTN"
				id="view" data-toggle="modal">View</button>
		</div>
		
		<div class="col-md-12">
			<div class="tablediv" style="margin-top:8px">
				<table class="table custometable">
					<thead>
						<tr>
							<th>时间</th>
							<th>总线名称</th>
							<th>工段名称</th>
							<th>生产段长</th>
							<th>第一台上线时间</th>
							<th>最后一台下线时间</th>
							<th>吃饭时间min</th>
							<th>等待上下游时间min</th>
							<th>产量</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageData.list_data}" var="row">
							<tr>
								<td>${row.get("model").create_datetime.substring(0, 10)}</td>
								<td>${row.get("line").name}</td>
								<td>${row.get("dan").name}</td>
								<td>${row.get("user_pro").name}</td>
								<td>${row.get("model").first_load}</td>
								<td>${row.get("model").last_load}</td>
								<td>${row.get("model").lunch_time}</td>
								<td>${row.get("model").wait_time}</td>
								<td>${row.get("model").output}</td>
								
								<%
								String json = "";
								String roles = "";
									try {
										ObjectMapper mapper = (ObjectMapper) request.getAttribute("mapper");
										HashMap row = (HashMap)pageContext.getAttribute("row");
										if(mapper == null){
											out.print("mapper null");
										}
										if(row == null){
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
