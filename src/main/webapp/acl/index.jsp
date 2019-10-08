<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/common/common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>"></base>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="style/oa.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${pageContext.request.contextPath}/script/public.js"></script>
  <script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/aclManager.js'></script>
  <script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
  <script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
<script type="text/javascript">
	function initPage(){
		aclManager.findAllAclByMainTypeMainId('${mainType}',${mainId},function(v1){
			for(var i=0 ;i<v1.length;i++){
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
				var cb_use = document.getElementById("USE_"+moduleId);
				
				//判断只有用户的时候才可以继承
				<c:if test="${!empty user }">
					var cb_ext = document.getElementById("EXT_"+moduleId);
					cb_ext.checked = ext==0?true:false;
				</c:if>
				
				cb_c.checked = c > 0?true:false;
				cb_r.checked = r > 0?true:false;
				cb_u.checked = u > 0?true:false;
				cb_d.checked = d > 0?true:false;
				cb_use.checked = true; 
				
			}
		});
	}
	function addOrUpdateAcl(cb){
		//public void addOrUpdateAcl(String mainType, int mainId, int moduleId, int permission, boolean yes
		aclManager.addOrUpdateAcl('${mainType}',${mainId},cb.moduleId,cb.permission,cb.checked);
		var cb_use = document.getElementById("USE_"+cb.moduleId);
		cb_use.checked = true;
		//如果是给用户直接授权，那么就是不继承关系
		<c:if test="${!empty user }">
			var cb_ext = document.getElementById("EXT_"+cb.moduleId);
			cb_ext.checked = true;
		</c:if>
	}
	
	function addOrDelAcl(cb){
		dwr.engine.setAsync(false);//设置请求处理为：同步请求处理（排队执行）
		if(cb.checked){
			//添加
			aclManager.addOrUpdateAcl('${mainType}',${mainId},cb.moduleId,0,cb.checked);
			aclManager.addOrUpdateAcl('${mainType}',${mainId},cb.moduleId,1,cb.checked);
			aclManager.addOrUpdateAcl('${mainType}',${mainId},cb.moduleId,2,cb.checked);
			aclManager.addOrUpdateAcl('${mainType}',${mainId},cb.moduleId,3,cb.checked);
		}else{
			//删除
			aclManager.delAcl('${mainType}',${mainId},cb.moduleId);
		}
		//全选问题
		var cb_c = document.getElementById("C_"+cb.moduleId);
		var cb_r = document.getElementById("R_"+cb.moduleId);
		var cb_u = document.getElementById("U_"+cb.moduleId);
		var cb_d = document.getElementById("D_"+cb.moduleId);
		var cb_ext = document.getElementById("EXT_"+cb.moduleId);
		
		<c:if test="${!empty user }">
		var cb_ext = document.getElementById("EXT_"+cb.moduleId);
			cb_ext.checked = cb.checked;
		</c:if>
	
		cb_c.checked = cb.checked;
		cb_r.checked = cb.checked;
		cb_u.checked = cb.checked;
		cb_d.checked = cb.checked;
		
	}
