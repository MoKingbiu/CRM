<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>错误</title>
</head>
<body>
    <center>
        <font style="font-size: 25px;color: red">${info}</font>
    </center>
</body>
</html>