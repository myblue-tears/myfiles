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
<title>用户管理</title>
</head>
<BODY bgColor=#dee7ff leftMargin=0 background="" topMargin=0 marginheight="0" marginwidth="0">
<center>
      <TABLE width="778" border=0 cellPadding=0 cellSpacing=0 borderColor=#ffffff bgColor=#dee7ff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR height=35>
            <TD align=middle width=20 background=images/title_left.gif 
          bgColor=#dee7ff></TD>
            <TD align=middle width=120 background=images/title_left.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7> 用户管理<font color="#FFFFFF">&nbsp;</font></FONT> </TD>
            <TD align=middle width=11 background=images/title_middle.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>&nbsp;</FONT> </TD>
            <TD align=middle background=images/title_right.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>&nbsp;</FONT> </TD>
          </TR>
        </TBODY>
      </TABLE>
      <TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD width="82%" height=14 align=right vAlign=center noWrap>
            </TD>
            <TD width="18%" align=right vAlign=center noWrap>　</TD>
          </TR>
          <TR>
            <TD height=14 align=right vAlign=center noWrap>
            	<!-- 在这里插入查询表单 -->
            </TD>
            <TD height=14 align="left" vAlign=center noWrap>
            <% 
            /**
            * 在这里定义“添加”，“查询”等按钮
            * <input type="image" name="find" value="find" src="images/cz.gif">
            * &nbsp;&nbsp;&nbsp;&nbsp; 
            * <a href="#" onClick="BeginOut('document.do?method=addInput','470')">
            * <img src="images/addpic.gif" border=0 align=absMiddle style="CURSOR: hand"></a>
            */
            %>
            <!-- 添加的功能在哪一个页面上就在哪一级页面添加 ,为了让原页面不受影响，采用伪类代替a标签的功能
            	如果使用a标签会在打开添加页面的时候返回到登录页面，效果不好
            -->
            	<span style="cursor:hand;color: #0000ff"
            	onmouseover="this.style.color='#ff0000'"
            	onmouseout="this.style.color='0000ff'"
            	onclick="openWin('${pageContext.request.contextPath}/person/addUI.action','addorg',600,200);">
            	添加人员信息
            	</span>
            
         
            </TD>
          </TR>
          <TR>
            <TD height=28 colspan="2" align=right vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
            <!-- 可以在这里插入分页导航条 -->
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      <table width="778" border="0" cellPadding="0" cellSpacing="1" bgcolor="#6386d6">
          <!-- 列表标题栏 -->
	      <tr bgcolor="#EFF3F7" class="TableBody1">
		      <td width="5%" height="37" align="center"><b>序号</b></td>
		      <td width="15%" height="37" align="center"><B>姓名</B></td>
		      <td width="10%" height="37" align="center"><b>性别</b></td>
		      <td width="20%" height="37" align="center"><B>所属机构</B></td>
		      <td width="20%" height="37" align="center"><b>登录账号</b></td>
              <td width="30%" height="37" align="center"><b>操作</b></td>
          </tr>
          <!-- 列表数据栏 -->
          <c:if test="${!empty pm.dataList}">
          <c:forEach items="${pm.dataList}" var="person">
	      <tr bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
		      <td align="center" vAlign="center">${person.id}</td>
	          <td align="center" vAlign="center">${person.name}</td>
	          <td align="center" vAlign="center">${person.sex}</td>
	          <td align="center" vAlign="center">${person.org.name}</td>
	           <td align="center" vAlign="center">${person.user.userName}</td>
	          <td align="center" vAlign="center">
	          <!-- 同样的删除之后要让原页面不受影响 -->
	          <span style="cursor:hand;color: #0000ff"
            	onmouseover="this.style.color='#ff0000'"
            	onmouseout="this.style.color='0000ff'"
            	onclick="openWin('${pageContext.request.contextPath}/user/addUI.action?personId=${person.id }','addUser',600,200);">
            	分配账号
            	</span>
            	&nbsp;
            	<!-- 删除的时候不是删除人员，而是删除用户的账号和id -->
	          <span style="cursor:hand;color: #0000ff"
            	onmouseover="this.style.color='#ff0000'"
            	onmouseout="this.style.color='0000ff'"
            	onclick="openWin('${pageContext.request.contextPath}/user/del.action?id=${person.user.id }');">
            	删除
            	</span>
            	
            	&nbsp;
            	<span style="cursor:hand;color: #0000ff"
            	onmouseover="this.style.color='#ff0000'"
            	onmouseout="this.style.color='0000ff'"
            	onclick="openWin('${pageContext.request.contextPath}/user/findRoleList.action?userId=${person.user.id }','addUser',600,200);">
            	分配角色
            	</span>
            	
            	&nbsp;
            	<span style="cursor:hand;color: #0000ff"
            	onmouseover="this.style.color='#ff0000'"
            	onmouseout="this.style.color='0000ff'"
            	onclick="openWin('${pageContext.request.contextPath}/acl/initPage.action?mainType=User&mainId=${person.user.id}','addacl',600,200,1);">
            	用户授权
            	</span>
        </tr>
        </c:forEach>
		</c:if>
        <!-- 在列表数据为空的时候，要显示的提示信息 -->
	    <c:if test="${empty pm.dataList}">
	    <tr>
	    	<td colspan="7" align="center" bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
	    	没有找到相应的记录
	    	</td>
	    </tr>
	    </c:if>
      </table>
      <TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD height=28 align=right vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
            <!-- 可以在这里插入分页导航条 -->
           <pg:pager items="${pm.items}" maxPageItems="${pm.pageSize}" maxIndexPages="5" 
           export="currentPageNumber=pageNumber" url="user/findAll.action">
				<pg:first>
					<a href="${pageUrl}">首页</a>
				</pg:first>
				<pg:prev>
					<a href="${pageUrl}">前页</a>
				</pg:prev>
				<pg:pages>
					<c:choose>
						<c:when test="${currentPageNumber eq pageNumber}">
							<font color="red">${pageNumber }</font>
						</c:when>
						<c:otherwise>
							<a href="${pageUrl}">${pageNumber }</a>
						</c:otherwise>
					</c:choose>
				</pg:pages>
				<pg:next>
					<a href="${pageUrl}">下页</a>
				</pg:next>
				<pg:last>
					<a href="${pageUrl}">尾页</a>
				</pg:last>
			</pg:pager>
    		</TD>
          </TR>
        </TBODY>
      </TABLE>
</center>

</body>

</html>
