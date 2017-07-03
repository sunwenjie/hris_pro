<%@page import="com.kan.hro.domain.biz.vendor.VendorVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			final VendorVO passObject = (VendorVO)request.getAttribute("passObject");
			final VendorVO originalObject = (VendorVO)request.getAttribute("originalObject");
			out.print(KANUtil.getCompareHTML(passObject.getVendorId(),originalObject.getVendorId(),"供应商ID"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"供应商名"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeLegalEntity(),originalObject.getDecodeLegalEntity(),"法务实体"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeType(),originalObject.getDecodeType(),"供应商类型"));
			out.print(KANUtil.getCompareHTML(passObject.getContractStartDate(),originalObject.getContractStartDate(),"合同开始日期"));
			out.print(KANUtil.getCompareHTML(passObject.getContractEndDate(),originalObject.getContractEndDate(),"合同结束日期"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeOwner(),originalObject.getDecodeOwner(),"所属人"));
			out.print(KANUtil.getCompareHTML(passObject.getCharterNumber(),originalObject.getCharterNumber(),"执照号"));
			out.print(KANUtil.getCompareHTML(passObject.getBankName(),originalObject.getBankName(),"账号银行名"));
			out.print(KANUtil.getCompareHTML(passObject.getBankAccount(),originalObject.getBankAccount(),"银行账号"));
			out.print(KANUtil.getCompareHTML(passObject.getBankAccountName(),originalObject.getBankAccountName(),"银行账号名"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeCityId(),originalObject.getDecodeCityId(),"城市"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"状态"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"描述","textarea"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>供应商ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="vendorId"/>' /></li>
		<li><label>供应商名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeLegalEntity"/>' /></li>
		<li><label>供应商类型</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeType"/>' /></li>
		<li><label>合同开始日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractStartDate"/>' /></li>
		<li><label>合同结束日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractEndDate"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeOwner"/>' /></li>
		<li><label>执照号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="charterNumber"/>' /></li>
		<li><label>账号银行名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="bankName"/>' /></li>
		<li><label>银行账号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="bankAccount"/>' /></li>
		<li><label>银行账号名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="bankAccountName"/>' /></li>
		<li><label>城市</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeCityId"/>' /></li>
		<li><label>状态</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
		<li><label>描述</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- 非修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>供应商ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="vendorId"/>' /></li>
		<li><label>供应商名</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="nameZH"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLegalEntity"/>' /></li>
		<li><label>供应商类型</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeType"/>' /></li>
		<li><label>合同开始日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractStartDate"/>' /></li>
		<li><label>合同结束日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractEndDate"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeOwner"/>' /></li>
		<li><label>执照号</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="charterNumber"/>' /></li>
		<li><label>账号银行名</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="bankName"/>' /></li>
		<li><label>银行账号</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="bankAccount"/>' /></li>
		<li><label>银行账号名</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="bankAccountName"/>' /></li>
		<li><label>城市</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeCityId"/>' /></li>
		<li><label>状态</label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		<li><label>描述</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:notEqual>

<script type="text/javascript">
	// 加载供应商服务
	loadHtml('#special_info', 'vendorServiceAction.do?proc=list_special_info_html&id=<%=( ( VendorVO )request.getAttribute( "passObject" ) ).getEncodedId() %>', false );
</script>