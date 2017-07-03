<%@page import="com.kan.hro.domain.biz.attendance.OTHeaderVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

		<%
			final boolean inHouse = request.getAttribute( "role" ).equals( "2" );
		%>

<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- �޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			OTHeaderVO passObject = ( OTHeaderVO )request.getAttribute("passObject");
			OTHeaderVO originalObject = ( OTHeaderVO )request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeId(),originalObject.getEmployeeId(),  inHouse ? "Ա��" : "��Ա" + "ID"));
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeName(),originalObject.getEmployeeName(), inHouse ? "Ա��" : "��Ա" + "����"));
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeNo(),originalObject.getEmployeeNo(), inHouse ? "Ա��" : "��Ա" + "���"));
			out.print(KANUtil.getCompareHTML(passObject.getCertificateNumber(),originalObject.getCertificateNumber(),"֤������"));
			out.print(KANUtil.getCompareHTML(passObject.getEstimateStartDate(),originalObject.getEstimateStartDate(),"��ʼʱ��"));
			out.print(KANUtil.getCompareHTML(passObject.getEstimateEndDate(),originalObject.getEstimateEndDate(),"����ʱ��"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDecodeOTDetail(),"�Ӱ���ϸ","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"״̬"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"����","textarea"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label><%=inHouse ? "Ա��" : "��Ա"%>ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
		<li><label><%=inHouse ? "Ա��" : "��Ա"%>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeName"/>' /></li>
		<li><label><%=inHouse ? "Ա��" : "��Ա"%>���</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeNo"/>' /></li>
		<li><label>֤������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="certificateNumber"/>' /></li>
		<li><label>��ʼʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="estimateStartDate"/>' /></li>
		<li><label>����ʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="estimateEndDate"/>' /></li>
		<li><label>�Ӱ���ϸ</label><textarea disabled="disabled"><bean:write name="originalObject" property="decodeOTDetail"/></textarea></li>
<%-- 		<li><label>״̬</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="decodeStatus"/>' /></li> --%>
		<li><label>����</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- ���޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li>
			<label>
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.id" /></logic:notEqual>
			</label>
			<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeId"/>' />
		</li>
		<li>
			<label>
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.name" /></logic:notEqual>
			</label>
			<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeName"/>' />
		</li>
		<li>
			<label>
				<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
				<logic:notEqual name="role" value="1"><bean:message bundle="public" key="public.employee2.no" /></logic:notEqual>
			</label>
			<input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeNo"/>' />
		</li>
		<li><label><bean:message bundle="public" key="public.certificate.number" /></label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="certificateNumber"/>' /></li>
		<li>
			<label><bean:message bundle="public" key="public.start.time" />
			<logic:empty name="passObject" property="actualStartDate">
			<span style="color : red">��Ԥ��</span>
			</logic:empty>
			</label>
			<logic:notEmpty name="passObject" property="actualStartDate">
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="actualStartDate"/>' />
			</logic:notEmpty>
			<logic:empty name="passObject" property="actualStartDate">
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateStartDate"/>' />
			</logic:empty>
		</li>
		<li>
			<label><bean:message bundle="public" key="public.end.time" />
			<logic:empty name="passObject" property="actualStartDate">
			<span style="color : red">��Ԥ��</span>
			</logic:empty>
			</label>
			<logic:notEmpty name="passObject" property="actualEndDate">
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="actualEndDate"/>' />
			</logic:notEmpty>
			<logic:empty name="passObject" property="actualEndDate">
				<input type="text" disabled="disabled" value='<bean:write name="passObject" property="estimateEndDate"/>' />
			</logic:empty>
		</li>
		<logic:equal name="accountId" value="100056">
		<li>
			<label><bean:message bundle="business" key="business.ot.special.overtime" /></label>
			<input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSpecialOT"/>' />
		</li>
		</logic:equal>
		<li><label><bean:message bundle="business" key="business.ot.search.title" /></label><textarea disabled="disabled"><bean:write name="passObject" property="decodeOTDetail"/></textarea></li>
		<li><label><bean:message bundle="public" key="public.description" /></label><textarea disabled="disabled"><bean:write name="passObject" property="remark2"/></textarea></li>
<%-- 		<li><label><bean:message bundle="public" key="public.status" /></label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li> --%>
	</ol>
	<br/>
	<div id="special_info_tab">
	
	</div>
</div>
</logic:notEqual>

<script type="text/javascript">
	// ���ؼӰ��¼
	loadHtml('#special_info_tab', 'otHeaderAction.do?proc=list_special_info_html&contractId=<%=( ( OTHeaderVO )request.getAttribute( "passObject" ) ).getContractId() %>&noTab=true', false, null);   
</script>