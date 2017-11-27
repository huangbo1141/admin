<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="loading" style="display: none;">Loading&#8230;</div>
<main class="cd-main-content"> <jsp:include
	page="../common/navbar.jsp" /> <jsp:include
	page="../template/${pageTerm}/content_${pageSubTerm}.jsp" /> <!-- .content-wrapper -->
</main>
<!-- .cd-main-content -->

