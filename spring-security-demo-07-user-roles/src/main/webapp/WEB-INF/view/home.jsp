<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Company Home Page</title>
</head>
<body>
	<h1>Welcome to the Company Home Page</h1>

	<hr>

	<!-- display user name and role -->

	<p>
		User:
		<security:authentication property="principal.username" />
	</p>
	<br>
	<br>
	<p>
		Role(s):
		<security:authentication property="principal.authorities" />
	</p>

	<hr>

	<security:authorize access="hasRole('MANAGER')">
		<p>
			<a href="${pageContext.request.contextPath}/leaders">Leadership
				Meeting</a> For Manager's Only
		</p>
	</security:authorize>


	<security:authorize access="hasRole('ADMIN')">
		<p>
			<a href="${pageContext.request.contextPath}/systems">Admin
				Meeting</a> For Admin Only
		</p>

		<hr>
	</security:authorize>
	
	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">

		<input type="submit" value="Logout" />

	</form:form>
</body>
</html>