</script>
<c:if test="${!empty role }">
<title>请给角色【 ${role.name}】授权</title>
</c:if>
<c:if test="${!empty user }">
<title>请给用户【 ${user.person.name} 】授权</title>
</c:if>
</head>
<BODY bgColor=#dee7ff leftMargin=0 background="" topMargin=0
	marginheight="0" marginwidth="0" onload="initPage()">
	<center>
		
		<TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0
			borderColor=#ffffff style="FONT-SIZE: 10pt">
			<TBODY>
				<TR>
					<TD height=28 colspan="2" align="center" vAlign=center noWrap
						background=images/list_middle.jpg>&nbsp;&nbsp; <!-- 可以在这里插入分页导航条 -->
						<c:if test="${!empty role }">
						请给角色【 ${role.name}】授权
						</c:if>
						<c:if test="${!empty user }">
						请给用户【 ${user.person.name} 】授权
						</c:if>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<table width="778" border="0" cellPadding="0" cellSpacing="1"
			bgcolor="#6386d6">
			<!-- 列表标题栏 -->
			<tr bgcolor="#EFF3F7" class="TableBody1">
				<td width="15%" height="37" align="center"><b>顶级模块</b></td>
				<td width="25%" height="37" align="center"><B>二级模块</B></td>
				<td width="30%" height="37" align="center"><b>权限</b></td>
				<c:if test="${!empty user}">
				<td width="10%" height="37" align="center"><b>不继承</b></td>
				</c:if>
				<td width="10%" height="37" align="center"><b>启用</b></td>
			</tr>
			<!-- 列表数据栏 -->
			<c:if test="${!empty pm.dataList}">
				<c:forEach items="${pm.dataList }" var="module">
					<tr bgcolor="#EFF3F7" class="TableBody1"
						onmouseover="this.bgColor = '#DEE7FF';"
						onmouseout="this.bgColor='#EFF3F7';">
						<td align="center" vAlign="center">${module.name }</td>
						<td align="center" vAlign="center">&nbsp;</td>
						<td align="center" vAlign="center">
						<input type="checkbox" id="C_${module.id }" moduleId="${module.id }" permission="0" onclick="addOrUpdateAcl(this)">C
						<input type="checkbox" id="R_${module.id }" moduleId="${module.id }" permission="1" onclick="addOrUpdateAcl(this)">R
						<input type="checkbox" id="U_${module.id }" moduleId="${module.id }" permission="2" onclick="addOrUpdateAcl(this)">U
						<input type="checkbox" id="D_${module.id }" moduleId="${module.id }" permission="3" onclick="addOrUpdateAcl(this)">D
						</td>
						<c:if test="${!empty user}">
						<td align="center" vAlign="center"><input type="checkbox" id="EXT_${module.id }"></td>
						</c:if>
						<td align="center" vAlign="center"><input type="checkbox" id="USE_${module.id }"  moduleId="${module.id }" onclick="addOrDelAcl(this)"></td>
					</tr>
					<c:forEach items="${module.childList }" var="child">
						<tr bgcolor="#EFF3F7" class="TableBody1"
							onmouseover="this.bgColor = '#DEE7FF';"
							onmouseout="this.bgColor='#EFF3F7';">
							<td align="center" vAlign="center">&nbsp;</td>
							<td align="center" vAlign="center">${child.name }</td>
							<td align="center" vAlign="center">
							<input type="checkbox" id="C_${child.id }" moduleId="${child.id }" permission="0" onclick="addOrUpdateAcl(this)">C
							<input type="checkbox" id="R_${child.id }" moduleId="${child.id }" permission="1" onclick="addOrUpdateAcl(this)">R
							<input type="checkbox" id="U_${child.id }" moduleId="${child.id }" permission="2" onclick="addOrUpdateAcl(this)">U
							<input type="checkbox" id="D_${child.id }" moduleId="${child.id }" permission="3" onclick="addOrUpdateAcl(this)">D
							</td>
							<c:if test="${!empty user}">
							<td align="center" vAlign="center"><input type="checkbox" id="EXT_${child.id }"></td>
							</c:if>
							<td align="center" vAlign="center"><input type="checkbox" id="USE_${child.id }"  moduleId="${child.id }" onclick="addOrDelAcl(this)"></td>
						</tr>
					</c:forEach>
				</c:forEach>
			</c:if>

			<!-- 在列表数据为空的时候，要显示的提示信息 -->
			<c:if test="${empty pm.dataList}">
				<tr>
					<td colspan="7" align="center" bgcolor="#EFF3F7" class="TableBody1"
						onmouseover="this.bgColor = '#DEE7FF';"
						onmouseout="this.bgColor='#EFF3F7';">没有找到相应的记录</td>
				</tr>
			</c:if>
		</table>
		
	</center>

</body>

</html>