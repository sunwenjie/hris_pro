<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderHeaderVO"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderCBAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderLeaveAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderSBAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderOTAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderOtherAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderHeaderRuleAction"%>
<%@ page import="com.kan.hro.web.actions.biz.client.ClientOrderDetailAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<% 
	final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) request.getAttribute("clientOrderHeaderForm"); 
%>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="_tabMenu1" onClick="_changeTab(1,11)" class="first hover">雇员 (<span id="numberOfEmployee"><bean:write name="numberOfEmployee" /></span>)</li>
			<li id="_tabMenu2" onClick="_changeTab(2,11)">总规则 (<span id="numberOfClientOrderHeaderRule"><bean:write name="numberOfClientOrderHeaderRule" /></span>)</li> 
			<li id="_tabMenu3" onClick="_changeTab(3,11)">收费 (<span id="numberOfClientOrderServiceFee"><bean:write name="numberOfClientOrderServiceFee" /></span>)</li> 
			<li id="_tabMenu4" onClick="_changeTab(4,11)">薪酬 </li>
			<li id="_tabMenu5" onClick="_changeTab(5,11)">社保公积金方案 (<span id="numberOfClientOrderSB"><bean:write name="numberOfClientOrderSB" /></span>)</li> 
			<li id="_tabMenu6" onClick="_changeTab(6,11)">商保方案 (<span id="numberOfClientOrderCB"><bean:write name="numberOfClientOrderCB" /></span>)</li> 
			<li id="_tabMenu7" onClick="_changeTab(7,11)">休假设置 (<span id="numberOfClientOrderLeave"><bean:write name="numberOfClientOrderLeave" /></span>)</li> 
			<li id="_tabMenu8" onClick="_changeTab(8,11)">加班设置 (<span id="numberOfClientOrderOT"><bean:write name="numberOfClientOrderOT" /></span>)</li> 
			<li id="_tabMenu9" onClick="_changeTab(9,11)">其他设置 (<span id="numberOfClientOrderOther"><bean:write name="numberOfClientOrderOther" /></span>)</li> 
			<li id="_tabMenu10" onClick="_changeTab(10,11)">附件 (<span id="numberOfAttachment"><bean:write name="numberOfAttachment" /></span>)</li> 
			<li id="_tabMenu11" onClick="_changeTab(11,11)">结算</li> 
		</ul> 
	</div> 
	<div class="tabContent">
		<div id="_tabContent1" class="kantab">
			<div id="tableWrapperContractService" class="tableWrapperContractService">
				<!--Service Contract Information Table--> 
				<jsp:include page="/contents/business/client/clientOrder/table/listServiceContractTable.jsp" flush="true"/>
			</div>
		</div>
		<div id="_tabContent2" class="kantab" style="display:none" >
			<ol class="auto">
				<logic:notEmpty name="clientOrderHeaderRuleVOs">
					<logic:iterate id="clientOrderHeaderRuleVO" name="clientOrderHeaderRuleVOs" indexId="number">
						<li>
							<bean:write name="clientOrderHeaderRuleVO" property="decodeRuleType" /> <logic:equal name="clientOrderHeaderRuleVO" property="ruleType" value="2">￥</logic:equal><bean:write name="clientOrderHeaderRuleVO" property="ruleValue" /><logic:equal name="clientOrderHeaderRuleVO" property="ruleType" value="1">人</logic:equal> / <bean:write name="clientOrderHeaderRuleVO" property="ruleResult" />%
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		<div id="_tabContent3" class="kantab" style="display:none" >
			<ol class="auto">
				<logic:notEmpty name="clientOrderDetailVOs">
					<logic:iterate id="clientOrderDetailVO" name="clientOrderDetailVOs" indexId="number">
						<li>
							<bean:write name="clientOrderDetailVO" property="decodeItemId" /> <logic:greaterThan name="clientOrderDetailVO" property="base" value="0">（￥<bean:write name="clientOrderDetailVO" property="base" /> / <bean:write name="clientOrderDetailVO" property="decodePackageType" />）</logic:greaterThan> <bean:write name="clientOrderDetailVO" property="startDate" /><logic:notEmpty name="clientOrderDetailVO" property="endDate"> ~ <bean:write name="clientOrderDetailVO" property="endDate" /></logic:notEmpty>
							<logic:equal name="clientOrderDetailVO" property="status" value="2"><span class="highlight">（停用）</span></logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		<div id="_tabContent4" class="kantab" style="display:none">
			<ol class="auto">
				<li>
					<label>发薪日期（每月）</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getPayrollDays(), "payrollDay", "payrollDay", clientOrderHeaderVO.getPayrollDay(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>计薪周期（开始）</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCircleStartDays(), "circleStartDay", "circleStartDay", clientOrderHeaderVO.getCircleStartDay(), null , null) %>
				</li>
				<li>
					<label>计薪周期（结束）</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCircleEndDays(), "circleEndDay", "circleEndDay", clientOrderHeaderVO.getCircleEndDay(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>薪酬供应商</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSalaryVendors(), "salaryVendorId", "salaryVendorId", clientOrderHeaderVO.getSalaryVendorId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>个税起征</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getIncomeTaxBases(), "incomeTaxBaseId", "incomeTaxBaseId", clientOrderHeaderVO.getIncomeTaxBaseId(), null , null) %>
				</li>
				<li>
					<label>个税税率</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getIncomeTaxRangeHeaders(), "incomeTaxRangeHeaderId", "incomeTaxRangeHeaderId", clientOrderHeaderVO.getIncomeTaxRangeHeaderId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li id="salaryTypeLI">
					<label>计薪方式</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSalaryTypes(), "salaryType", "salaryType", clientOrderHeaderVO.getSalaryType(), null , null) %>
				</li>
				<li id="divideTypeLI">
					<label>非全勤计算规则</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getDivideTypes(), "divideType", "divideType", clientOrderHeaderVO.getDivideType(), null , null) %>
				</li>
				<li id="excludeDivideItemIdsLI">
					<label>以下假期是否视<br/>为工作日，如不<br/>是，去除勾选 <img src="images/tips.png" title="针对非基本工资等企业可自行规定支付规则的项目，如“用餐补贴”按实际工作日支付，则以下假期全部去除勾选，“基本工资”按政府规定支付的项目，并不受此规则影响" /></label>
					<div style="width: 215px;">
						<%
							final String subAction = ((ClientOrderHeaderVO)request.getAttribute("clientOrderHeaderForm")).getSubAction();
							String excludeDivideItemIds = "";
							if(BaseAction.VIEW_OBJECT.equalsIgnoreCase(subAction)){
							   // 如果是 查看。
							   excludeDivideItemIds = clientOrderHeaderVO.getExcludeDivideItemIds();
							}else{
							   // 勾选全部
							   excludeDivideItemIds = KANUtil.formatMappingVOIds2StringJsonArray(clientOrderHeaderVO.getExcludeDivideItems());
							}
						%>
						<%=KANUtil.getCheckBoxHTML(clientOrderHeaderVO.getExcludeDivideItems(), "excludeDivideItemIds", excludeDivideItemIds, subAction, "<br/>") %>
					</div>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>考勤审批方式</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getAttendanceCheckTypes(), "attendanceCheckType", "attendanceCheckType", clientOrderHeaderVO.getAttendanceCheckType(), null , null) %>
				</li>
				<li>
					<label>考勤生成时间</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getAttendanceGenerates(), "attendanceGenerate", "attendanceGenerate", clientOrderHeaderVO.getAttendanceGenerate(), null , null) %>
				</li>
				<li>
					<label>审核方式</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getApproveTypes(), "approveType", "approveType", clientOrderHeaderVO.getApproveType(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>试用期期间</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getProbationMonths(), "probationMonth", "probationMonth", clientOrderHeaderVO.getProbationMonth(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>工作日历</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCalendarIds(), "calendarId", "calendarId", clientOrderHeaderVO.getCalendarId(), null , null) %>
				</li>
				<li>
					<label>排班</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getShiftIds(), "shiftId", "shiftId", clientOrderHeaderVO.getShiftId(), null , null) %>
				</li>
				<li>
					<label>病假工资</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSickLeaveSalaryIds(), "sickLeaveSalaryId", "sickLeaveSalaryId", clientOrderHeaderVO.getSickLeaveSalaryId(), null , null) %>
				</li>
			</ol>
		</div>
		<div id="_tabContent5" class="kantab" style="display:none" >
			<ol class="auto">
				<li>
					<label>公司承担个人社保公积金</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getFlags(), "personalSBBurden", "personalSBBurden", clientOrderHeaderVO.getPersonalSBBurden(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<logic:notEmpty name="clientOrderSBVOs">
					<logic:iterate id="clientOrderSBVO" name="clientOrderSBVOs" indexId="number">
						<li>
							<bean:write name="clientOrderSBVO" property="sbSolutionName" />
							<logic:equal name="clientOrderSBVO" property="status" value="2"><span class="highlight">（停用）</span></logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		<div id="_tabContent6" class="kantab" style="display:none" >
			<ol class="auto">
				<logic:notEmpty name="clientOrderCBVOs">
					<logic:iterate id="clientOrderCBVO" name="clientOrderCBVOs" indexId="number">
						<li>
							<bean:write name="clientOrderCBVO" property="cbSolutionName" />
							<logic:equal name="clientOrderCBVO" property="status" value="2"><span class="highlight">（停用）</span></logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		<div id="_tabContent7" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="clientOrderLeaveVOs">
					<logic:iterate id="clientOrderLeaveVO" name="clientOrderLeaveVOs" indexId="number">
						<li>
							<bean:write name="clientOrderLeaveVO" property="decodeItemId" /> （<bean:write name="clientOrderLeaveVO" property="benefitQuantity" />小时<logic:equal name="clientOrderLeaveVO" property="itemId" value="41">，法定<bean:write name="clientOrderLeaveVO" property="legalQuantity" />小时</logic:equal>）
							<logic:equal name="clientOrderLeaveVO" property="status" value="2"><span class="highlight">（停用）</span></logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		<div id="_tabContent8" class="kantab" style="display:none">
			<ol class="auto">
				<li>
					<label>加班需要申请</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getFlags(), "applyOTFirst", "applyOTFirst", clientOrderHeaderVO.getApplyOTFirst(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>每天加班上限（小时）</label>
					<input type="text" id="otLimitByDay" name="otLimitByDay" maxlength="2" class="otLimitByDay" style="" value="<bean:write name="clientOrderHeaderForm" property="otLimitByDay"/>" />
				</li>
				<li>
					<label>每月加班上限（小时）</label>
					<input type="text" id="otLimitByMonth" name="otLimitByMonth" maxlength="3" class="otLimitByMonth" style="" value="<bean:write name="clientOrderHeaderForm" property="otLimitByMonth"/>" />
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>工作日加班科目</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "workdayOTItemId", "workdayOTItemId", clientOrderHeaderVO.getWorkdayOTItemId(), null , null) %>
				</li>
				<li>
					<label>休息日加班科目</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "weekendOTItemId", "weekendOTItemId", clientOrderHeaderVO.getWeekendOTItemId(), null , null) %>
				</li>
				<li>
					<label>节假日加班科目</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "holidayOTItemId", "holidayOTItemId", clientOrderHeaderVO.getHolidayOTItemId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<logic:notEmpty name="clientOrderOTVOs">
					<logic:iterate id="clientOrderOTVO" name="clientOrderOTVOs" indexId="number">
						<li>
							<bean:write name="clientOrderOTVO" property="decodeItemId" />
							（
								<logic:greaterThan name="clientOrderOTVO" property="baseFrom" value="0">
									<bean:write name="clientOrderOTVO" property="decodeBaseFrom" />
									<logic:greaterThan name="clientOrderOTVO" property="percentage" value="0">
										&nbsp;*&nbsp;<bean:write name="clientOrderOTVO" property="percentage" />%
									</logic:greaterThan>
									<logic:greaterThan name="clientOrderOTVO" property="fix" value="0">
										&nbsp;+&nbsp;￥<bean:write name="clientOrderOTVO" property="fix" />
									</logic:greaterThan>
								</logic:greaterThan>
								<logic:lessThan name="clientOrderOTVO" property="baseFrom" value="1">
									<logic:greaterThan name="clientOrderOTVO" property="base" value="0">
										￥<bean:write name="clientOrderOTVO" property="base" />
									</logic:greaterThan> 
								</logic:lessThan>
								<logic:greaterThan name="clientOrderOTVO" property="discount" value="0">
									&nbsp;*&nbsp;<bean:write name="clientOrderOTVO" property="discount" />%
								</logic:greaterThan>
								<logic:greaterThan name="clientOrderOTVO" property="multiple" value="0">
									&nbsp;*&nbsp;<bean:write name="clientOrderOTVO" property="decodeMultiple" />
								</logic:greaterThan>
							）
							<bean:write name="clientOrderOTVO" property="startDate" />
							<logic:notEmpty name="clientOrderOTVO" property="endDate"> ~ <bean:write name="clientOrderOTVO" property="endDate" /></logic:notEmpty>
							<logic:equal name="clientOrderOTVO" property="status" value="2"><span class="highlight">（停用）</span></logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		<div id="_tabContent9" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="clientOrderOtherVOs">
					<logic:iterate id="clientOrderOtherVO" name="clientOrderOtherVOs" indexId="number">
						<li>
							<bean:write name="clientOrderOtherVO" property="decodeItemId" /> 
							（
								<logic:greaterThan name="clientOrderOtherVO" property="baseFrom" value="0">
									<bean:write name="clientOrderOtherVO" property="decodeBaseFrom" />
									<logic:greaterThan name="clientOrderOtherVO" property="percentage" value="0">
										&nbsp;*&nbsp;<bean:write name="clientOrderOtherVO" property="percentage" />%
									</logic:greaterThan>
									<logic:greaterThan name="clientOrderOtherVO" property="fix" value="0">
										&nbsp;+&nbsp;￥<bean:write name="clientOrderOtherVO" property="fix" />
									</logic:greaterThan>
								</logic:greaterThan>
								<logic:lessThan name="clientOrderOtherVO" property="baseFrom" value="1">
									<logic:greaterThan name="clientOrderOtherVO" property="base" value="0">
										￥<bean:write name="clientOrderOtherVO" property="base" />
									</logic:greaterThan> 
								</logic:lessThan>
							 / 
								<logic:greaterThan name="clientOrderOtherVO" property="cycle" value="0">
									<logic:notEqual name="clientOrderOtherVO" property="cycle" value="13">每</logic:notEqual>
									<bean:write name="clientOrderOtherVO" property="decodeCycle" />
								</logic:greaterThan>
							）
							<bean:write name="clientOrderOtherVO" property="startDate" />
							<logic:notEmpty name="clientOrderOtherVO" property="endDate"> ~ <bean:write name="clientOrderOtherVO" property="endDate" /></logic:notEmpty>
							<logic:equal name="clientOrderOtherVO" property="status" value="2"><span class="highlight">（停用）</span></logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		<div id="_tabContent10" class="kantab" style="display:none">
			<div id="attachmentsDiv">
				<ol id="attachmentsOL" class="auto">
					<%= AttachmentRender.getAttachments(request, clientOrderHeaderVO.getAttachmentArray(), null) %>
				</ol>
			</div>
		</div> 
		<div id="_tabContent11" class="kantab" style="display:none">
			<ol class="auto">
				<li>
					<label>税率</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getTaxes(), "taxId", "taxId", clientOrderHeaderVO.getTaxId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>开票方式</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getInvoiceTypes(), "invoiceType", "invoiceType", clientOrderHeaderVO.getInvoiceType(), null , null) %>
				</li>
				<li>
					<label>发票地址</label>
					<%= KANUtil.getSelectHTML(null, "invoiceAddressId", "invoiceAddressId", clientOrderHeaderVO.getInvoiceAddressId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>工资月份</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getMonthTypes(), "salaryMonth", "salaryMonth", clientOrderHeaderVO.getSalaryMonth(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>社保公积金月份</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getMonthTypes(), "sbMonth", "sbMonth", clientOrderHeaderVO.getSbMonth(), null , null) %>
				</li>
				<li>
					<label>商保月份</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getMonthTypes(), "cbMonth", "cbMonth", clientOrderHeaderVO.getCbMonth(), null , null) %>
				</li>
			</ol>
		</div>
	</div>
</div>

<input type="hidden" id="forwardURL" name="forwardURL" value="" />

<script type="text/javascript">
	(function($) {
		$(".tabMenu li").first().addClass('hover');
		$(".tabMenu li").first().addClass('first');
		$("#_tabContent .kantab").first().show();
	})(jQuery);
	
	// Tab切换
	function _changeTab(cursel, n){ 
		for(i = 1; i <= n; i++){ 
			$('#_tabMenu' + i).removeClass('hover');
			$('#_tabContent' + i).hide();
		} 
		
		$('#_tabMenu' + cursel).addClass('hover');
		$('#_tabContent' + cursel).show();
	};
</script>