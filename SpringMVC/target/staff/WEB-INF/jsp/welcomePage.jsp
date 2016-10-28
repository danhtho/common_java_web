<%@page session="false"%>
<html>
<head>
<title>${title}</title>
</head>
<body>
    <jsp:include page="_menu.jsp" />
    <h1>Message : ${message}</h1>
    <p>Account</p>
    <table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Username</th>
        <th>Password</th>
        <th>Role</th>
    </tr>
    <tr>
        <td>admin</td>
        <td>1</td>
        <td>ADMIN</td>
    </tr>
    <tr>
        <td>user</td>
        <td>1</td>
        <td>USER</td>
    </tr>
    </table>
</body>
</html>