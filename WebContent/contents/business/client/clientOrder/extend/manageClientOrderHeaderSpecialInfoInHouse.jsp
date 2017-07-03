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
<%@ taglib uri="http://kangroup/authTag" prefix="kan" %>

<% 
	final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) request.getAttribute("clientOrderHeaderForm"); 
%>

<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<kan:auth right="view" action="<%=EmployeeContractAction.getAccessAction(request,response) %>">
				<li id="tabMenu1" onClick="changeTab(1,11)" class="first"><bean:message bundle="public" key="menu.table.title.employee" /> (<span id="numberOfEmployee"><bean:write name="numberOfEmployee" /></span>)</li> 
			</kan:auth> 
			<kan:auth right="view" action="<%=ClientOrderHeaderRuleAction.accessAction %>">	
				<li id="tabMenu2" style="display:none;" onClick="changeTab(2,11)">总规则 (<span id="numberOfClientOrderHeaderRule"><bean:write name="numberOfClientOrderHeaderRule" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderHeaderRuleAction.accessAction %>">	
				<li id="tabMenu3" style="display:none;" onClick="changeTab(3,11)">收费 (<span id="numberOfClientOrderServiceFee"><bean:write name="numberOfClientOrderServiceFee" /></span>)</li> 
			</kan:auth>
				<li id="tabMenu4" onClick="changeTab(4,11)" class="hover"><bean:message bundle="public" key="menu.table.title.salary" /></li> 
			<kan:auth right="view" action="<%=ClientOrderSBAction.accessAction_in_house %>">	
				<li id="tabMenu5" onClick="changeTab(5,11)"><bean:message bundle="public" key="menu.table.title.sb" />(<span id="numberOfClientOrderSB"><bean:write name="numberOfClientOrderSB" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderCBAction.accessAction_in_house %>">		
				<li id="tabMenu6" onClick="changeTab(6,11)"><bean:message bundle="public" key="menu.table.title.cb" /> (<span id="numberOfClientOrderCB"><bean:write name="numberOfClientOrderCB" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderLeaveAction.accessAction_in_house %>">		
				<li id="tabMenu7" onClick="changeTab(7,11)"><bean:message bundle="public" key="menu.table.title.leave" />(<span id="numberOfClientOrderLeave"><bean:write name="numberOfClientOrderLeave" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderOTAction.accessAction_in_house %>">		
				<li id="tabMenu8" onClick="changeTab(8,11)"><bean:message bundle="public" key="menu.table.title.ot" /> (<span id="numberOfClientOrderOT"><bean:write name="numberOfClientOrderOT" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderOtherAction.accessAction_in_house %>">		
				<li id="tabMenu9" onClick="changeTab(9,11)"><bean:message bundle="public" key="menu.table.title.other" /> (<span id="numberOfClientOrderOther"><bean:write name="numberOfClientOrderOther" /></span>)</li> 
			</kan:auth>		
			<li id="tabMenu10" onClick="changeTab(10,11)"><bean:message bundle="public" key="menu.table.title.attachment" /> (<span id="numberOfAttachment"><bean:write name="numberOfAttachment" /></span>)</li> 
			<li id="tabMenu11" style="display:none;" class="hide" onClick="changeTab(11,11)">结算</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<kan:auth right="view" action="<%=EmployeeContractAction.getAccessAction(request,response) %>">
			<div id="tabContent1" class="kantab">
				<form class="exportEmployeeContractForm" action="clientOrderHeaderAction.do?proc=export_service_contract" method="post">
					
				</form>	
				<div class="top">
					<span>
						<kan:auth right="new" action="<%=EmployeeContractAction.getAccessAction(request,response) %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
							<a id="addContract" onclick="tabAdd('employeeContractAction.do?proc=to_objectNew&flag=2&from=order&orderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.labor.contract" /></a>
						</kan:auth>
						&nbsp; 
						<kan:auth  right="delete" action="<%=EmployeeContractAction.getAccessAction(request,response) %>" owner="<%=clientOrderHeaderVO.getOwner() %>">	
							<a onclick="if(kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')){$('#numberOfEmployee').html(eval(parseInt($('#numberOfEmployee').html()) - kanList_getSelectedLength())); submitForm('listServiceContract_form', 'deleteObjects', null, null, null, 'tableWrapperContractService');}" class="kanhandle"><bean:message bundle="public" key="button.delete" /></a>
						</kan:auth>
					</span>
           			<a style="float: right;" id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm('exportEmployeeContractForm', null, null,'orderHeaderId=<%=clientOrderHeaderVO.getEncodedId() %>');" ><img src="images/appicons/excel_16.png" /></a>
				</div>
				<div id="tableWrapperContractService" class="tableWrapperContractService">
					<!--Service Contract Information Table--> 
					<jsp:include page="/contents/business/client/clientOrder/table/listServiceContractTable.jsp" flush="true"/>
				</div>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderHeaderRuleAction.accessAction %>">
			<div id="tabContent2" class="kantab" style="display:none" >
				<kan:auth right="new" action="<%=ClientOrderHeaderRuleAction.accessAction%>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderHeaderRuleAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">添加总规则</a></span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="clientOrderHeaderRuleVOs">
						<logic:iterate id="clientOrderHeaderRuleVO" name="clientOrderHeaderRuleVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderHeaderRuleAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderHeaderRuleAction.do?proc=delete_object_ajax&clientOrderHeaderRuleId=<bean:write name="clientOrderHeaderRuleVO" property="encodedId" />', this, '#numberOfClientOrderHeaderRule');" src="images/warning-btn.png">
								</kan:auth>	
								&nbsp;&nbsp; 
								<kan:auth  right="Modify" action="<%=ClientOrderHeaderRuleAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderHeaderRuleAction.do?proc=to_objectModify&id=<bean:write name="clientOrderHeaderRuleVO" property="encodedId" />');">
								</kan:auth>
									<bean:write name="clientOrderHeaderRuleVO" property="decodeRuleType" /> <logic:equal name="clientOrderHeaderRuleVO" property="ruleType" value="2">￥</logic:equal><bean:write name="clientOrderHeaderRuleVO" property="ruleValue" /><logic:equal name="clientOrderHeaderRuleVO" property="ruleType" value="1">人</logic:equal> / <bean:write name="clientOrderHeaderRuleVO" property="ruleResult" />%
								<kan:auth  right="Modify" action="<%=ClientOrderHeaderRuleAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>	
		<kan:auth right="view" action="<%=ClientOrderDetailAction.accessAction %>">
			<div id="tabContent3" class="kantab" style="display:none" >
				<kan:auth  right="new" action="<%=ClientOrderDetailAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderDetailAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">添加收费</a></span>
				</kan:auth>	
				<ol class="auto">
					<logic:notEmpty name="clientOrderDetailVOs">
						<logic:iterate id="clientOrderDetailVO" name="clientOrderDetailVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderDetailAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderDetailAction.do?proc=delete_object_ajax&clientOrderDetailId=<bean:write name="clientOrderDetailVO" property="encodedId" />', this, '#numberOfClientOrderServiceFee');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="Modify" action="<%=ClientOrderDetailAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderDetailAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailVO" property="encodedId" />');">
								</kan:auth>
									<bean:write name="clientOrderDetailVO" property="decodeItemId" /> <logic:greaterThan name="clientOrderDetailVO" property="base" value="0">（￥<bean:write name="clientOrderDetailVO" property="base" /> / <bean:write name="clientOrderDetailVO" property="decodePackageType" />）</logic:greaterThan> <bean:write name="clientOrderDetailVO" property="startDate" /><logic:notEmpty name="clientOrderDetailVO" property="endDate"> ~ <bean:write name="clientOrderDetailVO" property="endDate" /></logic:notEmpty>
								<kan:auth  right="Modify" action="<%=ClientOrderDetailAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
								<logic:equal name="clientOrderDetailVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<div id="tabContent4" class="kantab">
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.client.order.payroll.day" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getPayrollDays(), "payrollDay", "payrollDay", clientOrderHeaderVO.getPayrollDay(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.client.order.circle.start.day" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCircleStartDays(), "circleStartDay", "circleStartDay", clientOrderHeaderVO.getCircleStartDay(), null , null) %>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.client.order.circle.end.day" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCircleEndDays(), "circleEndDay", "circleEndDay", clientOrderHeaderVO.getCircleEndDay(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.client.order.salary.vendor" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSalaryVendors(), "salaryVendorId", "salaryVendorId", clientOrderHeaderVO.getSalaryVendorId(), null , null) %>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.employee.contract.salary.currency" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCurrencys(), "currency", "currency", clientOrderHeaderVO.getCurrency(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.client.order.income.tax.base" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getIncomeTaxBases(), "incomeTaxBaseId", "incomeTaxBaseId", clientOrderHeaderVO.getIncomeTaxBaseId(), null , null) %>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.client.order.income.tax.range" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getIncomeTaxRangeHeaders(), "incomeTaxRangeHeaderId", "incomeTaxRangeHeaderId", clientOrderHeaderVO.getIncomeTaxRangeHeaderId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.client.order.salary.type" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSalaryTypes(), "salaryType", "salaryType", clientOrderHeaderVO.getSalaryType(), null , null) %>
				</li>
				<li id="divideTypeLI">
					<label><bean:message bundle="business" key="business.client.order.divide.type" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getDivideTypes(), "divideType", "divideType", clientOrderHeaderVO.getDivideType(), null , null) %>
				</li>
				<li id="excludeDivideItemIdsLI">
					<label>
						<%
							if( request.getLocale().getLanguage().equalsIgnoreCase( "ZH") )
							{
							   out.print( "以下假期是否视<br/>为工作日，如不<br/>是，去除勾选" );
							   out.print( "<img src=\"images/tips.png\" title=\"针对非基本工资等企业可自行规定支付规则的项目，如“用餐补贴”按实际工作日支付，则以下假期全部去除勾选，“基本工资”按政府规定支付的项目，并不受此规则影响。\" />" );
							}
							else
							{
							   out.print( "If any deduction for holidays, if not, please do not click it." );
							   out.print( "<img src=\"images/tips.png\" title=\"In view of the non basic salary and other enterprises can be specified payment rules project, such as 'meal subsidies' according to the actual working days paid vacation, the following completely removing the tick, 'basic wage' provided by the government for projects, are not affected by this rule\" />" );
							}
						%>
					</label>
					<div style="width: 215px;">
						<%=KANUtil.getCheckBoxHTML(clientOrderHeaderVO.getExcludeDivideItems(), "excludeDivideItemIds", clientOrderHeaderVO.getExcludeDivideItemIds(),((ClientOrderHeaderVO)request.getAttribute("clientOrderHeaderForm")).getSubAction(), "<br/>") %>
					</div>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.client.order.attendance.check.type" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getAttendanceCheckTypes(), "attendanceCheckType", "attendanceCheckType", clientOrderHeaderVO.getAttendanceCheckType(), null , null) %>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.client.order.attendance.generate.type" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getAttendanceGenerates(), "attendanceGenerate", "attendanceGenerate", clientOrderHeaderVO.getAttendanceGenerate(), null , null) %>
				</li>
				<li class="hide">
					<label>审核方式</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getApproveTypes(), "approveType", "approveType", clientOrderHeaderVO.getApproveType(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.employee.contract.contract.period" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getContractPeriods(), "contractPeriod", "contractPeriod", clientOrderHeaderVO.getContractPeriod(), null , null) %>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.client.order.probation.month" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getProbationMonths(), "probationMonth", "probationMonth", clientOrderHeaderVO.getProbationMonth(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label><bean:message bundle="business" key="business.client.order.work.calendar" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCalendarIds(), "calendarId", "calendarId", clientOrderHeaderVO.getCalendarId(), null , null) %>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.client.order.shift" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getShiftIds(), "shiftId", "shiftId", clientOrderHeaderVO.getShiftId(), null , null) %>
				</li>
				<li>
					<label><bean:message bundle="business" key="business.client.order.sick.leave.salary" /><img src="images/tips.png" title="<bean:message bundle="business" key="business.client.order.sick.leave.salary.tips" />" /></label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSickLeaveSalaryIds(), "sickLeaveSalaryId", "sickLeaveSalaryId", clientOrderHeaderVO.getSickLeaveSalaryId(), null , null) %>
				</li>
			</ol>
		</div>
		<kan:auth right="view" action="<%=ClientOrderSBAction.accessAction_in_house %>">
			<div id="tabContent5" class="kantab" style="display:none" >
				<kan:auth right="new" action="<%=ClientOrderSBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderSBAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.sb" /></a></span>
				</kan:auth>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.client.order.personal.sb.burden" /></label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getFlags(), "personalSBBurden", "personalSBBurden", clientOrderHeaderVO.getPersonalSBBurden(), null , null) %>
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="clientOrderSBVOs">
						<logic:iterate id="clientOrderSBVO" name="clientOrderSBVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderSBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderSBAction.do?proc=delete_object_ajax&clientOrderSBId=<bean:write name="clientOrderSBVO" property="encodedId" />', this, '#numberOfClientOrderSB');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderSBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderSBAction.do?proc=to_objectModify&id=<bean:write name="clientOrderSBVO" property="encodedId" />');">
								</kan:auth>
									<bean:write name="clientOrderSBVO" property="sbSolutionName" />
								<kan:auth  right="modify" action="<%=ClientOrderSBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
									&nbsp;
									&nbsp;
									<input type="button" class="function" id="btnChangeBase" value="<bean:message bundle="business" key="business.client.order.button.low.base" />" onclick="changeSbBase('<bean:write name="clientOrderSBVO" property="orderHeaderId" />','<bean:write name="clientOrderSBVO" property="sbSolutionId" />')">
								</kan:auth>
								<logic:equal name="clientOrderSBVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderCBAction.accessAction_in_house %>">
			<div id="tabContent6" class="kantab" style="display:none" >
				<kan:auth  right="new" action="<%=ClientOrderCBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderCBAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.cb" /></a></span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="clientOrderCBVOs">
						<logic:iterate id="clientOrderCBVO" name="clientOrderCBVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderCBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderCBAction.do?proc=delete_object_ajax&clientOrderCBId=<bean:write name="clientOrderCBVO" property="encodedId" />', this, '#numberOfClientOrderCB');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderCBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderCBAction.do?proc=to_objectModify&id=<bean:write name="clientOrderCBVO" property="encodedId" />');">
								</kan:auth>
								<bean:write name="clientOrderCBVO" property="cbSolutionName" />
								<kan:auth  right="modify" action="<%=ClientOrderCBAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
								<logic:equal name="clientOrderCBVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderLeaveAction.accessAction_in_house %>">
			<div id="tabContent7" class="kantab" style="display:none">
				<kan:auth right="new" action="<%=ClientOrderLeaveAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderLeaveAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.leave" /></a></span>
				</kan:auth>
				<input type="button" class="function" id="btnCalculateAnnualLeave" name="btnCalculateAnnualLeave" value='<bean:message bundle="public" key="button.calculation.annual.leave" />' />
				<ol class="auto">
					<logic:notEmpty name="clientOrderLeaveVOs">
						<logic:iterate id="clientOrderLeaveVO" name="clientOrderLeaveVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderLeaveAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderLeaveAction.do?proc=delete_object_ajax&clientOrderLeaveId=<bean:write name="clientOrderLeaveVO" property="encodedId" />', this, '#numberOfClientOrderLeave');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderLeaveAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderLeaveAction.do?proc=to_objectModify&id=<bean:write name="clientOrderLeaveVO" property="encodedId" />');">
								</kan:auth>
									<bean:write name="clientOrderLeaveVO" property="remark" />
								<kan:auth  right="modify" action="<%=ClientOrderLeaveAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
								<logic:equal name="clientOrderLeaveVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderOTAction.accessAction_in_house %>">
			<div id="tabContent8" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=ClientOrderOTAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderOTAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.ot" /></a></span>
				</kan:auth>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.client.order.ot.apply.first" /></label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getFlags(), "applyOTFirst", "applyOTFirst", clientOrderHeaderVO.getApplyOTFirst(), null , null) %>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.client.order.ot.limit.day" /></label>
						<input type="text" id="otLimitByDay" name="otLimitByDay" maxlength="2" class="otLimitByDay" style="" value="<bean:write name="clientOrderHeaderForm" property="otLimitByDay"/>" />
					</li>
					<li>
						<label><bean:message bundle="business" key="business.client.order.ot.limit.month" /></label>
						<input type="text" id="otLimitByMonth" name="otLimitByMonth" maxlength="3" class="otLimitByMonth" style="" value="<bean:write name="clientOrderHeaderForm" property="otLimitByMonth"/>" />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.client.order.ot.work.day.rule" /><img src="images/tips.png" title="<bean:message bundle="business" key="business.client.order.ot.rule.tips" />" /></label> 
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "workdayOTItemId", "workdayOTItemId", clientOrderHeaderVO.getWorkdayOTItemId(), null , null) %>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.client.order.ot.week.end.rule" /><img src="images/tips.png" title="<bean:message bundle="business" key="business.client.order.ot.rule.tips" />" /></label> 
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "weekendOTItemId", "weekendOTItemId", clientOrderHeaderVO.getWeekendOTItemId(), null , null) %>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.client.order.ot.holi.day.rule" /><img src="images/tips.png" title="<bean:message bundle="business" key="business.client.order.ot.rule.tips" />" /></label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "holidayOTItemId", "holidayOTItemId", clientOrderHeaderVO.getHolidayOTItemId(), null , null) %>
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="clientOrderOTVOs">
						<logic:iterate id="clientOrderOTVO" name="clientOrderOTVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderOTAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderOTAction.do?proc=delete_object_ajax&clientOrderOTId=<bean:write name="clientOrderOTVO" property="encodedId" />', this, '#numberOfClientOrderOT');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderOTAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderOTAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOTVO" property="encodedId" />');">
								</kan:auth>
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
								<kan:auth  right="modify" action="<%=ClientOrderOTAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
								<logic:equal name="clientOrderOTVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderOtherAction.accessAction_in_house %>">
			<div id="tabContent9" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=ClientOrderOtherAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderOtherAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.other" /></a></span>
				</kan:auth>	
				<ol class="auto">
					<logic:notEmpty name="clientOrderOtherVOs">
						<logic:iterate id="clientOrderOtherVO" name="clientOrderOtherVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderOtherAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderOtherAction.do?proc=delete_object_ajax&clientOrderOtherId=<bean:write name="clientOrderOtherVO" property="encodedId" />', this, '#numberOfClientOrderOther');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderOtherAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderOtherAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOtherVO" property="encodedId" />');">
								</kan:auth>
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
								<kan:auth  right="modify" action="<%=ClientOrderOtherAction.accessAction_in_house  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a> 
								</kan:auth>
								<logic:equal name="clientOrderOtherVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<div id="tabContent10" class="kantab" style="display:none">
			<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle" onclick="uploadObject.submit();"><bean:message bundle="public" key="link.upload.attachment" /></a></span>	
			<div id="attachmentsDiv">
				<ol id="attachmentsOL" class="auto">
					<%= AttachmentRender.getAttachments(request, clientOrderHeaderVO.getAttachmentArray(), null) %>
				</ol>
			</div>
		</div> 
		<div id="tabContent11" class="kantab" style="display:none">
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
		$(".tabMenu li.hover").trigger('click');

		// 服务订单状态为“取消”那么不能添加派送信息
		<%
			if(clientOrderHeaderVO != null && KANUtil.filterEmpty(clientOrderHeaderVO.getStatus()) != null && KANUtil.filterEmpty(clientOrderHeaderVO.getStatus()).equals("8")){
		%>
				disableLinkById('#addContract');
				disableLinkById('#addOrder');
		<%	   
			}
		%>
		
		var uploadObject = createUploadObject('uploadAttachment', 'common', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT_ORDER %>/<%= BaseAction.getAccountId(request, response) %>/<%= BaseAction.getUsername(request, response) %>/');
		
		// “计薪方式”添加Change事件
	 	$('#salaryType').change(function(){
		 	if($(this).val() == 1){
			 	$("#divideTypeLI").show();
		 	}else{
			 	$("#divideTypeLI").hide();
		 	}
	 	});
		
		// 计算年假点击事件
		$('#btnCalculateAnnualLeave').click( function() {
				if(confirm('确认应用到该结算规则下的员工？')){
					$('.manage_primary_form').attr('action', 'clientOrderHeaderAction.do?proc=calculate_annual_leave');
					submit('manage_primary_form');
				}
		});
		
		// “折算规则”添加Change事件
	 	$('#divideType').change(function(){
		 	if(($(this).val() == 2 || $(this).val() == 3|| $(this).val() == 5||$(this).val() == 4)&& $('#salaryType').val() == 1){
			 	$("#excludeDivideItemIdsLI").show();
		 	}else{
			 	$("#excludeDivideItemIdsLI").hide();
		 	}
	 	});
		
		$('#salaryType').change();
		$('#divideType').change();
		
		// 页面初始化 - 查看
		if($('.manage_primary_form input#subAction').val() == 'viewObject'){
			loadContractOptions('<bean:write name="clientOrderHeaderForm" property="contractId"/>');
			loadInvoiceAddressOptions('<bean:write name="clientOrderHeaderForm" property="invoiceAddressId"/>');
			disableLink('manage_primary_form');
		}
		// 页面初始化 - 新建或编辑
		else{
			// 客户ID添加着重背景
			$('#clientId').addClass('important');
			
			// 存在ClientId的情况
			if( $('#clientId').val() != '' ){
				$('#clientId').trigger('keyup');
			}
			
			// 设置客户中文名称和英文名称不能编辑
			$('#clientNameZH').attr('disabled', 'disabled');
			$('#clientNameEN').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
		}
	})(jQuery);
	
	// Tab下面的添加事件，如果当前是Modify Object则先保存然后再添加
	function tabAdd(targetURL, primaryKey){
		if(getSubAction()=='modifyObject'){
			// 先提交
			$("#forwardURL").val(targetURL);
			enableForm('manage_primary_form');
			$(".manage_primary_form").submit();
		}else{
			addExtraObject(targetURL,primaryKey);
		} 
	};
	
	function changeSbBase(orderId,sbSolutionId){
		if(confirm('<bean:message bundle="public" key="popup.confirm.operation" />')){
			var targetURL = 'clientOrderHeaderAction.do?proc=updateEmployeeSBBaseBySolution&orderId='+orderId+'&sbSolutionId='+sbSolutionId;
			$.ajax({url: targetURL + "&date=" + new Date(),
				dataType : 'json',
				success: function(data, status){
					if(data.status == 'success'){
						$('#messageWrapper').html('<div class="message success fadable">操作成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
					}
				}
			});
		}
	};
</script>