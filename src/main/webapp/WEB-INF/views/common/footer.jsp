 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/dialog.jsp" />

<script src="<c:url value='/resources/js/jquery-2.1.4.js' />" ></script> 
<script src="<c:url value='/resources/js/jquery.menu-aim.js' />" ></script> 
<script src="<c:url value='/resources/js/jquery.menu-aim.js' />" ></script> 

<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="<c:url value='/resources/js/main.js' />" ></script> 
<script>
	$("#accordion .panel-heading").click(function() {
		$('#accordion .panel-heading').removeClass('active');
		$(this).addClass('active');
	})
</script>