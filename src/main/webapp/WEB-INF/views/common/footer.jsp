<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../common/dialog.jsp" />

<script src="<c:url value='/resources/js/jquery-2.1.4.js' />"></script>
<script src="<c:url value='/resources/js/jquery.menu-aim.js' />"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>	
	
<script src="<c:url value='/resources/js/main.js' />"></script>


<script>
	$("#accordion .panel-heading").click(function(e) {
		if ($(this).hasClass("active")) {
			$(this).removeClass('active');
		} else {
			$('#accordion .panel-heading').removeClass('active');
			$(this).addClass('active')

		}

	});
	$(function() {
		$(".mdl-sv").submit(function(e) {
			e.preventDefault();

			$(".loading").show();
			setTimeout(function() {
				$(".loading").hide();
			}, 3000); // in milliseconds
		});
	});
	var bpmnJsonString = '${cat}';
	var cat = JSON.parse(bpmnJsonString);
	//alert(cat.username);
	var permission = [];
	<c:forEach items="${currentUser.curMenu.actions}" var="actionID">
	permission.push("${currentUser.map_menu_action[actionID].ac}");
	</c:forEach>
</script>
<script src="<c:url value='/resources/js/common/action.js' />"></script>
<%
	try {
%>
<jsp:include page="../common/${pageTerm}/subfooter_${pageSubTerm}.jsp" />
<%
	} catch (Exception e) {
		//out.println("An exception occurred: " + e.getMessage());
	}
%>
