<%@page import="com.kan.base.domain.security.StaffVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- �޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			StaffVO passObject = (StaffVO)request.getAttribute("passObject");
			StaffVO originalObject = (StaffVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getStaffNo(),originalObject.getStaffNo(),"�������"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"Ա������ (����)"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"Ա������ (Ӣ��)"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeSalutation(),originalObject.getDecodeSalutation(),"�ƺ�"));
			out.print(KANUtil.getCompareHTML(passObject.getBirthday(),originalObject.getBirthday(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getRegistrationHometown(),originalObject.getRegistrationHometown(),"�������У����ᣩ"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"״̬"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>Ա��ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="staffId"/>' /></li>
		<li><label>Ա������ �����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>Ա������ ��Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>�ƺ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeSalutation"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="birthday"/>' /></li>
		<li><label>�������У����ᣩ</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="registrationHometown"/>' /></li>
		<li><label>״̬</label><textarea disabled="disabled"><bean:write name="originalObject" property="decodeStatus"/></textarea></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- ���޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>Ա��ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="staffId"/>' /></li>
		<li><label>Ա������ (����)</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>Ա������ (Ӣ��)</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateNumber"/>' /></li>
		<li><label>״̬</label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		<li><label>����</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:notEqual>