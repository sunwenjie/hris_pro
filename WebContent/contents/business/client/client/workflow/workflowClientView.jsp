<%@page import="com.kan.hro.domain.biz.client.ClientVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- �޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			ClientVO passObject = (ClientVO)request.getAttribute("passObject");
			ClientVO originalObject = (ClientVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getNumber(),originalObject.getNumber(),"�ͻ����"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"�ͻ�������"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"�ͻ�Ӣ����"));
			out.print(KANUtil.getCompareHTML(passObject.getGroupName(),originalObject.getGroupName(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getMainContact(),originalObject.getMainContact(),"��ϵ�ˣ�Ĭ�ϣ�"));
			out.print(KANUtil.getCompareHTML(passObject.getIndustry(),originalObject.getIndustry(),"��ҵ"));
			out.print(KANUtil.getCompareHTML(passObject.getSize(),originalObject.getSize(),"��˾��ģ"));
			out.print(KANUtil.getCompareHTML(passObject.getType(),originalObject.getType(),"��ҵ����"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceDate(),originalObject.getInvoiceDate(),"��Ʊ����"));
			out.print(KANUtil.getCompareHTML(passObject.getPaymentTerms(),originalObject.getPaymentTerms(),"�������ڣ��죩"));
			out.print(KANUtil.getCompareHTML(passObject.getProvinceId(),originalObject.getProvinceId(),"ʡ�� - ����"));
			out.print(KANUtil.getCompareHTML(passObject.getAddress(),originalObject.getAddress(),"��ַ"));
			out.print(KANUtil.getCompareHTML(passObject.getPostcode(),originalObject.getPostcode(),"�ʱ�"));
			out.print(KANUtil.getCompareHTML(passObject.getMobile(),originalObject.getMobile(),"�ֻ�"));
			out.print(KANUtil.getCompareHTML(passObject.getPhone(),originalObject.getPhone(),"�绰"));
			out.print(KANUtil.getCompareHTML(passObject.getFax(),originalObject.getFax(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getWebsite(),originalObject.getWebsite(),"��ַ"));
			out.print(KANUtil.getCompareHTML(passObject.getEmail(),originalObject.getEmail(),"����"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1(),originalObject.getIm1(),"IM1����"));
			out.print(KANUtil.getCompareHTML(passObject.getIm1Type(),originalObject.getIm1Type(),"IM1����"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2(),originalObject.getIm2(),"IM2����"));
			out.print(KANUtil.getCompareHTML(passObject.getIm2Type(),originalObject.getIm2Type(),"IM2����"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"��˾���","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getStatus(),originalObject.getStatus(),"״̬"));
			out.print(KANUtil.getCompareHTML(passObject.getLegalEntity(),originalObject.getLegalEntity(),"����ʵ��"));
			out.print(KANUtil.getCompareHTML(passObject.getBranch(),originalObject.getBranch(),"��������"));
			out.print(KANUtil.getCompareHTML(passObject.getOwner(),originalObject.getOwner(),"������"));
			out.print(KANUtil.getCompareHTML(passObject.getRecommendPerson(),originalObject.getRecommendPerson(),"�Ƽ���"));
			out.print(KANUtil.getCompareHTML(passObject.getRecommendBranch(),originalObject.getRecommendBranch(),"�Ƽ�����"));
			out.print(KANUtil.getCompareHTML(passObject.getRecommendPosition(),originalObject.getRecommendPosition(),"�Ƽ�ְλ"));
			
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>�ͻ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="number"/>' /></li>
		<li><label>�ͻ�������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>�ͻ�Ӣ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="groupName"/>' /></li>
		<li><label>��ϵ�ˣ�Ĭ�ϣ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mainContact"/>' /></li>
		<li><label>��ҵ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="industry"/>' /></li>
		<li><label>��˾��ģ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="size"/>' /></li>
		<li><label>��˾����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="type"/>' /></li>
		<li><label>��Ʊ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceDate"/>' /></li>
		<li><label>�������ڣ��죩</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="paymentTerms"/>' /></li>
		<li><label>ʡ�� - ����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="provinceId"/>' /></li>
		<li><label>��ַ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="address"/>' /></li>
		<li><label>�ʱ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="postcode"/>' /></li>
		<li><label>�ֻ�</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="mobile"/>' /></li>
		<li><label>�绰</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="phone"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="fax"/>' /></li>
		<li><label>��ַ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="website"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="email"/>' /></li>
		<li><label>IM1����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1"/>' /></li>
		<li><label>IM1����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im1Type"/>' /></li>
		<li><label>IM2����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2"/>' /></li>
		<li><label>IM2����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="im2Type"/>' /></li>
		<li><label>��˾���</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="legalEntity"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
		<li><label>�Ƽ���</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recommendPerson"/>' /></li>
		<li><label>�Ƽ�����</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recommendBranch"/>' /></li>
		<li><label>�Ƽ�ְλ</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="recommendPosition"/>' /></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- ���޸����͵��������� --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>�ͻ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="number"/>' /></li>
		<li><label>�ͻ�������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>�ͻ�Ӣ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="groupName"/>' /></li>
		<li><label>��ϵ�ˣ�Ĭ�ϣ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mainContact"/>' /></li>
		<li><label>��ҵ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="industry"/>' /></li>
		<li><label>��˾��ģ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="size"/>' /></li>
		<li><label>��˾����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="type"/>' /></li>
		<li><label>��Ʊ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="invoiceDate"/>' /></li>
		<li><label>�������ڣ��죩</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="paymentTerms"/>' /></li>
		<li><label>ʡ�� - ����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="provinceId"/>' /></li>
		<li><label>��ַ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="address"/>' /></li>
		<li><label>�ʱ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="postcode"/>' /></li>
		<li><label>�ֻ�</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="mobile"/>' /></li>
		<li><label>�绰</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="phone"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="fax"/>' /></li>
		<li><label>��ַ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="website"/>' /></li>
		<li><label>����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="email"/>' /></li>
		<li><label>IM1����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1"/>' /></li>
		<li><label>IM1����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im1Type"/>' /></li>
		<li><label>IM2����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2"/>' /></li>
		<li><label>IM2����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="im2Type"/>' /></li>
		<li><label>��˾���</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
		<li><label>״̬</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="status"/>' /></li>
		<li><label>����ʵ��</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="legalEntity"/>' /></li>
		<li><label>��������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="branch"/>' /></li>
		<li><label>������</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="owner"/>' /></li>
		<li><label>�Ƽ���</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recommendPerson"/>' /></li>
		<li><label>�Ƽ�����</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recommendBranch"/>' /></li>
		<li><label>�Ƽ�ְλ</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="recommendPosition"/>' /></li>
	</ol>
</div>
</logic:notEqual>