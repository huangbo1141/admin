
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.net.URL"%>
<head>
<title>${pageName}</title>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700'
	rel='stylesheet' type='text/css'>
<link href="<c:url value='/resources/css/reset.css' />" rel="stylesheet">
<!-- CSS reset -->
<link href="<c:url value='/resources/css/style.css' />" rel="stylesheet">
<!-- Resource style -->
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<link href="<c:url value='/resources/css/customstyle.css' />"
	rel="stylesheet">
<!-- Custom style -->
<script src="<c:url value='/resources/js/modernizr.js' />"></script>
<!-- Modernizr -->
<%
	try {
%>
<jsp:include page="../common/${pageTerm}/subheader_${pageSubTerm}.jsp" />
<%
	} catch (Exception e) {
		//out.println("An exception occurred: " + e.getMessage());
	}
%>


</head>