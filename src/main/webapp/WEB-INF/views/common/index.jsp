<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Remember Me with Spring MSV Framework</title>
</head>
<body>
	<s:form method="post" commandName="account" action="login">
		<fieldset>
			<legend>Login</legend>
			${error}
			<table cellpadding="2" cellspacing="2">
				<tr>
					<td>Username</td>
					<td><s:input path="username" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><s:password path="password" /></td>
				</tr>
				<tr>
					<td>Remember Me?</td>
					<td><input type="checkbox" name="remember" value="1"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Login"></td>
				</tr>
			</table>
		</fieldset>
	</s:form>
</body>
</html>