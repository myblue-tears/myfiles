<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=utf-8">
<TITLE>Outlook Like Bar Example - (c) 2001 www.wallner-software.com</TITLE>
</HEAD>

  <FRAMESET  cols = "130,*" frameborder=yes bordercolor=silver>
     <FRAME SRC="outlook.jsp" NAME="Links" SCROLLING="No">
     <FRAME SRC="main.html" NAME="main" SCROLLING="AUTO">
  </FRAMESET> 

<NOFRAMES>
<BODY>
</BODY>

</NOFRAMES>

</HTML>
