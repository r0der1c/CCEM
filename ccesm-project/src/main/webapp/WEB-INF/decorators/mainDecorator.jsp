<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="userName" value="${pageContext.request.userPrincipal.name}" />
<html>
<head>
 <title><sitemesh:write property='title'/></title>
 <sitemesh:write property='head'/>
	<link href="${contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	<link href="${contextPath}/resources/css/font-awesome.css" rel="stylesheet" />

	<script type="text/javascript"	src="${contextPath}/resources/js/jquery-1.11.0.min.js?${initParam.ipUpdate}"></script>
	<script type="text/javascript"	src="${contextPath}/resources/js/jquery-ui-1.9.2.custom.min.js?${initParam.ipUpdate}"></script>
	<script type="text/javascript"	src="${contextPath}/resources/bootstrap/js/bootstrap.min.js?${initParam.ipUpdate}"></script>
</head>

<body>
	<div class="panel panel-default  text-center">
		<nav class="navbar navbar-default">
		  <div class="container-fluid">
		    <div class="navbar-header">
		      <a class="navbar-brand" href="#">
		      	<span>Inside Premium</span>
		      </a>
		    </div>
		     <div class="nav navbar-nav navbar-right">
	               <div class="btn-group">
  					<button class="btn btn-default btn-lg dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	    			${userName}<span class="caret"></span>
					 </button>
					 <ul class="dropdown-menu">
					  	<li><a href="#">Ayuda</a></li>
					  	<li role='separator' class='divider'></li>
					  	<li><a class='fa fa-close' href="${contextPath}/logout">Salir</a></li>
					  </ul>
					</div>
	         </div>
		  </div>
		</nav>
		<div class="panel-body">
 			<sitemesh:write property='body'/>
 		</div>
 		<footer class='navbar-fixed-bottom'>
			Premium Restaurants Of America
 		</footer>
	</div>
</body>
</html>
