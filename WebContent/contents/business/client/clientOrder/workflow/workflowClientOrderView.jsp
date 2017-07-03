<%@ page import="com.kan.base.domain.MappingVO"%>
<%@ page import="java.util.List"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderHeaderVO"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	final ClientOrderHeaderVO passObject = ( ClientOrderHeaderVO )request.getAttribute( "passObject" );
	final ClientOrderHeaderVO originalObject = ( ClientOrderHeaderVO )request.getAttribute( "originalObject" );
%>

<logic:equal  name="workflowActualVO" property="rightId" value="3">
<%-- 修改类型的审批流程 --%>
<div id="tabContent1"  class="kantab"  >
	<ol id="detailOL" class="auto">
		<%
			out.print(KANUtil.getCompareHTML(passObject.getClientNumber(),originalObject.getClientNumber(),"客户编号"));
			out.print(KANUtil.getCompareHTML(passObject.getOrderHeaderId(),originalObject.getOrderHeaderId(),"订单ID"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameZH(),originalObject.getClientNameZH(),"客户中文名"));
			out.print(KANUtil.getCompareHTML(passObject.getClientNameEN(),originalObject.getClientNameEN(),"客户英文名"));
			out.print(KANUtil.getCompareHTML(passObject.getClientName(),originalObject.getClientName(),"客户名称"));
			out.print(KANUtil.getCompareHTML(passObject.getClientId(),originalObject.getClientId(),"客户ID"));
			out.print(KANUtil.getCompareHTML(passObject.getContractNumber(),originalObject.getContractNumber(),"合同编号"));
			out.print(KANUtil.getCompareHTML(passObject.getContractId(),originalObject.getContractId(),"商务合同"));
			out.print(KANUtil.getCompareHTML(passObject.getBusinessTypeId(),originalObject.getBusinessTypeId(),"业务类型"));
			out.print(KANUtil.getCompareHTML(passObject.getNameZH(),originalObject.getNameZH(),"订单名称（中文）"));
			out.print(KANUtil.getCompareHTML(passObject.getNameEN(),originalObject.getNameEN(),"订单名称（英文）"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceAddressId(),originalObject.getInvoiceAddressId(),"发票地址"));
			out.print(KANUtil.getCompareHTML(passObject.getStartDate(),originalObject.getStartDate(),"开始时间"));
			out.print(KANUtil.getCompareHTML(passObject.getEndDate(),originalObject.getEndDate(),"结束时间"));
			out.print(KANUtil.getCompareHTML(passObject.getSalesType(),originalObject.getSalesType(),"销售方式"));
			out.print(KANUtil.getCompareHTML(passObject.getServiceScope(),originalObject.getServiceScope(),"服务内容"));
			out.print(KANUtil.getCompareHTML(passObject.getSettlementType(),originalObject.getSettlementType(),"结算方式"));
			out.print(KANUtil.getCompareHTML(passObject.getInvoiceType(),originalObject.getInvoiceType(),"开票方式"));
			out.print(KANUtil.getCompareHTML(passObject.getEntityId(),originalObject.getEntityId(),"法务实体"));
			out.print(KANUtil.getCompareHTML(passObject.getTaxId(),originalObject.getTaxId(),"税率"));
			out.print(KANUtil.getCompareHTML(passObject.getAttendanceCheckType(),originalObject.getAttendanceCheckType(),"考勤方式"));
			out.print(KANUtil.getCompareHTML(passObject.getAttendanceGenerate(),originalObject.getAttendanceGenerate(),"考勤生成"));
			out.print(KANUtil.getCompareHTML(passObject.getPayrollDay(),originalObject.getPayrollDay(),"发薪日期（每月）"));
			out.print(KANUtil.getCompareHTML(passObject.getCircleStartDay(),originalObject.getCircleStartDay(),"记薪周期（开始）"));
			out.print(KANUtil.getCompareHTML(passObject.getCircleEndDay(),originalObject.getCircleEndDay(),"记薪周期（结束）"));
			out.print(KANUtil.getCompareHTML(passObject.getCalendarId(),originalObject.getCalendarId(),"日历"));
			out.print(KANUtil.getCompareHTML(passObject.getShiftId(),originalObject.getShiftId(),"排班"));
			out.print(KANUtil.getCompareHTML(passObject.getSalaryMonth(),originalObject.getSalaryMonth(),"工资月份"));
			out.print(KANUtil.getCompareHTML(passObject.getSbMonth(),originalObject.getSbMonth(),"社保公积金月份"));
			out.print(KANUtil.getCompareHTML(passObject.getCbMonth(),originalObject.getCbMonth(),"商报月份"));
			out.print(KANUtil.getCompareHTML(passObject.getProbationMonth(),originalObject.getProbationMonth(),"试用期"));
			out.print(KANUtil.getCompareHTML(passObject.getPersonalSBBurden(),originalObject.getPersonalSBBurden(),"公司承担个人社保公积金"));
			out.print(KANUtil.getCompareHTML(passObject.getLocked(),originalObject.getLocked(),"锁定"));
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
		<li><label>订单ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="orderHeaderId"/>' /></li>
		<li><label>客户中文名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameZH"/>' /></li>
		<li><label>客户英文名</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientNameEN"/>' /></li>
		<li><label>客户名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientName"/>' /></li>
		<li><label>客户ID</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="clientId"/>' /></li>
		<%-- <li><label>是否绑定合同</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="orderBindContract"/>' /></li> --%>
		<li><label>合同编号</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNumber"/>' /></li>
		<li><label>合同名称</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractNameZH"/>' /></li>
		<li><label>商务合同</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="contractId"/>' /></li>
		<li><label>业务类型</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="businessTypeId"/>' /></li>
		<li><label>订单名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameZH"/>' /></li>
		<li><label>订单名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="nameEN"/>' /></li>
		<li><label>发票地址</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceAddressId"/>' /></li>
		<li><label>开始时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="startDate"/>' /></li>
		<li><label>结束时间</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="endDate"/>' /></li>
		<li><label>销售方式</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="salesType"/>' /></li>
		<li><label>服务内容</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="serviceScope"/>' /></li>
		<li><label>结算方式</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="settlementType"/>' /></li>
		<li><label>开票方式</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="invoiceType"/>' /></li>
		<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="entityId"/>' /></li>
		<li><label>税率</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="taxId"/>' /></li>
		<li><label>考勤方式</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="attendanceCheckType"/>' /></li>
		<li><label>考勤生成</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="attendanceGenerate"/>' /></li>
		<li><label>发薪日期（每月）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="payrollDay"/>' /></li>
		<li><label>记薪周期（开始）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="circleStartDay"/>' /></li>
		<li><label>记薪周期（结束）</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="circleEndDay"/>' /></li>
		<li><label>日历</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="calendarId"/>' /></li>
		<li><label>排班</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="shiftId"/>' /></li>
		<li><label>工资月份</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="salaryMonth"/>' /></li>
		<li><label>社保公积金月份</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="sbMonth"/>' /></li>
		<li><label>商报月份</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="cbMonth"/>' /></li>
		<li><label>试用期</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="probationMonth"/>' /></li>
		<li><label>公司承担个人社保公积金</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="personalSBBurden"/>' /></li>
		<li><label>锁定</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="locked"/>' /></li>
		<li><label>备注</label><textarea disabled="disabled"><bean:write name="originalObject" property="description"/></textarea></li>
		<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="status"/>' /></li>
		<li><label>所属部门</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="branch"/>' /></li>
		<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="originalObject" property="owner"/>' /></li>
	</ol>
