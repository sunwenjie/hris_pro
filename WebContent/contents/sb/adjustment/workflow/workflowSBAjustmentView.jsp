<%@page import="com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- �޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			SBAdjustmentHeaderVO passObject = ( SBAdjustmentHeaderVO )request.getAttribute( "passObject" );
			SBAdjustmentHeaderVO originalObject = ( SBAdjustmentHeaderVO )request.getAttribute("originalObject");
			
			out.print(KANUtil.getCompareHTML(passObject.getAdjustmentHeaderId(),originalObject.getAdjustmentHeaderId(),"����ID"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeLegalEntity(),originalObject.getDecodeLegalEntity(),"����ʵ��"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeBusinessType(),originalObject.getDecodeBusinessType(),"ҵ������"));
			out.print(KANUtil.getCompareHTML(passObject.getClientId(),originalObject.getClientId(),"�ͻ�ID"));
			out.print(KANUtil.getCompareHTML(passObject.getOrderId() ,originalObject.getOrderId(),"����ID"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameZH(),originalObject.getClientNameZH(),"�ͻ����ƣ����ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameEN(),originalObject.getClientNameEN(),"�ͻ����ƣ�Ӣ�ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeId(),originalObject.getEmployeeId(),"��ԱID"));
			out.print(KANUtil.getCompareHTML(passObject.getContractId(),originalObject.getContractId(),"����Э��"));
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeSBId(),originalObject.getEmployeeSBId(),"�籣�����𷽰�"));
			out.print(KANUtil.getCompareHTML(passObject.getAmountPersonal(),originalObject.getAmountPersonal(),"�ϼƣ����ˣ�"));
			out.print(KANUtil.getCompareHTML(passObject.getAmountCompany(),originalObject.getAmountCompany(),"�ϼƣ���˾��"));
			out.print(KANUtil.getCompareHTML(passObject.getMonthly(),originalObject.getMonthly(),"�˵��·�"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"״̬"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"����","textarea"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>����ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="adjustmentHeaderId"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeLegalEntity"/>' /></li>
		<li><label>ҵ������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeBusinessType"/>' /></li>
		<li><label>�ͻ�ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientId"/>' /></li>
		<li><label>����ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="orderId"/>' /></li>
		<li><label>�ͻ����ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameZH"/>' /></li>
		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameEN"/>' /></li>
		<li><label>��ԱID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
		<li><label>����Э��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractId"/>' /></li>
		<li><label>�籣�����𷽰�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeSBId"/>' /></li>
		<li><label>�ϼƣ����ˣ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="amountPersonal"/>' /></li>
		<li><label>�ϼƣ���˾��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="amountCompany"/>' /></li>
		<li><label>״̬</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
		<li><label>����</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- ���޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>����ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="adjustmentHeaderId"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLegalEntity"/>' /></li>
		<li><label>ҵ������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBusinessType"/>' /></li>
		<li><label>�ͻ�ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientId"/>' /></li>
		<li><label>����ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="orderId"/>' /></li>
		<li><label>�ͻ����ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameZH"/>' /></li>
		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameEN"/>' /></li>
		<li><label>��ԱID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeId"/>' /></li>
		<li><label>����Э��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractId"/>' /></li>
		<li><label>�籣�����𷽰�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeSBId"/>' /></li>
		<li><label>�ϼƣ����ˣ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="amountPersonal"/>' /></li>
		<li><label>�ϼƣ���˾��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="amountCompany"/>' /></li>
		<li><label>״̬</label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		<li><label>����</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:notEqual>