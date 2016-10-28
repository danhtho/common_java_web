<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<title>List staff</title>
<style type="text/css">
table {
	border-collapse: collapse;
}

table, td, th {
	border: 1px solid black;
	padding: 3px;
}

th {
	background-color: #0066ff;
	color: #FFF;
}

tr:nth-child(even) {
	background: #ccffcc;
}

tr:nth-child(odd) {
	background: #FFF
}
</style>
</head>
<body>
	<jsp:include page="_menu.jsp" />
	<h2>List user</h2>
	<c:choose>
		<c:when test="${not empty listStaff}">
			<table class="table_border">
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Kana name</th>
					<th>Email</th>
					<th>Birthday</th>
					<th>Gender</th>
					<th>Position</th>
					<th>Action</th>
				</tr>
				<c:forEach var="staff" items="${listStaff}" varStatus="status">
					<tr>
						<td>${staff.id}</td>
						<td>${staff.name}</td>
						<td>${staff.kananame}</td>
						<td>${staff.email}</td>
						<td>${staff.birthday}</td>
						<td>${staff.gender}</td>
						<td>${staff.position}</td>
						<td align="center">
						      <a href="${pageContext.request.contextPath}/staff/view/${staff.id}">View</a>
						      <sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
							| 
						      <a href="${pageContext.request.contextPath}/staff/edit/${staff.id}">Edit</a>
							| <a
							href="${pageContext.request.contextPath}/staff/delete/${staff.id}"
							onclick="return confirm('Are you sure to delete this staff ?')">Delete</a>
							</sec:authorize>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
    No data
    </c:otherwise>
	</c:choose>
</body>
</html>