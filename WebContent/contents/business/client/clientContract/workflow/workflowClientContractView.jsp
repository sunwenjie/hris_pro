<%@page import="com.kan.hro.domain.biz.client.ClientContractVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			ClientContractVO passObject = (ClientContractVO)request.getAttribute("passObject");
			ClientContractVO originalObject = (ClientContractVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getClientNumber(),originalObject.getClientNumber(),"客户编号"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameZH(),originalObject.getClientNameZH(),"客户名称（中文）"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameEN(),originalObject.getClientNameEN(),"客户名称（英文）"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeEntityId(),originalObject.getDecodeEntityId(),"法务实体"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeBusinessTypeId(),originalObject.getDecodeBusinessTypeId(),"业务类型"));
			out.print(KANUtil.getCompareHTML(passObject.getContractNo(),originalObject.getContractNo(),"合同编号"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceAddressId(),originalObject.getInvoiceAddressId(),"发票地址"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"合同名称（中文）"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"合同名称（英文）"));
			out.print(KANUtil.getCompareHTML(passObject.getStartDate(),originalObject.getStartDate(),"开始时间"));
			out.print(KANUtil.getCompareHTML(passObject.getEndDate(),originalObject.getEndDate(),"结束时间"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"备注","textarea"));
			out.print(KANUtil.getCompareHTML(passObject.getStatus(),originalObject.getStatus(),"状态"));
			out.print(KANUtil.getCompareHTML(passObject.getBranch(),originalObject.getBranch(),"所属部门"));
			out.print(KANUtil.getCompareHTML(passObject.getOwner(),originalObject.getOwner(),"所属人"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>客户编号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNumber"/>' /></li>
		<li><label>客户名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameZH"/>' /></li>
		<li><label>客户名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameEN"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeEntityId"/>' /></li>
		<li><label>业务类型</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeBusinessTypeId"/>' /></li>
		<li><label>合同编号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNo"/>' /></li>
		<li><label>发票地址</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceAddressId"/>' /></li>
		<li><label>合同名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>合同名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>开始时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
		<li><label>结束时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
		<li><label>备注</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="description"/>' /></li>
		<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>所属部门</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- 非修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>客户编号</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNumber"/>' /></li>
		<li><label>客户名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameZH"/>' /></li>
		<li><label>客户名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameEN"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeEntityId"/>' /></li>
		<li><label>业务类型</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBusinessTypeId"/>' /></li>
		<li><label>合同编号</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractNo"/>' /></li>
		<li><label>发票地址</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="invoiceAddressId"/>' /></li>
		<li><label>合同名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>合同名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameEN"/>' /></li>
		<li><label>开始时间</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="startDate"/>' /></li>
		<li><label>结束时间</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="endDate"/>' /></li>
		<li><label>备注</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="description"/>' /></li>
		<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="status"/>' /></li>
		<li><label>所属部门</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="branch"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="owner"/>' /></li>
	</ol>
</div>
</logic:notEqual>