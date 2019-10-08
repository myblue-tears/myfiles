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
	<script language="javascript"  src="${pageContext.request.contextPath}/script/public.js"></script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/aclManager.js'></script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
	<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
	<script type="text/javascript">
		function initPage() {
			//findAllAclByMainTypeMainId(String mainType, int mainId)
			aclManager.findAllAclByMainTypeMainId('${mainType}',${mainId},function(v1){
				for(var i=0;i<v1.length;i++){
					var arr = v1[i];
					var moduleId = arr[0];
					var c = arr[1];
					var r = arr[2];
					var u = arr[3];
					var d = arr[4];
					var ext = arr[5];
					
					var cb_c = document.getElementById("C_"+moduleId);
					var cb_r = document.getElementById("R_"+moduleId);
					var cb_u = document.getElementById("U_"+moduleId);
					var cb_d = document.getElementById("D_"+moduleId);
				
					//给文本框进行赋值
					cb_c.checked=c>0?true:false;
					cb_r.checked=r>0?true:false;
					cb_u.checked=u>0?true:false;
					cb_d.checked=d>0?true:false;
					
				}
				
			});
		}
	
	</script>
	<title>
		<c:if test="${!empty user }">请给用户【${user.person.name}】授权</c:if>
		<c:if test="${!empty role }">请给角色【${role.name}】授权</c:if>
	</title>
</head>
<BODY onload="initPage()">
<center>
      <TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD height=28 colspan="2" align=center vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
            <!-- 可以在这里插入分页导航条 -->
            <c:if test="${!empty user }">请给用户【${user.person.name}】授权</c:if>
			<c:if test="${!empty role }">请给角色【${role.name}】授权</c:if>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      <table width="778" border="0" cellPadding="0" cellSpacing="1" bgcolor="#6386d6">
          <!-- 列表标题栏 -->
	      <tr bgcolor="#EFF3F7" class="TableBody1">
		      <td width="15%" height="37" align="center"><b>顶级模块地</b></td>
		      <td width="15%" height="37" align="center"><B>二级模块</B></td>
		      <td width="30%" height="37" align="center"><b>授权</b></td>
		      <!-- 只有用户的时候才是显示继承关系，所以进行判断 -->
		      <c:if test="${!empty user }">
		      <td width="18%" height="37" align="center"><b>不继承</b></td>
		      </c:if>
		      
              <td width="10%" height="37" align="center"><b>启用</b></td>
          </tr>
          
          <!-- 列表数据栏 -->
          <c:if test="${!empty pm.dataList}">
          <c:forEach items="${pm.dataList}" var="module">
	      <tr bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
		      <td align="center" vAlign="center">${module.name}</td>
	          <td align="center" vAlign="center">
	          		&nbsp;
	          </td>
	          <td align="center" vAlign="center">
	          	<input type="checkbox" id="C_${module.id}">C
	          	<input type="checkbox" id="R_-${module.id}">R
	          	<input type="checkbox" id="U_${module.id}">U
	          	<input type="checkbox" id="D_${module.id}">D
	          </td>
	          
	           <c:if test="${!empty user }">
	          <td align="center" vAlign="center">
	          <input type="checkbox">
	          </td>
	          </c:if>
	          
	          <td align="center" vAlign="center">
	          <input type="checkbox">
	          </td>
        </tr>
        <c:forEach items="${module.childList}" var="cmodule">
	         <tr bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
			      <td align="center" vAlign="center">&nbsp;</td>
		          <td align="center" vAlign="center">
		          		${cmodule.name }
		          </td>
		          <td align="center" vAlign="center">
		          	<input type="checkbox" id="C_${cmodule.id}">C
		          	<input type="checkbox" id="R_${cmodule.id}">R
		          	<input type="checkbox" id="U_${cmodule.id}">U
		          	<input type="checkbox" id="D_${cmodule.id}">D
		          </td>
		          
		           <c:if test="${!empty user }">
		          <td align="center" vAlign="center">
		          <input type="checkbox">
		          </td>
		          </c:if>
		          
		          <td align="center" vAlign="center">
		          <input type="checkbox">
		          </td>
	        </tr>
        </c:forEach>
        
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
          
    		</TD>
          </TR>
        </TBODY>
      </TABLE>
</center>

</body>

</html>
