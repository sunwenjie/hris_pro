<%@page import="com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			AdjustmentHeaderVO passObject = ( AdjustmentHeaderVO )request.getAttribute( "passObject" );
			AdjustmentHeaderVO originalObject = ( AdjustmentHeaderVO )request.getAttribute( "originalObject" );
			out.print(KANUtil.getCompareHTML(passObject.getAdjustmentHeaderId(),originalObject.getAdjustmentHeaderId(),"调整ID"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeLegalEntity(),originalObject.getDecodeLegalEntity(),"法务实体"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeBusinessType(),originalObject.getDecodeBusinessType(),"业务类型"));
			out.print(KANUtil.getCompareHTML(passObject.getClientId(),originalObject.getClientId(),"客户ID"));
			out.print(KANUtil.getCompareHTML(passObject.getOrderId() ,originalObject.getOrderId(),"订单ID"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameZH(),originalObject.getClientNameZH(),"客户名称（中文）"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameEN(),originalObject.getClientNameEN(),"客户名称（英文）"));
			out.print(KANUtil.getCompareHTML(passObject.getEmployeeId(),originalObject.getEmployeeId(),"雇员ID"));
			out.print(KANUtil.getCompareHTML(passObject.getContractId(),originalObject.getContractId(),"服务协议"));
			out.print(KANUtil.getCompareHTML(passObject.getAdjustmentDate(),originalObject.getAdjustmentDate(),"调整日期"));
			out.print(KANUtil.getCompareHTML(passObject.getBillAmountPersonal(),originalObject.getBillAmountPersonal(),"合计（个人收入）"));
			out.print(KANUtil.getCompareHTML(passObject.getBillAmountCompany(),originalObject.getBillAmountCompany(),"合计（公司营收）"));
			out.print(KANUtil.getCompareHTML(passObject.getCostAmountPersonal(),originalObject.getCostAmountPersonal(),"合计（个人支出）"));
			out.print(KANUtil.getCompareHTML(passObject.getCostAmountCompany(),originalObject.getCostAmountCompany(),"合计（个人）"));
			out.print(KANUtil.getCompareHTML(passObject.getDecodeStatus(),originalObject.getDecodeStatus(),"状态"));
			out.print(KANUtil.getCompareHTML(passObject.getDescription(),originalObject.getDescription(),"描述","textarea"));
		%>
	</ol>
</div>
<div id="tabContent2"  class="kantab" style="display: none" >
	<ol id="detail2OL" class="auto">
		<li><label>调整ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="adjustmentHeaderId"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeLegalEntity"/>' /></li>
		<li><label>业务类型</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="decodeBusinessType"/>' /></li>
		<li><label>客户ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientId"/>' /></li>
		<li><label>订单ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="orderId"/>' /></li>
		<li><label>客户名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameZH"/>' /></li>
		<li><label>客户名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameEN"/>' /></li>
		<li><label>雇员ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="employeeId"/>' /></li>
		<li><label>服务协议</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractId"/>' /></li>
		<li><label>调整日期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="adjustmentDate"/>' /></li>
		<li><label>合计（个人收入）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="billAmountPersonal"/>' /></li>
		<li><label>合计（公司营收）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="billAmountCompany"/>' /></li>
		<li><label>合计（个人支出）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="costAmountPersonal"/>' /></li>
		<li><label>合计（公司成本）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="costAmountCompany"/>' /></li>
		<li><label>状态</label><input type="text" disabled="disabled"   value='<bean:write name="originalObject" property="decodeStatus"/>' /></li>
		<li><label>描述</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:equal>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
<%-- 非修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<li><label>调整ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="adjustmentHeaderId"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLegalEntity"/>' /></li>
		<li><label>业务类型</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBusinessType"/>' /></li>
		<li><label>客户ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientId"/>' /></li>
		<li><label>订单ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="orderId"/>' /></li>
		<li><label>客户名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameZH"/>' /></li>
		<li><label>客户名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameEN"/>' /></li>
		<li><label>雇员ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="employeeId"/>' /></li>
		<li><label>服务协议</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractId"/>' /></li>
		<li><label>调整日期</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="adjustmentDate"/>' /></li>
		<li><label>合计（个人收入）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="billAmountPersonal"/>' /></li>
		<li><label>合计（公司营收）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="billAmountCompany"/>' /></li>
		<li><label>合计（个人支出）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="costAmountPersonal"/>' /></li>
		<li><label>合计（公司成本）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="costAmountCompany"/>' /></li>
		<li><label>状态</label><input type="text" disabled="disabled"   value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		<li><label>描述</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
	</ol>
</div>
</logic:notEqual>