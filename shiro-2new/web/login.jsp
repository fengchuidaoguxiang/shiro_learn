<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2019/7/27
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <h4>login page</h4>
  <form action="shiro/login" method="post">
      username: <input type="text" name="username"/> <br/><br/>
      password: <input type="password" name="password"/> <br/><br/>
      <input type="submit" value="Submit"/>
  </form>
  </body>
</html>
