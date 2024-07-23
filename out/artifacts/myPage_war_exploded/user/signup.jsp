<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-07-15
  Time: 오전 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="user.mvc.model.UserService" %>
<%@ page import="mvc.domain.User" %>
<%@ page import="static user.mvc.model.SignupConst.SUCCESS" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<script>

  if(${result == SUCCESS}){
    alert('성공')
    location.href='board.do';
  }else if (${result == NOPASSWORD}){
    alert('비번틀림')
    location.href='board.do';
  }else{
    alert('아이디없음')
    location.href='board.do';
  }

</script>
</html>
