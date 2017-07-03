<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.domain.MappingVO"%>
<%@ page import="com.kan.base.web.renders.util.AttachmentRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractSalaryAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractSBAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractCBAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractLeaveAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractOTAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractOtherAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractSettlementAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeSalaryAdjustmentAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<% 
	final EmployeeContractVO employeeContractVO = (EmployeeContractVO) request.getAttribute("employeeContractForm"); 
	request.setAttribute("role", BaseAction.getRole(request, response)); 
%>

<div id="attachement" class="kantab">
	<span><a name="uploadAttachment" id="uploadAttachment" class="kanhandle" style="display: none;"><bean:message bundle="public" key="link.upload.attachment" /></a></span>	
	<div id="attachmentsDiv">
		<ol id="attachmentsOL" class="auto">
			<%= AttachmentRender.getAttachments( request, employeeContractVO.getAttachmentArray(), null) %>
		</ol>
	</div>
</div>
<div id="tab">
	<div class="tabMenu"> 
		<ul> 
			<kan:auth action="<%=EmployeeContractSalaryAction.accessAction%>" right="view">
				<li id="tabMenu1" onClick="changeTab(1,8)" class="hover first"><bean:message bundle="public" key="menu.table.title.salary" /> (<span id="numberOfContractSalary"><bean:write name="numberOfContractSalary"/></span>)</li>
			</kan:auth>
			<kan:auth action="<%=EmployeeContractSBAction.accessAction%>" right="view">
				<li id="tabMenu2" onClick="changeTab(2,8)" ><bean:message bundle="public" key="menu.table.title.sb" /> (<span id="numberOfContractSB"><bean:write name="numberOfContractSB"/></span>)</li> 
			</kan:auth>
				<kan:auth action="<%=EmployeeContractCBAction.accessAction%>" right="view">
			<li id="tabMenu3" onClick="changeTab(3,8)" ><bean:message bundle="public" key="menu.table.title.cb" /> (<span id="numberOfContractCB"><bean:write name="numberOfContractCB"/></span>)</li>
			</kan:auth>
			<kan:auth action="<%=EmployeeContractLeaveAction.accessAction%>" right="view">
				<li id="tabMenu4" onClick="changeTab(4,8)" ><bean:message bundle="public" key="menu.table.title.leave" /> (<span id="numberOfContractLeave"><bean:write name="numberOfContractLeave"/></span>)</li> 
			</kan:auth>
			<kan:auth action="<%=EmployeeContractOTAction.accessAction%>" right="view">
				<li id="tabMenu5" onClick="changeTab(5,8)" ><bean:message bundle="public" key="menu.table.title.ot" /> (<span id="numberOfContractOT"><bean:write name="numberOfContractOT"/></span>)</li> 
			</kan:auth>
			<kan:auth action="<%=EmployeeContractOtherAction.accessAction%>" right="view">
				<li id="tabMenu6" onClick="changeTab(6,8)" ><bean:message bundle="public" key="menu.table.title.other" /> (<span id="numberOfContractOther"><bean:write name="numberOfContractOther"/></span>)</li> 
			</kan:auth>
			<logic:notEqual name="role" value="5">
			<logic:notEqual value="4" name="role">
				<kan:auth action="<%=EmployeeContractSettlementAction.accessAction%>" right="view">
					<li id="tabMenu7" onClick="changeTab(7,8)" ><%=KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) ? KANUtil.getProperty( request.getLocale(), "menu.table.title.cost" ) : "营收" %> (<span id="numberOfContractSettlement"><bean:write name="numberOfContractSettlement"/></span>)</li> 
				</kan:auth>
			</logic:notEqual>
			</logic:notEqual>
			<kan:auth action="<%=EmployeeSalaryAdjustmentAction.accessAction%>" right="view">
				<li id="tabMenu8" onClick="changeTab(8,8)" ><bean:message bundle="public" key="menu.table.title.salary.adjustment" /></li> 
			</kan:auth>
		</ul> 
	</div>  
	<div id="tabContent" class="tabContent">
		<kan:auth action="<%=EmployeeContractSalaryAction.accessAction%>" right="view">
			<div id="tabContent1" class="kantab">
				<logic:notEqual name="role" value="5">
					<kan:auth right="new" action="<%=EmployeeContractSalaryAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
						<span><a id="addSalary" onclick="tabAdd('employeeContractSalaryAction.do?proc=to_objectNew&contractId=<bean:write name="employeeContractForm" property="encodedId"/>','<bean:write name="employeeContractForm" property="encodedId"/>')" class="kanhandle"><bean:message bundle="public" key="link.add.employee.contract.salary" /></a></span>
					</kan:auth>
				</logic:notEqual>
				<span><bean:message bundle="public" key="public.contract2.tab.tips" /></span>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.vendor" /></label>
						<%=KANUtil.getSelectHTML(employeeContractVO.getSalaryVendors(), "salaryVendorId", "salaryVendorId", employeeContractVO.getSalaryVendorId(), null , null)%>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.income.tax.base" /></label>
						<%
							final boolean lang_en = request.getLocale().getLanguage().equals( "en" );
							employeeContractVO.getIncomeTaxBases().add(0,new MappingVO( "0", "1".equals( BaseAction.getRole( request, null ) ) ? ( lang_en ? "Quote Order" : "参考订单设置" ) : ( lang_en ? "Quote Calculation Rule" : "参照结算规则" ) ) );
						%>
						<%=KANUtil.getSelectHTML(employeeContractVO.getIncomeTaxBases(), "incomeTaxBaseId", "incomeTaxBaseId", employeeContractVO.getIncomeTaxBaseId(), null , null)%>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.income.tax.range" /></label>
						<%
							employeeContractVO.getIncomeTaxRangeHeaders().add(0,new MappingVO( "0", "1".equals( BaseAction.getRole( request, null ) ) ? ( lang_en ? "Quote Order" : "参考订单设置" ) : ( lang_en ? "Quote Calculation Rule" : "参照结算规则" ) ) );
						%>
						<%=KANUtil.getSelectHTML(employeeContractVO.getIncomeTaxRangeHeaders(), "incomeTaxRangeHeaderId", "incomeTaxRangeHeaderId", employeeContractVO.getIncomeTaxRangeHeaderId(), null , null)%>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.type" /></label>
						<%
							employeeContractVO.getSickLeaveSalaryIds().set(0,new MappingVO( "0", "1".equals( BaseAction.getRole( request, null ) ) ? ( lang_en ? "Quote Order" : "参考订单设置" ) : ( lang_en ? "Quote Calculation Rule" : "参照结算规则" ) ) );
						%>
						<%=KANUtil.getSelectHTML(employeeContractVO.getSickLeaveSalaryIds(), "sickLeaveSalaryId", "sickLeaveSalaryId", employeeContractVO.getSickLeaveSalaryId(), null , null)%>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.currency" /></label>
						<%
							employeeContractVO.getCurrencys().set(0,new MappingVO( "0", "1".equals( BaseAction.getRole( request, null ) ) ? ( lang_en ? "Quote Order" : "参考订单设置" ) : ( lang_en ? "Quote Calculation Rule" : "参照结算规则" ) ) );
						%>
						<%=KANUtil.getSelectHTML(employeeContractVO.getCurrencys(), "currency", "currency", employeeContractVO.getCurrency(), null , null)%>
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="employeeContractSalaryVOs">
						<logic:iterate id="employeeContractSalaryVO" name="employeeContractSalaryVOs" indexId="number">
						<%-- <logic:equal name="employeeContractSalaryVO" property="status" value="1"> --%>
							<li>
								<logic:notEqual name="role" value="5">
									<kan:auth right="delete" action="<%=EmployeeContractSalaryAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
										<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
										<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeContractSalaryAction.do?proc=delete_object_ajax&employeeSalaryId=<bean:write name="employeeContractSalaryVO" property="encodedId" />', this, '#numberOfContractSalary');" src="images/warning-btn.png">
									</kan:auth>
								</logic:notEqual>
								<kan:auth right="modify" action="<%=EmployeeContractSalaryAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									&nbsp;&nbsp; <a onclick="link('employeeContractSalaryAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSalaryVO" property="encodedId" />');">
								</kan:auth>
									<bean:write name="employeeContractSalaryVO" property="remark" /> 
								<kan:auth right="modify" action="<%=EmployeeContractSalaryAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									</a> 
								</kan:auth>
								<logic:equal name="employeeContractSalaryVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						<%-- </logic:equal>	 --%>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth action="<%=EmployeeContractSBAction.accessAction%>" right="view">
			<div id="tabContent2" class="kantab" style="display:none">
				<logic:notEqual name="role" value="5">
					<kan:auth right="new" action="<%=EmployeeContractSBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
						<span><a id="addSB" onclick="tabAdd('employeeContractSBAction.do?proc=to_objectNew&contractId=<bean:write name="employeeContractForm" property="encodedId"/>', '<bean:write name="employeeContractForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.sb" /></a></span>
					</kan:auth>
				</logic:notEqual>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.sb.number" /></label>
						<input type="text" name="sbNumber" id="sbNumber" maxlength="50" class="sbNumber" value='<bean:write name="employeeContractForm" property="sbNumber"/>'>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.medical.number" /></label>
						<input type="text" name="medicalNumber" id="medicalNumber" maxlength="50" class="medicalNumber" value='<bean:write name="employeeContractForm" property="medicalNumber"/>'>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.fund.number" /></label>
						<input type="text" name="fundNumber" id="fundNumber" maxlength="50" class="fundNumber" value='<bean:write name="employeeContractForm" property="fundNumber"/>'>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.house.number" /></label>
						<input type="text" name="hsNumber" id="hsNumber" maxlength="50" class="hsNumber" value='<bean:write name="employeeContractForm" property="hsNumber"/>'>
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="employeeContractSBVOs">
						<logic:iterate id="employeeContractSBVO" name="employeeContractSBVOs" indexId="number">
							<li>
								<logic:equal name="employeeContractSBVO" property="status" value="0">
									<logic:notEqual name="role" value="5">
										<kan:auth right="delete" action="<%=EmployeeContractSBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
											<img name="disable_img" width="12" height="12" id="disable_img"  src="images/disable-btn.png">
											<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeContractSBAction.do?proc=delete_object_ajax&employeeSBId=<bean:write name="employeeContractSBVO" property="encodedId" />', this, '#numberOfContractSB');" src="images/warning-btn.png">
										</kan:auth>
									</logic:notEqual>
								</logic:equal>
								<kan:auth right="modify" action="<%=EmployeeContractSBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									&nbsp;&nbsp; <a onclick="link('employeeContractSBAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSBVO" property="encodedId" />');">
								</kan:auth>
									<bean:write name="employeeContractSBVO" property="decodeSbSolutionId" /> 
									<logic:notEmpty name="employeeContractSBVO" property="startDate">
										<bean:write name="employeeContractSBVO" property="startDate" />
									</logic:notEmpty>
									<logic:notEmpty name="employeeContractSBVO" property="endDate">
									 	~ 
									 	<bean:write name="employeeContractSBVO" property="endDate" />
									 </logic:notEmpty>
								 <kan:auth right="modify" action="<%=EmployeeContractSBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>"></a></kan:auth>
								 <logic:notEqual name="employeeContractSBVO" property="status" value="0">
								 	（<bean:write name="employeeContractSBVO" property="decodeStatus" />）
								 </logic:notEqual>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		</kan:auth>
		<kan:auth action="<%=EmployeeContractCBAction.accessAction%>" right="view">
		<div id="tabContent3" class="kantab" style="display:none">
			<logic:notEqual name="role" value="5">
				<kan:auth right="new" action="<%=EmployeeContractCBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
				<span><a id="addCB" onclick="tabAdd('employeeContractCBAction.do?proc=to_objectNew&contractId=<bean:write name="employeeContractForm" property="encodedId"/>', '<bean:write name="employeeContractForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.cb" /></a></span>
				</kan:auth>
			</logic:notEqual>
			<ol class="auto">
				<logic:notEmpty name="employeeContractCBVOs">
					<logic:iterate id="employeeContractCBVO" name="employeeContractCBVOs" indexId="number">
						<li>
							<logic:equal name="employeeContractCBVO" property="status" value="0">
								<logic:notEqual name="role" value="5">
									<kan:auth right="delete" action="<%=EmployeeContractCBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeContractCBAction.do?proc=delete_object_ajax&employeeCBId=<bean:write name="employeeContractCBVO" property="encodedId" />', this, '#numberOfContractCB');" src="images/warning-btn.png">
									</kan:auth>
								</logic:notEqual>
							</logic:equal>
							<kan:auth right="modify" action="<%=EmployeeContractCBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
								&nbsp;&nbsp; <a onclick="link('employeeContractCBAction.do?proc=to_objectModify&id=<bean:write name="employeeContractCBVO" property="encodedId" />');"><bean:write name="employeeContractCBVO" property="decodeSolutionId" />
							</kan:auth>
								 <logic:notEmpty name="employeeContractCBVO" property="startDate">
								 	<bean:write name="employeeContractCBVO" property="startDate" />
								 </logic:notEmpty>
								 <logic:notEmpty name="employeeContractCBVO" property="endDate">
								 	 ~ <bean:write name="employeeContractCBVO" property="endDate" />
								 </logic:notEmpty>
							<kan:auth right="modify" action="<%=EmployeeContractCBAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>"></a> </kan:auth>
							<logic:notEqual name="employeeContractCBVO" property="status" value="0">
								（<bean:write name="employeeContractCBVO" property="decodeStatus" />）
							</logic:notEqual>
						 </li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		</kan:auth>
		<kan:auth action="<%=EmployeeContractLeaveAction.accessAction%>" right="view">
		<div id="tabContent4" class="kantab" style="display:none">
			<logic:notEqual name="role" value="5">
				<kan:auth right="new" action="<%=EmployeeContractLeaveAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
					<span><a id="addLeave" onclick="tabAdd('employeeContractLeaveAction.do?proc=to_objectNew&contractId=<bean:write name="employeeContractForm" property="encodedId"/>', '<bean:write name="employeeContractForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.leave" /></a></span>
				</kan:auth>
			</logic:notEqual>
			<span><bean:message bundle="public" key="public.contract2.tab.tips" /></span>
			<input type="button" class="function" id="btnCalculateAnnualLeave" name="btnCalculateAnnualLeave" value='<bean:message bundle="public" key="button.calculation.annual.leave" />' />
			<ol class="auto">
				<logic:notEmpty name="employeeContractLeaveVOs">
					<logic:iterate id="employeeContractLeaveVO" name="employeeContractLeaveVOs" indexId="number">
						<li>
							<logic:notEqual name="role" value="5">
								<kan:auth right="delete" action="<%=EmployeeContractLeaveAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeContractLeaveAction.do?proc=delete_object_ajax&employeeLeaveId=<bean:write name="employeeContractLeaveVO" property="encodedId" />', this, '#numberOfContractLeave');" src="images/warning-btn.png">
								</kan:auth>
							</logic:notEqual>
							<kan:auth right="modify" action="<%=EmployeeContractLeaveAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
								&nbsp;&nbsp; <a onclick="link('employeeContractLeaveAction.do?proc=to_objectModify&id=<bean:write name="employeeContractLeaveVO" property="encodedId" />');">
							</kan:auth>
								<bean:write name="employeeContractLeaveVO" property="remark" />
							<kan:auth right="modify" action="<%=EmployeeContractLeaveAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
								</a>
							</kan:auth> 
							<logic:equal name="employeeContractLeaveVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		</kan:auth>
		<kan:auth action="<%=EmployeeContractOTAction.accessAction%>" right="view">
		<div id="tabContent5" class="kantab" style="display:none">
			<logic:notEqual name="role" value="5">
				<kan:auth right="new" action="<%=EmployeeContractOTAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
					<span><a id="addOT" onclick="tabAdd('employeeContractOTAction.do?proc=to_objectNew&contractId=<bean:write name="employeeContractForm" property="encodedId"/>', '<bean:write name="employeeContractForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.ot" /></a></span>
				</kan:auth>
			</logic:notEqual>
			<span><bean:message bundle="public" key="public.contract2.tab.tips" /></span>
			<logic:notEmpty name="employeeContractForm" property="flag">
				<logic:equal name="employeeContractForm" property="flag" value="2">
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.apply.first" /></label>
							<%	
								final boolean lang_en = request.getLocale().getLanguage().equalsIgnoreCase( "EN" );
								employeeContractVO.getFlags().set(0,new MappingVO( "0", "1".equals( BaseAction.getRole( request, null ) ) ? ( lang_en ? "Quote Order" : "参考订单设置" ) : ( lang_en ? "Quote Calculation Rule" : "参照结算规则" ) ) );
							%>
							<%=KANUtil.getSelectHTML(employeeContractVO.getFlags(), "applyOTFirst", "applyOTFirst", employeeContractVO.getApplyOTFirst(), null , null)%>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.limit.day" /></label> 
							<input type="text" name="otLimitByDay" id="otLimitByDay" maxlength="2" class="otLimitByDay" value='<bean:write name="employeeContractForm" property="otLimitByDay"/>'>
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.limit.month" /></label> 
							<input type="text" name="otLimitByMonth" id="otLimitByMonth" maxlength="3" class="otLimitByMonth" value='<bean:write name="employeeContractForm" property="otLimitByMonth"/>'>
						</li>
					</ol>
					<ol class="auto">
						<%
							employeeContractVO.getOtItems().set(0,new MappingVO( "0", "1".equals( BaseAction.getRole( request, null ) ) ? ( lang_en ? "Quote Order" : "参考订单设置" ) : ( lang_en ? "Quote Calculation Rule" : "参照结算规则" ) ) );
						%>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.work.day.rule" />  <img src="images/tips.png" title="<bean:message bundle="business" key="business.employee.contract.ot.rule.tips" />" /></label>
							<%=KANUtil.getSelectHTML(employeeContractVO.getOtItems(), "workdayOTItemId", "workdayOTItemId", employeeContractVO.getWorkdayOTItemId(), null , null)%>
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.week.end.rule" />  <img src="images/tips.png" title="<bean:message bundle="business" key="business.employee.contract.ot.rule.tips" />" /></label> 
							<%=KANUtil.getSelectHTML(employeeContractVO.getOtItems(), "weekendOTItemId", "weekendOTItemId", employeeContractVO.getWeekendOTItemId(), null , null)%>
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.holi.day.rule" />  <img src="images/tips.png" title="<bean:message bundle="business" key="business.employee.contract.ot.rule.tips" />" /></label> 
							<%=KANUtil.getSelectHTML(employeeContractVO.getOtItems(), "holidayOTItemId", "holidayOTItemId", employeeContractVO.getHolidayOTItemId(), null , null)%>
						</li>
					</ol>
				</logic:equal>
			</logic:notEmpty>
			<ol class="auto">
				<logic:notEmpty name="employeeContractOTVOs">
					<logic:iterate id="employeeContractOTVO" name="employeeContractOTVOs" indexId="number">
						<li>
							<logic:notEqual name="role" value="5">
								<kan:auth right="delete" action="<%=EmployeeContractOTAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
									<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeContractOTAction.do?proc=delete_object_ajax&employeeOTId=<bean:write name="employeeContractOTVO" property="encodedId" />', this, '#numberOfContractOT');" src="images/warning-btn.png">
								</kan:auth>
							</logic:notEqual>
							<kan:auth right="modify" action="<%=EmployeeContractOTAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
								&nbsp;&nbsp; <a onclick="link('employeeContractOTAction.do?proc=to_objectModify&id=<bean:write name="employeeContractOTVO" property="encodedId" />');">
							</kan:auth>
							<bean:write name="employeeContractOTVO" property="decodeItemId" />
								（
								<logic:greaterThan name="employeeContractOTVO" property="baseFrom" value="0">
									<bean:write name="employeeContractOTVO" property="decodeBaseFrom" />
									<logic:greaterThan name="employeeContractOTVO" property="percentage" value="0">
										&nbsp;*&nbsp;<bean:write name="employeeContractOTVO" property="percentage" />%
									</logic:greaterThan>
									<logic:greaterThan name="employeeContractOTVO" property="fix" value="0">
										&nbsp;+&nbsp;￥<bean:write name="employeeContractOTVO" property="fix" />
									</logic:greaterThan>
								</logic:greaterThan>
								<logic:lessThan name="employeeContractOTVO" property="baseFrom" value="1">
									<logic:greaterThan name="employeeContractOTVO" property="base" value="0">
										￥<bean:write name="employeeContractOTVO" property="base" />
									</logic:greaterThan> 
								</logic:lessThan>
								<logic:greaterThan name="employeeContractOTVO" property="discount" value="0">
									&nbsp;*&nbsp;<bean:write name="employeeContractOTVO" property="discount" />%
								</logic:greaterThan>
								<logic:greaterThan name="employeeContractOTVO" property="multiple" value="0">
									&nbsp;*&nbsp;<bean:write name="employeeContractOTVO" property="decodeMultiple" />
								</logic:greaterThan>
								）
							<bean:write name="employeeContractOTVO" property="startDate" />
							<logic:notEmpty name="employeeContractOTVO" property="endDate"> ~ <bean:write name="employeeContractOTVO" property="endDate" /></logic:notEmpty>
							<kan:auth right="modify" action="<%=EmployeeContractOTAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
								</a>
							</kan:auth>
							<logic:equal name="employeeContractOTVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
		</kan:auth>
		<kan:auth action="<%=EmployeeContractOtherAction.accessAction%>" right="view">
			<div id="tabContent6" class="kantab" style="display:none">
				<logic:notEqual name="role" value="5">
					<kan:auth right="new" action="<%=EmployeeContractOtherAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
						<span><a id="addOther" onclick="tabAdd('employeeContractOtherAction.do?proc=to_objectNew&contractId=<bean:write name="employeeContractForm" property="encodedId"/>', '<bean:write name="employeeContractForm" property="encodedId"/>');" class="kanhandle"><bean:message bundle="public" key="link.new.other" /></a></span>
					</kan:auth>
				</logic:notEqual>
				<ol class="auto">
					<logic:notEmpty name="employeeContractOtherVOs">
						<logic:iterate id="employeeContractOtherVO" name="employeeContractOtherVOs" indexId="number">
							<li>
								<logic:notEqual name="role" value="5">
									<kan:auth right="delete" action="<%=EmployeeContractOtherAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
										<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
										<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeContractOtherAction.do?proc=delete_object_ajax&employeeOtherId=<bean:write name="employeeContractOtherVO" property="encodedId" />', this, '#numberOfContractOther');" src="images/warning-btn.png">
									</kan:auth>
								</logic:notEqual>
								<kan:auth right="modify" action="<%=EmployeeContractOtherAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									&nbsp;&nbsp; <a onclick="link('employeeContractOtherAction.do?proc=to_objectModify&id=<bean:write name="employeeContractOtherVO" property="encodedId" />');">
								</kan:auth>
								<bean:write name="employeeContractOtherVO" property="decodeItemId" /> 
								（
									<logic:greaterThan name="employeeContractOtherVO" property="baseFrom" value="0">
										<bean:write name="employeeContractOtherVO" property="decodeBaseFrom" />
										<logic:greaterThan name="employeeContractOtherVO" property="percentage" value="0">
											&nbsp;*&nbsp;<bean:write name="employeeContractOtherVO" property="percentage" />%
										</logic:greaterThan>
										<logic:greaterThan name="employeeContractOtherVO" property="fix" value="0">
											&nbsp;+&nbsp;￥<bean:write name="employeeContractOtherVO" property="fix" />
										</logic:greaterThan>
									</logic:greaterThan>
									<logic:lessThan name="employeeContractOtherVO" property="baseFrom" value="1">
										<logic:greaterThan name="employeeContractOtherVO" property="base" value="0">
											￥<bean:write name="employeeContractOtherVO" property="base" />
										</logic:greaterThan> 
									</logic:lessThan>
								 / 
									<logic:notEqual name="employeeContractOtherVO" property="cycle" value="0">
										<logic:notEqual name="employeeContractOtherVO" property="cycle" value="13">
											<bean:write name="employeeContractOtherVO" property="decodeCycle" />
										</logic:notEqual>
									</logic:notEqual>
								）
									<bean:write name="employeeContractOtherVO" property="startDate" />
								<logic:notEmpty name="employeeContractOtherVO" property="endDate"> ~ <bean:write name="employeeContractOtherVO" property="endDate" /></logic:notEmpty>
								<kan:auth right="modify" action="<%=EmployeeContractOtherAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
									</a>
								</kan:auth>
								<logic:equal name="employeeContractOtherVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
		</kan:auth>
		<logic:notEqual name="role" value="5">
			<kan:auth action="<%=EmployeeContractSettlementAction.accessAction%>" right="view">
				<div id="tabContent7" class="kantab" style="display:none">
						<kan:auth right="new" action="<%=EmployeeContractSettlementAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
							<span><a id="addOther" onclick="tabAdd('employeeContractSettlementAction.do?proc=to_objectNew&contractId=<bean:write name="employeeContractForm" property="encodedId"/>', '<bean:write name="employeeContractForm" property="encodedId"/>');" class="kanhandle"><%=KANConstants.ROLE_IN_HOUSE.equals(BaseAction.getRole(request,null))?"<bean:message bundle='public' key='link.add.employee.cost' />":"添加营收" %></a></span>
						</kan:auth>
					<ol class="auto">
						<logic:notEmpty name="employeeContractSettlementVOs">
							<logic:iterate id="employeeContractSettlementVO" name="employeeContractSettlementVOs" indexId="number">
								<li>
									<logic:notEqual name="role" value="5">
										<kan:auth right="delete" action="<%=EmployeeContractSettlementAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
											<img name="disable_img" width="12" height="12" id="disable_img" src="images/disable-btn.png">
											<img name="warning_img" width="12" height="12" id="warning_img" style="display: none;" onclick="removeExtraObject('employeeContractSettlementAction.do?proc=delete_object_ajax&employeeSettlementId=<bean:write name="employeeContractSettlementVO" property="encodedId" />', this, '#numberOfContractSettlement');" src="images/warning-btn.png">
										</kan:auth>
									</logic:notEqual>
									<kan:auth right="modify" action="<%=EmployeeContractSettlementAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
										&nbsp;&nbsp; <a onclick="link('employeeContractSettlementAction.do?proc=to_objectModify&id=<bean:write name="employeeContractSettlementVO" property="encodedId" />');">
									</kan:auth>
									<bean:write name="employeeContractSettlementVO" property="showTitle"/>
									<kan:auth right="modify" action="<%=EmployeeContractSettlementAction.accessAction%>" owner="<%=employeeContractVO.getOwner()%>">
										</a>
									</kan:auth>
									<logic:equal name="employeeContractSettlementVO" property="status" value="2">
										<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
									</logic:equal>
								</li>
							</logic:iterate>
						</logic:notEmpty>
					</ol>
				</div> 
			</kan:auth>
		</logic:notEqual>
		<kan:auth right="view" action="<%=EmployeeSalaryAdjustmentAction.accessAction %>">
			<div id="tabContent8" class="kantab" style="display: none;">
				<div id="tableWrapper">
					<jsp:include page="/contents/business/employee/salaryAdjustment/table/listSalaryAdjustmentTable.jsp" flush="true"/>  
				</div>
			</div>
		</kan:auth>
	</div>
