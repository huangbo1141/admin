<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<script>
	var bpmnJsonString = '${pageData.json_dan}';
	var map_dan = JSON.parse(bpmnJsonString);
	</script>