</div>
</logic:equal>

<%-- 非修改类型的审批流程 --%>
<logic:notEqual name="workflowActualVO" property="rightId" value="3">
	<div id="tabContent1"  class="kantab"  >
		<ol class="auto">
			<li><label>订单ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="orderHeaderId"/>' /></li>
		</ol>
		<ol class="auto">
			<li><label>开始时间</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="startDate"/>' /></li>
			<li><label>结束时间</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="endDate"/>' /></li>
			<li><label>企业ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientId"/>' /></li>
			<li><label>财务编码</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNumber"/>' /></li>
			<li><label>企业名称（中文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameZH"/>' /></li>
			<li><label>企业名称（英文）</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="clientNameEN"/>' /></li>
		</ol>	
		<ol class="auto">
			<li><label>商务合同ID</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="contractId"/>' /></li>
		</ol>
		<ol class="auto">	
			<li><label>法务实体</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeEntityId"/>' /></li>
			<li><label>业务类型</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBusinessTypeId"/>' /></li>
			<li><label>结算方式</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSettlementType"/>' /></li>
			<li><label>开票方式</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeInvoiceType"/>' /></li>
			<li><label>销售方式</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeSalesType"/>' /></li>
			<li><label>服务内容</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeServiceScope"/>' /></li>
			<li><label>锁定</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeLocked"/>' /></li>
			<li><label>状态</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeStatus"/>' /></li>
		</ol>
		<ol class="auto">	
			<li><label>备注</label><textarea disabled="disabled"><bean:write name="passObject" property="description"/></textarea></li>
			<li>
				<label>合同到期提醒</label>
				<%
					final List< MappingVO > tipTypes = KANUtil.getMappings( request.getLocale(), "business.client.order.header.noticeExpire" );
				
				    String[] noticeExpireArray = null;
				    
					if( passObject != null )
					{
					   noticeExpireArray = KANUtil.jasonArrayToStringArray( passObject.getNoticeExpire() );
					}
					if( tipTypes != null && tipTypes.size() > 0 )
					{
					   out.print( "<span>" );
					   for( MappingVO mappingVO : tipTypes )
					   {
					      String checked = "";
					      if( noticeExpireArray != null && noticeExpireArray.length > 0 )
					      {
					         for( String s : noticeExpireArray )
					         {
					            if( mappingVO.getMappingId().equals( s ) )
					            {
					               checked = "checked=\"checked\"";
					               break;
					            }
					         }
					      }  
					      out.print( "<input type=\"checkbox\" disabled " + checked + "value=\"" + mappingVO.getMappingId() + "\" />" + mappingVO.getMappingValue() + " " );
					   }
					   out.print( "</span>" );
					}
				%>
			</li>
			<li>
				<label>试用期到期提醒</label>
				<%
				
				    String[] noticeProbationExpireArray = null;
				    
					if( passObject != null )
					{
					   noticeProbationExpireArray = KANUtil.jasonArrayToStringArray( passObject.getNoticeProbationExpire() );
					}
					if( tipTypes != null && tipTypes.size() > 0 )
					{
					   out.print( "<span>" );
					   for( MappingVO mappingVO : tipTypes )
					   {
					      String checked = "";
					      if( noticeProbationExpireArray != null && noticeProbationExpireArray.length > 0 )
					      {
					         for( String s : noticeProbationExpireArray )
					         {
					            if( mappingVO.getMappingId().equals( s ) )
					            {
					               checked = "checked=\"checked\"";
					               break;
					            }
					         }
					      }  
					      out.print( "<input type=\"checkbox\" disabled " + checked + "value=\"" + mappingVO.getMappingId() + "\" />" + mappingVO.getMappingValue() + " " );
					   }
					   out.print( "</span>" );
					}
				%>
			</li>
		</ol>
		<ol class="auto">	
			<li><label>所属部门</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeBranch"/>' /></li>
			<li><label>所属人</label><input type="text" disabled="disabled" value='<bean:write name="passObject" property="decodeOwner"/>' /></li>
		</ol>
		<br/>
		<div id="special_info_tab">
		
		</div>
	</div>
</logic:notEqual>

<script type="text/javascript">
	// 加载Tab
	loadHtml('#special_info_tab', 'clientOrderHeaderAction.do?proc=list_special_info_html&comeFrom=workflow&orderHeaderId=<bean:write name="originalObject" property="encodedId"/>', true );
</script>

