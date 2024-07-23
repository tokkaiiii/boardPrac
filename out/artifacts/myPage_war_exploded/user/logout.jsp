<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-07-15
  Time: 오전 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    session.invalidate();
%>
<script>
  location.href='board.do'
</script>
</body>
</html>
