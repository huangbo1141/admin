<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.hgc.admin.database.model.AdminUser"%>
<div class="content-wrapper">
	
	<div class=row>
		<form id='FormEditModal' method='post'>
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">${pageData.msg}</label>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<label class="control-label">初始密码设置</label>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<input type="password" class="form-control inputstyle" id=""
							placeholder="Password" name="password" autocomplete="off">
				</div>
			</div>
			<div class="col-md-12">
				<button type="submit" class="NewBTN">保存</button>
			</div>
		</form>
	</div>
</div>
<!-- .content-wrapper -->
