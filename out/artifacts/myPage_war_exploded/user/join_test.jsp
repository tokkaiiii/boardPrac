<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-07-15
  Time: 오전 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="mvc.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script>
  const joinValid = <%=request.getAttribute("joinValid")%>;
  if (joinValid === -1) {
    alert('다시 입력해주세요')
    location.href = 'user/join.jsp'
  } else {
    <%
    User user = (User)request.getAttribute("user");
      session.setAttribute("id", user.getId());
    %>
    alert('가입이 성공했습니다')
    location.href = 'board.do'
  }
</script>
</html>
