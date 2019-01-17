<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=10, user-scalable=yes">
		<title>CCESM - Administracion</title>
		<link href="${contextPath}/resources/sencha/classic/themes/theme-triton-all.css" rel="stylesheet" type="text/css" />
		<script src="${contextPath}/resources/sencha/classic/ext-all.js" type="text/javascript"></script>
		<script src="${contextPath}/resources/sencha/classic/fixes/workarounds.js" type="text/javascript"></script>
		<script type="text/javascript">
			Ext.Loader.setPath({
				"admin": "${contextPath}/resources/views/classic/administrador",
				"ux": "${contextPath}/resources/sencha/classic/ux",
			});
			Ext.Loader.setConfig({
			    enabled: true,
			    disableCaching: false
			});
		</script>
		<sitemesh:write property='head'/>
	</head>
	<body>
		<sitemesh:write property='body'/>
	</body>
</html>