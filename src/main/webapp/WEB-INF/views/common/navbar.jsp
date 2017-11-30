<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="cd-side-nav1">

	<div class="logo">
		<a href="#"><img src="/${rootdir}/resources/img/logo.png" /></a>
		<c:out value="${currentUser.username}"/> 
	</div>
	<div class="panel-group" id="accordion">
		<c:forEach items="${currentUser.list_lmenu}" var="lm">
			<div class="panel ">
				<%
					String active = "";
						String in = "";
				%>
				<c:if test="${lm.mid == currentUser.curMenu.mid}">
					<%
						active = "active";
								in = "in";
					%>
				</c:if>
				<div class="panel-heading <%=active%>">
					<h4 class="panel-title">
						<a class="leftmenu" data-toggle="collapse"
							data-parent="#accordion" href="#collapse${lm.menu.id}">
							${lm.menu.name} </a>
					</h4>
				</div>
				<c:if test="${not empty lm.submenu}">
					<div id="collapse${lm.menu.id}"
						class="panel-collapse collapse <%=in%>">
						<div class="panel-body">
							<ul>
								<c:forEach items="${lm.submenu}" var="lm_s">
									<a href="/${rootdir}/${lm.menu.term}/${lm_s.menu.term}"><li>${lm_s.menu.name}</li></a>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>

			</div>
		</c:forEach>
		<div class="panel ">
			<div class="panel-heading ">
				<h4 class="panel-title">
					<a href="${pageContext.request.contextPath}/account/logout">退出</a>	
				</h4>
			</div>
		</div>
	</div>
</nav>