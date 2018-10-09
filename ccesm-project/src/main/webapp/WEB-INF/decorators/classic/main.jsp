<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title><sitemesh:write property='title'/></title>
		<sitemesh:write property='head'/>
	</head>
	<body>
		<sitemesh:write property='body'/>
	</body>
</html>
