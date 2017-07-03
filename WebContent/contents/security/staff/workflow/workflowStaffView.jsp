<%@page import="com.kan.base.domain.security.StaffVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			StaffVO passObject = (StaffVO)request.getAttribute("passObject");
			StaffVO originalObject = (StaffVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getStaffNo(),originalObject.getStaffNo(),"档案编号"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"员工姓名 (中文)"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"员工姓名 (英文)"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeSalutation(),originalObject.getDecodeSalutation(),"称呼"));
			out.print(KANUtil.getCompareHTML(passObject.getBirthday(),originalObject.getBirthday(),"生日"));
			out.print(KANUtil.getCompareHTML(passObject.getRegistrationHometown(),originalObject.getRegistrationHometown(),"户籍城市（籍贯）"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"状态"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>员工ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="staffId"/>' /></li>
		<li><label>员工姓名 （中文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>员工姓名 （英文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>称呼</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSalutation"/>' /></li>
		<li><label>生日</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="birthday"/>' /></li>
		<li><label>户籍城市（籍贯）</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="registrationHometown"/>' /></li>
		<li><label>状态</label><textarea disabled="disabled"><bean:write name="originalObject" property="decodeStatus"/></textarea></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- 非修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>员工ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="staffId"/>' /></li>
		<li><label>员工姓名 (中文)</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>员工姓名 (英文)</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>证件号码</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateNumber"/>' /></li>
		<li><label>状态</label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		<li><label>描述</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:notEqual>