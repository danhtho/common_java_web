<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page session="true"%>

<html>
<head>
<title>Register Staff</title>
<script>
function register() {
    var form = document.getElementById('staffForm');
    form.action = "${pageContext.request.contextPath}/staff/register";
    form.submit();
}
function edit() {
    var form = document.getElementById('staffForm');
    form.action = "${pageContext.request.contextPath}/staff/edit";
    form.submit();
}
</script>
<style>
.global_error {
    color: red;
    border: 1px solid #FF0000;
    width: 500px;
    margin-bottom: 10px;
    padding: 5px;
}
.error {
    color: red;
}
</style>
</head>
<body>
    <jsp:include page="_menu.jsp" />
    <h2>Register</h2>
<form:form name='f' action="" modelAttribute="staffForm"  method="POST">
<c:set var="domainNameErrors"><form:errors path=""/></c:set>
<c:if test="${not empty domainNameErrors}">
    <div class="global_error">${domainNameErrors}</div>
</c:if>
<c:choose>
<c:when test="${staffForm.mode == 2}">
<table border=1>
<tr>
    <td>Name</td>
    <td>${staffForm.name}</td>
</tr>
<tr>
    <td>Katakana name</td>
    <td>${staffForm.kananame}</td>
</tr>
<tr>
    <td>Birthday</td>
    <td>${staffForm.birthday}</td>
</tr>
<tr>
    <td>Email</td>
    <td>${staffForm.email}</td>
</tr>
<tr>
    <td>Mobile</td>
    <td>${staffForm.mobile}</td>
</tr>
<tr>
    <td>Gender</td>
    <td><form:radiobuttons disabled = "true" path="gender" items="${genders}" value="${genders.key}" label="${genders.value}" /></td>
</tr>
<tr>
    <td>Position</td>
    <td><form:select disabled = "true" path="position" items="${positions}" /></td>
</tr>
</table>
</c:when>
<c:otherwise>
<table>
<tr>
	<td>Name</td>
	<td><form:input path="name" /></td>
	<td><form:errors path="name" cssClass="error"/></td>
</tr>
<tr>
	<td>Katakana name</td>
	<td><form:input path="kananame" /></td>
	<td><form:errors path="kananame"  cssClass="error"/></td>
</tr>
<tr>
	<td>Birthday</td>
	<td><form:input path="birthday" /></td>
	<td><form:errors path="birthday"  cssClass="error"/></td>
</tr>
<tr>
	<td>Email</td>
	<td><form:input path="email" /></td>
	<td><form:errors path="email"  cssClass="error"/></td>
</tr>
<tr>
	<td>Mobile</td>
	<td><form:input path="mobile" /></td>
	<td><form:errors path="mobile"  cssClass="error"/></td>
</tr>
<tr>
	<td>Gender</td>
	<td><form:radiobuttons path="gender" items="${genders}" value="${genders.key}" label="${genders.value}" /></td>
	<td><form:errors path="gender"  cssClass="error"/></td>
</tr>
<tr>
	<td>Position</td>
	<td><form:select path="position" items="${positions}" /></td>
	<td><form:errors path="position"  cssClass="error"/></td>
</tr>
<tr>
	<td></td>
	<td valign="bottom" height="50px">
<c:if test="${staffForm.mode == 0}">
<input type="button" value="Register" onclick="register()" />
</c:if>
<c:if test="${staffForm.mode == 1}">
<input type="button" value="Update" onclick="edit()" />
</c:if>
    </td>
	<td></td>
</tr>
</table>
</c:otherwise>
</c:choose>
<input type="hidden" name="id" value="${staffForm.id}" />
<input type="hidden" name="mode" value="${staffForm.mode}" />
</form:form>
</body>
</html>