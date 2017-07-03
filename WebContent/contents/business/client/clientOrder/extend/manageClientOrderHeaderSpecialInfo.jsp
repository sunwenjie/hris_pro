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
			<li id="tabMenu1" onClick="changeTab(1,11)" class="first hover">��Ա (<span id="numberOfEmployee"><bean:write name="numberOfEmployee" /></span>)</li>
			<kan:auth right="view" action="<%=ClientOrderHeaderRuleAction.accessAction %>">
				<li id="tabMenu2" onClick="changeTab(2,11)">�ܹ��� (<span id="numberOfClientOrderHeaderRule"><bean:write name="numberOfClientOrderHeaderRule" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderDetailAction.accessAction %>">
				<li id="tabMenu3" onClick="changeTab(3,11)">�շ� (<span id="numberOfClientOrderServiceFee"><bean:write name="numberOfClientOrderServiceFee" /></span>)</li> 
			</kan:auth>
			<li id="tabMenu4" onClick="changeTab(4,11)">н�� </li>
			<kan:auth right="view" action="<%=ClientOrderSBAction.accessAction %>">
				<li id="tabMenu5" onClick="changeTab(5,11)">�籣�����𷽰� (<span id="numberOfClientOrderSB"><bean:write name="numberOfClientOrderSB" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderCBAction.accessAction %>">
				<li id="tabMenu6" onClick="changeTab(6,11)">�̱����� (<span id="numberOfClientOrderCB"><bean:write name="numberOfClientOrderCB" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderLeaveAction.accessAction %>">
				<li id="tabMenu7" onClick="changeTab(7,11)">�ݼ����� (<span id="numberOfClientOrderLeave"><bean:write name="numberOfClientOrderLeave" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderOTAction.accessAction %>">
				<li id="tabMenu8" onClick="changeTab(8,11)">�Ӱ����� (<span id="numberOfClientOrderOT"><bean:write name="numberOfClientOrderOT" /></span>)</li> 
			</kan:auth>
			<kan:auth right="view" action="<%=ClientOrderOtherAction.accessAction %>">
				<li id="tabMenu9" onClick="changeTab(9,11)">�������� (<span id="numberOfClientOrderOther"><bean:write name="numberOfClientOrderOther" /></span>)</li> 
			</kan:auth>
			<li id="tabMenu10" onClick="changeTab(10,11)">���� (<span id="numberOfAttachment"><bean:write name="numberOfAttachment" /></span>)</li> 
			<li id="tabMenu11" onClick="changeTab(11,11)">����</li> 
		</ul> 
	</div> 
	<div class="tabContent">
		<div id="tabContent1" class="kantab">
			<form class="exportEmployeeContractForm" action="clientOrderHeaderAction.do?proc=export_service_contract" method="post">
				
			</form>	
			<div class="top" >
				<span>
					<kan:auth right="new" action="<%=EmployeeContractAction.getAccessAction(request,response) %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
						<a id="addContract" onclick="tabAdd('employeeContractAction.do?proc=to_objectNew&flag=2&from=order&orderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">���͹�Ա</a>
					</kan:auth>
					&nbsp;
					<kan:auth right="delete" action="<%=EmployeeContractAction.getAccessAction(request,response) %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
						<a onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')){$('#numberOfEmployee').html(eval(parseInt($('#numberOfEmployee').html()) - kanList_getSelectedLength())); submitForm('listServiceContract_form', 'deleteObjects', null, null, null, 'tableWrapperContractService');}" class="kanhandle">ɾ��</a>
					</kan:auth>
				</span>
           	    <a style="float: right;" id="exportExcel" name="exportExcel" class="commonTools" title="����Excel�ļ�" onclick="linkForm('exportEmployeeContractForm', null, null,'orderHeaderId=<%=clientOrderHeaderVO.getEncodedId() %>');" ><img src="images/appicons/excel_16.png" /></a>
			</div>
			<div id="tableWrapperContractService" class="tableWrapperContractService">
				<!--Service Contract Information Table--> 
				<jsp:include page="/contents/business/client/clientOrder/table/listServiceContractTable.jsp" flush="true"/>
			</div>
		</div>
		<kan:auth right="view" action="<%=ClientOrderHeaderRuleAction.accessAction %>">
			<div id="tabContent2" class="kantab" style="display:none" >
				<kan:auth right="new" action="<%=ClientOrderHeaderRuleAction.accessAction%>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderHeaderRuleAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">����ܹ���</a></span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="clientOrderHeaderRuleVOs">
						<logic:iterate id="clientOrderHeaderRuleVO" name="clientOrderHeaderRuleVOs" indexId="number">
							<li>
								<kan:auth right="delete" action="<%=ClientOrderHeaderRuleAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderHeaderRuleAction.do?proc=delete_object_ajax&clientOrderHeaderRuleId=<bean:write name="clientOrderHeaderRuleVO" property="encodedId" />', this, '#numberOfClientOrderHeaderRule');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderHeaderRuleAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderHeaderRuleAction.do?proc=to_objectModify&id=<bean:write name="clientOrderHeaderRuleVO" property="encodedId" />');">
								</kan:auth>	
								<bean:write name="clientOrderHeaderRuleVO" property="decodeRuleType" /> <logic:equal name="clientOrderHeaderRuleVO" property="ruleType" value="2">��</logic:equal><bean:write name="clientOrderHeaderRuleVO" property="ruleValue" /><logic:equal name="clientOrderHeaderRuleVO" property="ruleType" value="1">��</logic:equal> / <bean:write name="clientOrderHeaderRuleVO" property="ruleResult" />%
								<kan:auth  right="modify" action="<%=ClientOrderHeaderRuleAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">	
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
				<kan:auth  right="new" action="<%=ClientOrderDetailAction.accessAction%>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderDetailAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">����շ�</a></span>
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
								<kan:auth  right="modify" action="<%=ClientOrderDetailAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderDetailAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailVO" property="encodedId" />');">
								</kan:auth>
								<bean:write name="clientOrderDetailVO" property="decodeItemId" /> <logic:greaterThan name="clientOrderDetailVO" property="base" value="0">����<bean:write name="clientOrderDetailVO" property="base" /> / <bean:write name="clientOrderDetailVO" property="decodePackageType" />��</logic:greaterThan> <bean:write name="clientOrderDetailVO" property="startDate" /><logic:notEmpty name="clientOrderDetailVO" property="endDate"> ~ <bean:write name="clientOrderDetailVO" property="endDate" /></logic:notEmpty>
								<kan:auth  right="modify" action="<%=ClientOrderDetailAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
								<logic:equal name="clientOrderDetailVO" property="status" value="2"><span class="highlight">��ͣ�ã�</span></logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<div id="tabContent4" class="kantab" style="display:none">
			<ol class="auto">
				<li>
					<label>��н���ڣ�ÿ�£�</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getPayrollDays(), "payrollDay", "payrollDay", clientOrderHeaderVO.getPayrollDay(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>��н���ڣ���ʼ��</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCircleStartDays(), "circleStartDay", "circleStartDay", clientOrderHeaderVO.getCircleStartDay(), null , null) %>
				</li>
				<li>
					<label>��н���ڣ�������</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCircleEndDays(), "circleEndDay", "circleEndDay", clientOrderHeaderVO.getCircleEndDay(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>н�깩Ӧ��</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSalaryVendors(), "salaryVendorId", "salaryVendorId", clientOrderHeaderVO.getSalaryVendorId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>��˰����</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getIncomeTaxBases(), "incomeTaxBaseId", "incomeTaxBaseId", clientOrderHeaderVO.getIncomeTaxBaseId(), null , null) %>
				</li>
				<li>
					<label>��˰˰��</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getIncomeTaxRangeHeaders(), "incomeTaxRangeHeaderId", "incomeTaxRangeHeaderId", clientOrderHeaderVO.getIncomeTaxRangeHeaderId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li id="salaryTypeLI">
					<label>��н��ʽ</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSalaryTypes(), "salaryType", "salaryType", clientOrderHeaderVO.getSalaryType(), null , null) %>
				</li>
				<li id="divideTypeLI">
					<label>��ȫ�ڼ������</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getDivideTypes(), "divideType", "divideType", clientOrderHeaderVO.getDivideType(), null , null) %>
				</li>
				<li id="excludeDivideItemIdsLI">
					<label>���¼����Ƿ���<br/>Ϊ�����գ��粻<br/>�ǣ�ȥ����ѡ <img src="images/tips.png" title="��Էǻ������ʵ���ҵ�����й涨֧���������Ŀ���硰�òͲ�������ʵ�ʹ�����֧���������¼���ȫ��ȥ����ѡ�����������ʡ��������涨֧������Ŀ�������ܴ˹���Ӱ��" /></label>
					<div style="width: 215px;">
						<%
							final String subAction = ((ClientOrderHeaderVO)request.getAttribute("clientOrderHeaderForm")).getSubAction();
							String excludeDivideItemIds = "";
							if(BaseAction.VIEW_OBJECT.equalsIgnoreCase(subAction)){
							   // ����� �鿴��
							   excludeDivideItemIds = clientOrderHeaderVO.getExcludeDivideItemIds();
							}else{
							   // ��ѡȫ��
							   excludeDivideItemIds = KANUtil.formatMappingVOIds2StringJsonArray(clientOrderHeaderVO.getExcludeDivideItems());
							}
						%>
						<%=KANUtil.getCheckBoxHTML(clientOrderHeaderVO.getExcludeDivideItems(), "excludeDivideItemIds", excludeDivideItemIds, subAction, "<br/>") %>
					</div>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>����������ʽ</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getAttendanceCheckTypes(), "attendanceCheckType", "attendanceCheckType", clientOrderHeaderVO.getAttendanceCheckType(), null , null) %>
				</li>
				<li>
					<label>��������ʱ��</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getAttendanceGenerates(), "attendanceGenerate", "attendanceGenerate", clientOrderHeaderVO.getAttendanceGenerate(), null , null) %>
				</li>
				<li>
					<label>��˷�ʽ</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getApproveTypes(), "approveType", "approveType", clientOrderHeaderVO.getApproveType(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>�������ڼ�</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getProbationMonths(), "probationMonth", "probationMonth", clientOrderHeaderVO.getProbationMonth(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>��������</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getCalendarIds(), "calendarId", "calendarId", clientOrderHeaderVO.getCalendarId(), null , null) %>
				</li>
				<li>
					<label>�Ű�</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getShiftIds(), "shiftId", "shiftId", clientOrderHeaderVO.getShiftId(), null , null) %>
				</li>
				<li>
					<label>���ٹ���</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getSickLeaveSalaryIds(), "sickLeaveSalaryId", "sickLeaveSalaryId", clientOrderHeaderVO.getSickLeaveSalaryId(), null , null) %>
				</li>
			</ol>
		</div>
		<kan:auth right="view" action="<%=ClientOrderSBAction.accessAction %>">
			<div id="tabContent5" class="kantab" style="display:none" >
				<kan:auth  right="new" action="<%=ClientOrderSBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderSBAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">����籣�����𷽰�</a></span>
				</kan:auth>
				<ol class="auto">
					<li>
						<label>��˾�е������籣������</label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getFlags(), "personalSBBurden", "personalSBBurden", clientOrderHeaderVO.getPersonalSBBurden(), null , null) %>
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="clientOrderSBVOs">
						<logic:iterate id="clientOrderSBVO" name="clientOrderSBVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderSBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderSBAction.do?proc=delete_object_ajax&clientOrderSBId=<bean:write name="clientOrderSBVO" property="encodedId" />', this, '#numberOfClientOrderSB');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderSBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderSBAction.do?proc=to_objectModify&id=<bean:write name="clientOrderSBVO" property="encodedId" />');">
								</kan:auth>
								<bean:write name="clientOrderSBVO" property="sbSolutionName" />
								<kan:auth  right="modify" action="<%=ClientOrderSBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
									<input type="button" class="function" id="btnChangeBase" value="Ӧ����ͻ���" onclick="changeSbBase('<bean:write name="clientOrderSBVO" property="orderHeaderId" />','<bean:write name="clientOrderSBVO" property="sbSolutionId" />')">
								</kan:auth>
								<logic:equal name="clientOrderSBVO" property="status" value="2"><span class="highlight">��ͣ�ã�</span></logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderCBAction.accessAction %>">
			<div id="tabContent6" class="kantab" style="display:none" >
				<kan:auth  right="new" action="<%=ClientOrderCBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderCBAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">����̱�����</a></span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="clientOrderCBVOs">
						<logic:iterate id="clientOrderCBVO" name="clientOrderCBVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderCBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderCBAction.do?proc=delete_object_ajax&clientOrderCBId=<bean:write name="clientOrderCBVO" property="encodedId" />', this, '#numberOfClientOrderCB');" src="images/warning-btn.png">
								</kan:auth>	
								&nbsp;&nbsp;
								<kan:auth  right="modify" action="<%=ClientOrderCBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderCBAction.do?proc=to_objectModify&id=<bean:write name="clientOrderCBVO" property="encodedId" />');">
								</kan:auth>
								<bean:write name="clientOrderCBVO" property="cbSolutionName" />
								<kan:auth  right="modify" action="<%=ClientOrderCBAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
								<logic:equal name="clientOrderCBVO" property="status" value="2"><span class="highlight">��ͣ�ã�</span></logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderLeaveAction.accessAction %>">
			<div id="tabContent7" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=ClientOrderLeaveAction.accessAction%>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderLeaveAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">����ݼ�����</a></span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="clientOrderLeaveVOs">
						<logic:iterate id="clientOrderLeaveVO" name="clientOrderLeaveVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderLeaveAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderLeaveAction.do?proc=delete_object_ajax&clientOrderLeaveId=<bean:write name="clientOrderLeaveVO" property="encodedId" />', this, '#numberOfClientOrderLeave');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp; 
								<kan:auth  right="modify" action="<%=ClientOrderLeaveAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderLeaveAction.do?proc=to_objectModify&id=<bean:write name="clientOrderLeaveVO" property="encodedId" />');">
								</kan:auth>
								<bean:write name="clientOrderLeaveVO" property="decodeItemId" /> ��<bean:write name="clientOrderLeaveVO" property="benefitQuantity" />Сʱ<logic:equal name="clientOrderLeaveVO" property="itemId" value="41">������<bean:write name="clientOrderLeaveVO" property="legalQuantity" />Сʱ</logic:equal>��
								<kan:auth  right="modify" action="<%=ClientOrderLeaveAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>
								<logic:equal name="clientOrderLeaveVO" property="status" value="2"><span class="highlight">��ͣ�ã�</span></logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderOTAction.accessAction %>">
			<div id="tabContent8" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=ClientOrderOTAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderOTAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">��ӼӰ�����</a></span>
				</kan:auth>
				<ol class="auto">
					<li>
						<label>�Ӱ���Ҫ����</label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getFlags(), "applyOTFirst", "applyOTFirst", clientOrderHeaderVO.getApplyOTFirst(), null , null) %>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>ÿ��Ӱ����ޣ�Сʱ��</label>
						<input type="text" id="otLimitByDay" name="otLimitByDay" maxlength="2" class="otLimitByDay" style="" value="<bean:write name="clientOrderHeaderForm" property="otLimitByDay"/>" />
					</li>
					<li>
						<label>ÿ�¼Ӱ����ޣ�Сʱ��</label>
						<input type="text" id="otLimitByMonth" name="otLimitByMonth" maxlength="3" class="otLimitByMonth" style="" value="<bean:write name="clientOrderHeaderForm" property="otLimitByMonth"/>" />
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label>�����ռӰ��Ŀ</label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "workdayOTItemId", "workdayOTItemId", clientOrderHeaderVO.getWorkdayOTItemId(), null , null) %>
					</li>
					<li>
						<label>��Ϣ�ռӰ��Ŀ</label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "weekendOTItemId", "weekendOTItemId", clientOrderHeaderVO.getWeekendOTItemId(), null , null) %>
					</li>
					<li>
						<label>�ڼ��ռӰ��Ŀ</label>
						<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getOtItems(), "holidayOTItemId", "holidayOTItemId", clientOrderHeaderVO.getHolidayOTItemId(), null , null) %>
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="clientOrderOTVOs">
						<logic:iterate id="clientOrderOTVO" name="clientOrderOTVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderOTAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderOTAction.do?proc=delete_object_ajax&clientOrderOTId=<bean:write name="clientOrderOTVO" property="encodedId" />', this, '#numberOfClientOrderOT');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp;
								<kan:auth  right="modify" action="<%=ClientOrderOTAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<a href="#" onclick="link('clientOrderOTAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOTVO" property="encodedId" />');">
								</kan:auth>	
								<bean:write name="clientOrderOTVO" property="decodeItemId" />
								��
									<logic:greaterThan name="clientOrderOTVO" property="baseFrom" value="0">
										<bean:write name="clientOrderOTVO" property="decodeBaseFrom" />
										<logic:greaterThan name="clientOrderOTVO" property="percentage" value="0">
											&nbsp;*&nbsp;<bean:write name="clientOrderOTVO" property="percentage" />%
										</logic:greaterThan>
										<logic:greaterThan name="clientOrderOTVO" property="fix" value="0">
											&nbsp;+&nbsp;��<bean:write name="clientOrderOTVO" property="fix" />
										</logic:greaterThan>
									</logic:greaterThan>
									<logic:lessThan name="clientOrderOTVO" property="baseFrom" value="1">
										<logic:greaterThan name="clientOrderOTVO" property="base" value="0">
											��<bean:write name="clientOrderOTVO" property="base" />
										</logic:greaterThan> 
									</logic:lessThan>
									<logic:greaterThan name="clientOrderOTVO" property="discount" value="0">
										&nbsp;*&nbsp;<bean:write name="clientOrderOTVO" property="discount" />%
									</logic:greaterThan>
									<logic:greaterThan name="clientOrderOTVO" property="multiple" value="0">
										&nbsp;*&nbsp;<bean:write name="clientOrderOTVO" property="decodeMultiple" />
									</logic:greaterThan>
								��
								<bean:write name="clientOrderOTVO" property="startDate" />
								<logic:notEmpty name="clientOrderOTVO" property="endDate"> ~ <bean:write name="clientOrderOTVO" property="endDate" /></logic:notEmpty>
								<kan:auth right="modify" action="<%=ClientOrderOTAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									</a>
								</kan:auth>	
								<logic:equal name="clientOrderOTVO" property="status" value="2"><span class="highlight">��ͣ�ã�</span></logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth right="view" action="<%=ClientOrderOtherAction.accessAction%>">
			<div id="tabContent9" class="kantab" style="display:none">
				<kan:auth  right="new" action="<%=ClientOrderOtherAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
					<span><a onclick="tabAdd('clientOrderOtherAction.do?proc=to_objectNew&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', '<bean:write name="clientOrderHeaderForm" property="encodedId"/>');" class="kanhandle">�����������</a></span>
				</kan:auth>
				<ol class="auto">
					<logic:notEmpty name="clientOrderOtherVOs">
						<logic:iterate id="clientOrderOtherVO" name="clientOrderOtherVOs" indexId="number">
							<li>
								<kan:auth  right="delete" action="<%=ClientOrderOtherAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('clientOrderOtherAction.do?proc=delete_object_ajax&clientOrderOtherId=<bean:write name="clientOrderOtherVO" property="encodedId" />', this, '#numberOfClientOrderOther');" src="images/warning-btn.png">
								</kan:auth>
								&nbsp;&nbsp;
								<kan:auth  right="modify" action="<%=ClientOrderOtherAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">	
									<a href="#" onclick="link('clientOrderOtherAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOtherVO" property="encodedId" />');">
								</kan:auth>	
								<bean:write name="clientOrderOtherVO" property="decodeItemId" /> 
								��
									<logic:greaterThan name="clientOrderOtherVO" property="baseFrom" value="0">
										<bean:write name="clientOrderOtherVO" property="decodeBaseFrom" />
										<logic:greaterThan name="clientOrderOtherVO" property="percentage" value="0">
											&nbsp;*&nbsp;<bean:write name="clientOrderOtherVO" property="percentage" />%
										</logic:greaterThan>
										<logic:greaterThan name="clientOrderOtherVO" property="fix" value="0">
											&nbsp;+&nbsp;��<bean:write name="clientOrderOtherVO" property="fix" />
										</logic:greaterThan>
									</logic:greaterThan>
									<logic:lessThan name="clientOrderOtherVO" property="baseFrom" value="1">
										<logic:greaterThan name="clientOrderOtherVO" property="base" value="0">
											��<bean:write name="clientOrderOtherVO" property="base" />
										</logic:greaterThan> 
									</logic:lessThan>
								 / 
									<logic:greaterThan name="clientOrderOtherVO" property="cycle" value="0">
										<logic:notEqual name="clientOrderOtherVO" property="cycle" value="13">ÿ</logic:notEqual>
										<bean:write name="clientOrderOtherVO" property="decodeCycle" />
									</logic:greaterThan>
								��
								<bean:write name="clientOrderOtherVO" property="startDate" />
								<logic:notEmpty name="clientOrderOtherVO" property="endDate"> ~ <bean:write name="clientOrderOtherVO" property="endDate" /></logic:notEmpty>
								<kan:auth  right="modify" action="<%=ClientOrderOtherAction.accessAction  %>" owner="<%=clientOrderHeaderVO.getOwner() %>">	
									</a> 
								</kan:auth>
								<logic:equal name="clientOrderOtherVO" property="status" value="2"><span class="highlight">��ͣ�ã�</span></logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<div id="tabContent10" class="kantab" style="display:none">
			<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle" onclick="uploadObject.submit();">�ϴ�����</a></span>	
			<div id="attachmentsDiv">
				<ol id="attachmentsOL" class="auto">
					<%= AttachmentRender.getAttachments(request, clientOrderHeaderVO.getAttachmentArray(), null) %>
				</ol>
			</div>
		</div> 
		<div id="tabContent11" class="kantab" style="display:none">
			<ol class="auto">
				<li>
					<label>˰��</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getTaxes(), "taxId", "taxId", clientOrderHeaderVO.getTaxId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>��Ʊ��ʽ</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getInvoiceTypes(), "invoiceType", "invoiceType", clientOrderHeaderVO.getInvoiceType(), null , null) %>
				</li>
				<li>
					<label>��Ʊ��ַ</label>
					<%= KANUtil.getSelectHTML(null, "invoiceAddressId", "invoiceAddressId", clientOrderHeaderVO.getInvoiceAddressId(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>�����·�</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getMonthTypes(), "salaryMonth", "salaryMonth", clientOrderHeaderVO.getSalaryMonth(), null , null) %>
				</li>
			</ol>
			<ol class="auto">
				<li>
					<label>�籣�������·�</label>
					<%= KANUtil.getSelectHTML(clientOrderHeaderVO.getMonthTypes(), "sbMonth", "sbMonth", clientOrderHeaderVO.getSbMonth(), null , null) %>
				</li>
				<li>
					<label>�̱��·�</label>
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

		// ���񶩵�״̬Ϊ��ȡ������ô�������������Ϣ
		<%
			if(clientOrderHeaderVO.getStatus() != null && (clientOrderHeaderVO.getStatus().trim().equals("8") )){
		%>
			disableLinkById('#addContract');
			disableLinkById('#addOrder');
		<%	   
			}
		%>	
		
		// ҳ���ʼ�� - �鿴
		if(isView()){
			loadContractOptions('<bean:write name="clientOrderHeaderForm" property="contractId"/>');
			loadInvoiceAddressOptions('<bean:write name="clientOrderHeaderForm" property="invoiceAddressId"/>');
			
			disableLink('manage_primary_form');
		}
		// ҳ���ʼ�� - �½���ͨ����
		else{
			// ����ClientId�����
			if( $('#clientId').val() != '' ){
				$('#clientId').trigger('keyup');
			}
			
			// �ͻ�ID������ر���
			$('#clientId').addClass('important');
			
			// ���ÿͻ��������ƺ�Ӣ�����Ʋ��ܱ༭
			$('#clientNameZH').attr('disabled', 'disabled');
			$('#clientNameEN').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
		}
		
		var uploadObject = createUploadObject('uploadAttachment', 'common', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_CLIENT_ORDER %>/<%= BaseAction.getAccountId(request, response) %>/<%= BaseAction.getUsername(request, response) %>/');
		
		// ����н��ʽ�����Change�¼�
	 	$('#salaryType').change(function(){
		 	if($(this).val() == 1){
			 	$("#divideTypeLI").show();
			 	$("#excludeDivideItemIdsLI").show();
		 	}else{
			 	$("#divideTypeLI").hide();
			 	$("#excludeDivideItemIdsLI").hide();
		 	}
	 	});
		
	 	$('#divideType').change(function(){
		 	if(($(this).val() == 2 || $(this).val() == 3)&& $('#salaryType').val()==1){
			 	$("#excludeDivideItemIdsLI").show();
		 	}else{
			 	$("#excludeDivideItemIdsLI").hide();
		 	}
	 	});
		
		$('#salaryType').change();
		$('#divideType').change();
	})(jQuery);
	
	// Tab���������¼��������ǰ��Modify Object���ȱ���Ȼ�������
	function tabAdd(targetURL, primaryKey){
		if($('.subAction').val()=='modifyObject'){
			// ���ύ
			$("#forwardURL").val(targetURL);
			enableForm('manage_primary_form');
			$(".manage_primary_form").submit();
		}else{
			addExtraObject(targetURL,primaryKey);
		} 
	};
	
	function changeSbBase(orderId,sbSolutionId){
		if(confirm('ȷ��Ҫִ�д˲�����')){
			var targetURL = 'clientOrderHeaderAction.do?proc=updateEmployeeSBBaseBySolution&orderId='+orderId+'&sbSolutionId='+sbSolutionId;
			$.ajax({url: targetURL + "&date=" + new Date(),
				dataType : 'json',
				success: function(data, status){
					if(data.status == 'success'){
						$('#messageWrapper').html('<div class="message success fadable">�����ɹ���<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
						messageWrapperFada();
					}
				}
			});
		}
	};
</script>