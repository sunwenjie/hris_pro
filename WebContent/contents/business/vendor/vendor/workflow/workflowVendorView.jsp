<%@page import="com.kan.hro.domain.biz.vendor.VendorVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- �޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			final VendorVO passObject = (VendorVO)request.getAttribute("passObject");
			final VendorVO originalObject = (VendorVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getVendorId(),originalObject.getVendorId(),"��Ӧ��ID"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"��Ӧ����"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeLegalEntity(),originalObject.getDecodeLegalEntity(),"����ʵ��"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeType(),originalObject.getDecodeType(),"��Ӧ������"));
			out.print(KANUtil.getCompareHTML(passObject.getContractStartDate(),originalObject.getContractStartDate(),"��ͬ��ʼ����"));
			out.print(KANUtil.getCompareHTML(passObject.getContractEndDate(),originalObject.getContractEndDate(),"��ͬ��������"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeOwner(),originalObject.getDecodeOwner(),"������"));
			out.print(KANUtil.getCompareHTML(passObject.getCharterNumber(),originalObject.getCharterNumber(),"ִ�պ�"));
			out.print(KANUtil.getCompareHTML(passObject.getBankName(),originalObject.getBankName(),"�˺�������"));
			out.print(KANUtil.getCompareHTML(passObject.getBankAccount(),originalObject.getBankAccount(),"�����˺�"));
			out.print(KANUtil.getCompareHTML(passObject.getBankAccountName(),originalObject.getBankAccountName(),"�����˺���"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeCityId(),originalObject.getDecodeCityId(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"״̬"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"����","textarea"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>��Ӧ��ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="vendorId"/>' /></li>
		<li><label>��Ӧ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeLegalEntity"/>' /></li>
		<li><label>��Ӧ������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeType"/>' /></li>
		<li><label>��ͬ��ʼ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractStartDate"/>' /></li>
		<li><label>��ͬ��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractEndDate"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
		<li><label>ִ�պ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="charterNumber"/>' /></li>
		<li><label>�˺�������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="bankName"/>' /></li>
		<li><label>�����˺�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="bankAccount"/>' /></li>
		<li><label>�����˺���</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="bankAccountName"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeCityId"/>' /></li>
		<li><label>״̬</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
		<li><label>����</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- ���޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>��Ӧ��ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="vendorId"/>' /></li>
		<li><label>��Ӧ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLegalEntity"/>' /></li>
		<li><label>��Ӧ������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeType"/>' /></li>
		<li><label>��ͬ��ʼ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractStartDate"/>' /></li>
		<li><label>��ͬ��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractEndDate"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeOwner"/>' /></li>
		<li><label>ִ�պ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="charterNumber"/>' /></li>
		<li><label>�˺�������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="bankName"/>' /></li>
		<li><label>�����˺�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="bankAccount"/>' /></li>
		<li><label>�����˺���</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="bankAccountName"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeCityId"/>' /></li>
		<li><label>״̬</label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		<li><label>����</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:notEqual>

<script type="text/javascript">
	// ���ع�Ӧ�̷���
	loadHtml('#special_info', 'vendorServiceAction.do?proc=list_special_info_html&id=<%=( ( VendorVO )request.getAttribute( "passObject" ) ).getEncodedId() %>', false );
</script>