</div>

<input type="hidden" id="forwardURL" name="forwardURL" value="" />

<script type="text/javascript">	
	(function($) {
		$(".tabMenu li").first().addClass('hover');
		$(".tabMenu li").first().addClass('first');
		$("#tabContent .kantab").first().show();
		
		var contractId = '<bean:write name="employeeContractForm" property="encodedId"/>';
		
		// 计算年假点击事件
		$('#btnCalculateAnnualLeave').click( function() {
			if(confirm('<bean:message bundle="public" key="popup.confirm.calculation.annual.leave" />')){
				$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=calculate_annual_leave');
				submit('manage_primary_form');
			}
		});
		
		// 存在EmployeeId的情况
		if( $('#employeeId').val() != '' ){
			$('#employeeId').trigger('keyup');
		}
		
		// 存在OrderId的情况
		if( $('#orderId').val() != '' ){
			$('#orderId').trigger('keyup');
		}
		
		// 页面初始化 - 除新建情况
		if($('.manage_primary_form input#subAction').val() != 'createObject'){
			// 禁止Tab中的链接
			disableLink('manage_primary_form');	
			$('.manage_primary_form input#subAction').val('viewObject');
			
			<%
				if(employeeContractVO.getStatus() != null && (employeeContractVO.getStatus().trim().equals("1") || employeeContractVO.getStatus().trim().equals("3") || employeeContractVO.getStatus().trim().equals("4") || employeeContractVO.getStatus().trim().equals("5") || employeeContractVO.getStatus().trim().equals("6"))){
			%>
					enableLinkById('#addSalary');
					enableLinkById('#addSB');
					enableLinkById('#addCB');
					enableLinkById('#addLeave');
					enableLinkById('#addOT');
					enableLinkById('#addOther');
					$('li #warning_img').each(function(){$(this).show();}); 
					$('li #disable_img').each(function(){$(this).hide();});
			<%	   
				}
			%>
		}
		// 页面初始化 - 新建
		else{
			// 突显
			$('#employeeId').addClass('important');
			$('#orderId').addClass('important');
			// Disable控件
			$('#employeeNameZH').attr('disabled', 'disabled');
			$('#employeeNameEN').attr('disabled', 'disabled');
			$('#clientId').attr('disabled', 'disabled');
			$('#clientNameZH').attr('disabled', 'disabled');
			$('#clientNameEN').attr('disabled', 'disabled');
			$('#resignDate').attr('disabled', 'disabled');
			$('#employStatus').attr('disabled', 'disabled');
			$('#entityId').attr('disabled', 'disabled');
			$('#locked').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
			
			if($("#employeeId").val() == ''){
				$('#employeeIdLI label:eq(0)').append(' &nbsp; <a onclick="popupEmployeeAdd()"><bean:message bundle="public" key="link.quick.create" /></a>');
			}
		}
		

		if( getSubAction() == 'createObject'){
			$('#uploadAttachment').show();
		}
		
		// 附件提交按钮事件
		var uploadObject = createUploadObject('uploadAttachment', 'all', '/<%= KANConstants.SHAREFOLDER_SUB_DIRECTORY_EMPLOYEE_CONTRACT %>/<%= BaseAction.getAccountId(request, response) %>/<%= java.net.URLEncoder.encode( java.net.URLEncoder.encode(BaseAction.getUsername(request, response),"utf-8"),"utf-8") %>/');
	})(jQuery);
	
	// tab下面的添加事件，如果当前是modifyObject 则先保存然后再添加
	function tabAdd(targetURL, primaryKey){
		link(targetURL);
		//if($('.subAction').val()=='modifyObject'){
			// 先提交
			//$("#forwardURL").val(targetURL);
			//enableForm('manage_primary_form');
			//$(".manage_primary_form").submit();
		//}else{
			//addExtraObject(targetURL,primaryKey);
		//} 
	};
</script>