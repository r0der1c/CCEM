<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script src="${contextPath}/resources/views/classic/administrador/asamblea/AsambleaView.js" type="text/javascript"></script>
		<script type="text/javascript">
			Ext.onReady(function(){
				new View({"asambleas": Ext.decode('${asambleas}')});
			})
		</script>
	</head>
	<body>

	</body>
</html>