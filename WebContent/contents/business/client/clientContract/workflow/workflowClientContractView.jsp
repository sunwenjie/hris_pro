<%@page import="com.kan.hro.domain.biz.client.ClientContractVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- �޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			ClientContractVO passObject = (ClientContractVO)request.getAttribute("passObject");
			ClientContractVO originalObject = (ClientContractVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getClientNumber(),originalObject.getClientNumber(),"�ͻ����"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameZH(),originalObject.getClientNameZH(),"�ͻ����ƣ����ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameEN(),originalObject.getClientNameEN(),"�ͻ����ƣ�Ӣ�ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeEntityId(),originalObject.getDecodeEntityId(),"����ʵ��"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeBusinessTypeId(),originalObject.getDecodeBusinessTypeId(),"ҵ������"));
			out.print(KANUtil.getCompareHTML(passObject.getContractNo(),originalObject.getContractNo(),"��ͬ���"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceAddressId(),originalObject.getInvoiceAddressId(),"��Ʊ��ַ"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"��ͬ���ƣ����ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"��ͬ���ƣ�Ӣ�ģ�"));
			out.print(KANUtil.getCompareHTML(passObject.getStartDate(),originalObject.getStartDate(),"��ʼʱ��"));
			out.print(KANUtil.getCompareHTML(passObject.getEndDate(),originalObject.getEndDate(),"����ʱ��"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"��ע","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getStatus(),originalObject.getStatus(),"״̬"));
			out.print(KANUtil.getCompareHTML(passObject.getBranch(),originalObject.getBranch(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getOwner(),originalObject.getOwner(),"������"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>�ͻ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNumber"/>' /></li>
		<li><label>�ͻ����ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameZH"/>' /></li>
		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameEN"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeEntityId"/>' /></li>
		<li><label>ҵ������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeBusinessTypeId"/>' /></li>
		<li><label>��ͬ���</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNo"/>' /></li>
		<li><label>��Ʊ��ַ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceAddressId"/>' /></li>
		<li><label>��ͬ���ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>��ͬ���ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>��ʼʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
		<li><label>����ʱ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
		<li><label>��ע</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="description"/>' /></li>
		<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- ���޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>�ͻ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNumber"/>' /></li>
		<li><label>�ͻ����ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameZH"/>' /></li>
		<li><label>�ͻ����ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameEN"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeEntityId"/>' /></li>
		<li><label>ҵ������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBusinessTypeId"/>' /></li>
		<li><label>��ͬ���</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractNo"/>' /></li>
		<li><label>��Ʊ��ַ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="invoiceAddressId"/>' /></li>
		<li><label>��ͬ���ƣ����ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>��ͬ���ƣ�Ӣ�ģ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>��ʼʱ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="startDate"/>' /></li>
		<li><label>����ʱ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="endDate"/>' /></li>
		<li><label>��ע</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="description"/>' /></li>
		<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="status"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="branch"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="owner"/>' /></li>
	</ol>
</div>
</logic:notEqual>