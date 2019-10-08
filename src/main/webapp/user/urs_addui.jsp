<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/common.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="style/oa.css" rel="stylesheet" type="text/css">
<script language="javascript" src="script/public.js"></script>
<title>请选择分配给用户的角色</title>
</head>
<body>
<center>
<!-- ？为什么要加action -->
<form action="user/addUrs.action" method="post">
<input type="hidden" name="user.id" value="${userId}">
<TABLE class="tableEdit" border="0" cellspacing="1" cellpadding="0" style="width:580px;">
	<TBODY>
		<TR>
			<!-- 这里是添加、编辑界面的标题 -->
			<td align="center" class="tdEditTitle">请选择给用户分配的角色</TD>
		</TR>
		<TR>
			<td>
			<!-- 主输入域开始 -->

	<table class="tableEdit" style="width:580px;" cellspacing="0" border="1" cellpadding="0">
	
	<tr>
		<td class="tdEditLabel" align="center">选择</td>			
		<td class="tdEditLabel" align="center">角色名称</td>			
	</tr>
	
	<c:forEach items="${roleList}" var="role">
	<tr>
		<td class="tdEditLabel" align="center">
			<input type="radio" name="role.id" value="${role.id}">
		</td>			
		<td align="center">
			${role.name }
		</td>
		</tr>
		</c:forEach>
		<tr>
			<TR>
				<td align="left" colspan="2">
				请输入角色的优先级<input type="text" name="level">
				</TD>
			</TR>
		</tr>
	</table>

			<!-- 主输入域结束 -->
			</td>
		</TR>
	</TBODY>
</TABLE>

<TABLE>
		<TR align="center">
			<TD colspan="3" bgcolor="#EFF3F7">
			<input type="submit" name="saveButton"
				class="MyButton" value="分配角色管理"> 
			<input type="button" class="MyButton"
				value="关闭窗口" onclick="window.close()">
			</TD>
		</TR>
</TABLE>
</form>
</center>
</body>
</html>