<%@page import="java.util.Locale"%>
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
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<% 
	final EmployeeContractVO employeeContractVO = (EmployeeContractVO) request.getAttribute("employeeContractForm"); 
	final EmployeeContractVO passObject = ( EmployeeContractVO )request.getAttribute( "passObject" );
	final EmployeeContractVO originalObject = ( EmployeeContractVO )request.getAttribute( "originalObject" );
	request.setAttribute("role", BaseAction.getRole(request, response)); 
	final String index = (String)request.getAttribute( "tabIndex" );
	final Locale locale = request.getLocale();
%>

<%-- 修改类型 Tab1 --%>
<logic:equal name="type" value="3">
<logic:equal name="tabIndex" value="1">
<div id="tab">
	<div class="tabMenu"> 
		<ul> 
			<li id="_tabMenu1" onClick="_changeTab(1,8)" class="hover first"><bean:message bundle="public" key="menu.table.title.salary" /> (<span id="numberOfContractSalary"><bean:write name="numberOfContractSalary"/></span>)</li>
			<li id="_tabMenu2" onClick="_changeTab(2,8)" ><bean:message bundle="public" key="menu.table.title.sb" /> (<span id="numberOfContractSB"><bean:write name="numberOfContractSB"/></span>)</li> 
			<li id="_tabMenu3" onClick="_changeTab(3,8)" ><bean:message bundle="public" key="menu.table.title.cb" /> (<span id="numberOfContractCB"><bean:write name="numberOfContractCB"/></span>)</li>
			<li id="_tabMenu4" onClick="_changeTab(4,8)" ><bean:message bundle="public" key="menu.table.title.leave" /> (<span id="numberOfContractLeave"><bean:write name="numberOfContractLeave"/></span>)</li> 
			<li id="_tabMenu5" onClick="_changeTab(5,8)" ><bean:message bundle="public" key="menu.table.title.ot" /> (<span id="numberOfContractOT"><bean:write name="numberOfContractOT"/></span>)</li> 
			<li id="_tabMenu6" onClick="_changeTab(6,8)" ><bean:message bundle="public" key="menu.table.title.other" /> (<span id="numberOfContractOther"><bean:write name="numberOfContractOther"/></span>)</li> 
			<li id="_tabMenu7" onClick="_changeTab(7,8)" ><%=KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) ? KANUtil.getProperty( request.getLocale(), "menu.table.title.cost" ) : "营收" %> (<span id="numberOfContractSettlement"><bean:write name="numberOfContractSettlement"/></span>)</li>  
		</ul> 
	</div>  
	<div id="tabContent" class="tabContent">
			<div id="_tabContent1" class="kantab">
				<ol class="auto">
					<%
						final String salaryVendorIdLabel = KANUtil.getProperty( locale, "business.employee.contract.salary.vendor" );
						if( index.equals( "1" )){
						   out.print( KANUtil.getCompareHTML( passObject.getDecodeSalaryVendorId(), originalObject.getDecodeSalaryVendorId(), salaryVendorIdLabel ) );
						}
					%>
				</ol>
				<ol class="auto">
						<%
							final String incomeTaxBaseIdLabel = KANUtil.getProperty( locale, "business.employee.contract.salary.income.tax.base" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeIncomeTaxBaseId(), originalObject.getDecodeIncomeTaxBaseId(), incomeTaxBaseIdLabel ) );
							}
						%>
						<%
							final String incomeTaxRangeHeaderIdLabel = KANUtil.getProperty( locale, "business.employee.contract.salary.income.tax.range" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeIncomeTaxRangeHeaderId(), originalObject.getDecodeIncomeTaxRangeHeaderId(), incomeTaxRangeHeaderIdLabel ) );
							}
						%>
						<%
							final String sickLeaveSalaryIdLabel = KANUtil.getProperty( locale, "business.employee.contract.salary.type" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeSickLeaveSalaryId(), originalObject.getDecodeSickLeaveSalaryId(), sickLeaveSalaryIdLabel ) );
							}
						%>
						<%
							final String currencyLabel = KANUtil.getProperty( locale, "business.employee.contract.salary.currency" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeCurrency(), originalObject.getDecodeCurrency(), currencyLabel ) );
							}
						%>
					
				</ol>
				<ol class="auto">
					<logic:notEmpty name="employeeContractSalaryVOs">
						<logic:iterate id="employeeContractSalaryVO" name="employeeContractSalaryVOs" indexId="number">
							<li>
								<bean:write name="employeeContractSalaryVO" property="remark" /> 
								<logic:equal name="employeeContractSalaryVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
			<div id="_tabContent2" class="kantab" style="display:none">
				<ol class="auto">
						<%
							final String sbNumberLabel = KANUtil.getProperty( locale, "business.employee.contract.sb.sb.number" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getSbNumber(), originalObject.getSbNumber(), sbNumberLabel ) );
							}
						%>
						<%
							final String medicalNumberLabel = KANUtil.getProperty( locale, "business.employee.contract.sb.medical.number" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getMedicalNumber(), originalObject.getMedicalNumber(), medicalNumberLabel ) );
							}
						%>
					
				</ol>
				<ol class="auto">
					
						<%
							final String fundNumberLabel = KANUtil.getProperty( locale, "business.employee.contract.sb.fund.number" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getFundNumber(), originalObject.getFundNumber(), fundNumberLabel ) );
							}
						%>
						<%
							final String hsNumberLabel = KANUtil.getProperty( locale, "business.employee.contract.sb.house.number" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getHsNumber(), originalObject.getHsNumber(), hsNumberLabel ) );
							}
						%>
					
				</ol>
				<ol class="auto">
					<logic:notEmpty name="employeeContractSBVOs">
						<logic:iterate id="employeeContractSBVO" name="employeeContractSBVOs" indexId="number">
							<li>
								<logic:equal name="employeeContractSBVO" property="status" value="0">
								</logic:equal>
									<bean:write name="employeeContractSBVO" property="decodeSbSolutionId" /> 
									<logic:notEmpty name="employeeContractSBVO" property="startDate">
										<bean:write name="employeeContractSBVO" property="startDate" />
									</logic:notEmpty>
									<logic:notEmpty name="employeeContractSBVO" property="endDate">
									 	~ 
									 	<bean:write name="employeeContractSBVO" property="endDate" />
									 </logic:notEmpty>
								 <logic:notEqual name="employeeContractSBVO" property="status" value="0">
								 	（<bean:write name="employeeContractSBVO" property="decodeStatus" />）
								 </logic:notEqual>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		<div id="_tabContent3" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="employeeContractCBVOs">
					<logic:iterate id="employeeContractCBVO" name="employeeContractCBVOs" indexId="number">
						<li>
							<logic:equal name="employeeContractCBVO" property="status" value="0">
							</logic:equal>
								 <logic:notEmpty name="employeeContractCBVO" property="startDate">
								 	<bean:write name="employeeContractCBVO" property="startDate" />
								 </logic:notEmpty>
								 <logic:notEmpty name="employeeContractCBVO" property="endDate">
								 	 ~ <bean:write name="employeeContractCBVO" property="endDate" />
								 </logic:notEmpty>
							<logic:notEqual name="employeeContractCBVO" property="status" value="0">
								（<bean:write name="employeeContractCBVO" property="decodeStatus" />）
							</logic:notEqual>
						 </li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		<div id="_tabContent4" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="employeeContractLeaveVOs">
					<logic:iterate id="employeeContractLeaveVO" name="employeeContractLeaveVOs" indexId="number">
						<li>
							<bean:write name="employeeContractLeaveVO" property="remark" />
							<logic:equal name="employeeContractLeaveVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		<div id="_tabContent5" class="kantab" style="display:none">
			<logic:notEmpty name="employeeContractForm" property="flag">
				<logic:equal name="employeeContractForm" property="flag" value="2">
					<ol class="auto">
						<%
							final String applyOTFirstLabel = KANUtil.getProperty( locale, "business.employee.contract.ot.apply.first" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeApplyOTFirst(), originalObject.getDecodeApplyOTFirst(), applyOTFirstLabel ) );
							}
						%>
					</ol>
					<ol class="auto">
						<%
							final String otLimitByDayLabel = KANUtil.getProperty( locale, "business.employee.contract.ot.limit.day" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getOtLimitByDay(), originalObject.getOtLimitByDay(), otLimitByDayLabel ) );
							}
						%>
						<%
							final String otLimitByMonthLabel = KANUtil.getProperty( locale, "business.employee.contract.ot.limit.day" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getOtLimitByMonth(), originalObject.getOtLimitByMonth(), otLimitByMonthLabel ) );
							}
						%>
					</ol>
					<ol class="auto">
						<%
							final String workdayOTItemIdLabel = KANUtil.getProperty( locale, "business.employee.contract.ot.work.day.rule" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeWorkdayOTItemId(), originalObject.getDecodeWorkdayOTItemId(), workdayOTItemIdLabel ) );
							}
						%>
						<%
							final String weekendOTItemIdLabel = KANUtil.getProperty( locale, "business.employee.contract.ot.week.end.rule" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeWeekendOTItemId(), originalObject.getDecodeWeekendOTItemId(), weekendOTItemIdLabel ) );
							}
						%>
						<%
							final String holidayOTItemIdLabel = KANUtil.getProperty( locale, "business.employee.contract.ot.holi.day.rule" );
							if( index.equals( "1" )){
							   out.print( KANUtil.getCompareHTML( passObject.getDecodeHolidayOTItemId(), originalObject.getDecodeHolidayOTItemId(), holidayOTItemIdLabel ) );
							}
						%>
						
					</ol>
				</logic:equal>
			</logic:notEmpty>
			<ol class="auto">
				<logic:notEmpty name="employeeContractOTVOs">
					<logic:iterate id="employeeContractOTVO" name="employeeContractOTVOs" indexId="number">
						<li>
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
							<logic:equal name="employeeContractOTVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
			<div id="_tabContent6" class="kantab" style="display:none">
				<ol class="auto">
					<logic:notEmpty name="employeeContractOtherVOs">
						<logic:iterate id="employeeContractOtherVO" name="employeeContractOtherVOs" indexId="number">
							<li>
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
										<logic:notEqual name="employeeContractOtherVO" property="cycle" value="13"></logic:notEqual>
										<bean:write name="employeeContractOtherVO" property="decodeCycle" />
									</logic:notEqual>
								）
								<bean:write name="employeeContractOtherVO" property="startDate" />
								<logic:notEmpty name="employeeContractOtherVO" property="endDate"> ~ <bean:write name="employeeContractOtherVO" property="endDate" /></logic:notEmpty>
								<logic:equal name="employeeContractOtherVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
			<div id="_tabContent7" class="kantab" style="display:none">
				<ol class="auto">
					<logic:notEmpty name="employeeContractSettlementVOs">
						<logic:iterate id="employeeContractSettlementVO" name="employeeContractSettlementVOs" indexId="number">
							<li>
								<bean:write name="employeeContractSettlementVO" property="showTitle"/>
								<logic:equal name="employeeContractSettlementVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
	</div>
</div>
</logic:equal>
</logic:equal>


<%--修改类型Tab2 --%>
<logic:equal name="type" value="3">
<logic:equal name="tabIndex" value="2">
<div id="tab">
	<div class="tabMenu"> 
		<ul> 
			<li id="__tabMenu1" onClick="__changeTab(1,8)" class="hover first"><bean:message bundle="public" key="menu.table.title.salary" /> (<span id="numberOfContractSalary"><bean:write name="numberOfContractSalary"/></span>)</li>
			<li id="__tabMenu2" onClick="__changeTab(2,8)" ><bean:message bundle="public" key="menu.table.title.sb" /> (<span id="numberOfContractSB"><bean:write name="numberOfContractSB"/></span>)</li> 
			<li id="__tabMenu3" onClick="__changeTab(3,8)" ><bean:message bundle="public" key="menu.table.title.cb" /> (<span id="numberOfContractCB"><bean:write name="numberOfContractCB"/></span>)</li>
			<li id="__tabMenu4" onClick="__changeTab(4,8)" ><bean:message bundle="public" key="menu.table.title.leave" /> (<span id="numberOfContractLeave"><bean:write name="numberOfContractLeave"/></span>)</li> 
			<li id="__tabMenu5" onClick="__changeTab(5,8)" ><bean:message bundle="public" key="menu.table.title.ot" /> (<span id="numberOfContractOT"><bean:write name="numberOfContractOT"/></span>)</li> 
			<li id="__tabMenu6" onClick="__changeTab(6,8)" ><bean:message bundle="public" key="menu.table.title.other" /> (<span id="numberOfContractOther"><bean:write name="numberOfContractOther"/></span>)</li> 
			<li id="__tabMenu7" onClick="__changeTab(7,8)" ><%=KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) ? KANUtil.getProperty( request.getLocale(), "menu.table.title.cost" ) : "营收" %> (<span id="numberOfContractSettlement"><bean:write name="numberOfContractSettlement"/></span>)</li>  
		</ul> 
	</div>  
	<div id="tabContent" class="tabContent">
			<div id="__tabContent1" class="kantab">
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.vendor" /></label>
						<input type="text" value='<bean:write name="originalObject" property="decodeSalaryVendorId" />' >
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.income.tax.base" /></label>
						<input type="text" value='<bean:write name="originalObject" property="decodeIncomeTaxBaseId" />' >
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.income.tax.range" /></label>
						<input type="text" value='<bean:write name="originalObject" property="decodeIncomeTaxRangeHeaderId" />' >
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.type" /></label>
						<input type="text" value='<bean:write name="originalObject" property="decodeSickLeaveSalaryId" />' >
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.salary.currency" /></label>
						<input type="text" value='<bean:write name="originalObject" property="decodeCurrency" />' >
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="employeeContractSalaryVOs">
						<logic:iterate id="employeeContractSalaryVO" name="employeeContractSalaryVOs" indexId="number">
							<li>
								<bean:write name="employeeContractSalaryVO" property="remark" /> 
								<logic:equal name="employeeContractSalaryVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
			<div id="__tabContent2" class="kantab" style="display:none">
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.sb.number" /></label>
						<input type="text" value='<bean:write name="originalObject" property="sbNumber" />' >
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.medical.number" /></label>
						<input type="text" value='<bean:write name="originalObject" property="medicalNumber" />' >
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.fund.number" /></label>
						<input type="text" value='<bean:write name="originalObject" property="fundNumber" />' >
					</li>
					<li>
						<label><bean:message bundle="business" key="business.employee.contract.sb.house.number" /></label>
						<input type="text" value='<bean:write name="originalObject" property="hsNumber" />' >
					</li>
				</ol>
				<ol class="auto">
					<logic:notEmpty name="employeeContractSBVOs">
						<logic:iterate id="employeeContractSBVO" name="employeeContractSBVOs" indexId="number">
							<li>
								<logic:equal name="employeeContractSBVO" property="status" value="0">
								</logic:equal>
									<bean:write name="employeeContractSBVO" property="decodeSbSolutionId" /> 
									<logic:notEmpty name="employeeContractSBVO" property="startDate">
										<bean:write name="employeeContractSBVO" property="startDate" />
									</logic:notEmpty>
									<logic:notEmpty name="employeeContractSBVO" property="endDate">
									 	~ 
									 	<bean:write name="employeeContractSBVO" property="endDate" />
									 </logic:notEmpty>
								 <logic:notEqual name="employeeContractSBVO" property="status" value="0">
								 	（<bean:write name="employeeContractSBVO" property="decodeStatus" />）
								 </logic:notEqual>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		<div id="__tabContent3" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="employeeContractCBVOs">
					<logic:iterate id="employeeContractCBVO" name="employeeContractCBVOs" indexId="number">
						<li>
							<logic:equal name="employeeContractCBVO" property="status" value="0">
							</logic:equal>
								 <logic:notEmpty name="employeeContractCBVO" property="startDate">
								 	<bean:write name="employeeContractCBVO" property="startDate" />
								 </logic:notEmpty>
								 <logic:notEmpty name="employeeContractCBVO" property="endDate">
								 	 ~ <bean:write name="employeeContractCBVO" property="endDate" />
								 </logic:notEmpty>
							<logic:notEqual name="employeeContractCBVO" property="status" value="0">
								（<bean:write name="employeeContractCBVO" property="decodeStatus" />）
							</logic:notEqual>
						 </li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		<div id="__tabContent4" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="employeeContractLeaveVOs">
					<logic:iterate id="employeeContractLeaveVO" name="employeeContractLeaveVOs" indexId="number">
						<li>
							<bean:write name="employeeContractLeaveVO" property="remark" />
							<logic:equal name="employeeContractLeaveVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		<div id="__tabContent5" class="kantab" style="display:none">
			<logic:notEmpty name="employeeContractForm" property="flag">
				<logic:equal name="employeeContractForm" property="flag" value="2">
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.apply.first" /></label>
							<input type="text" value='<bean:write name="originalObject" property="decodeApplyOTFirst" />' >
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.limit.day" /></label>
							<input type="text" value='<bean:write name="originalObject" property="otLimitByDay" />' >
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.limit.day" /></label>
							<input type="text" value='<bean:write name="originalObject" property="otLimitByMonth" />' >
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.work.day.rule" /></label>
							<input type="text" value='<bean:write name="originalObject" property="decodeWorkdayOTItemId" />' >
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.week.end.rule" /></label>
							<input type="text" value='<bean:write name="originalObject" property="decodeWeekendOTItemId" />' >
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.holi.day.rule" /></label>
							<input type="text" value='<bean:write name="originalObject" property="decodeHolidayOTItemId" />' >
						</li>
					</ol>
				</logic:equal>
			</logic:notEmpty>
			<ol class="auto">
				<logic:notEmpty name="employeeContractOTVOs">
					<logic:iterate id="employeeContractOTVO" name="employeeContractOTVOs" indexId="number">
						<li>
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
							<logic:equal name="employeeContractOTVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
			<div id="__tabContent6" class="kantab" style="display:none">
				<ol class="auto">
					<logic:notEmpty name="employeeContractOtherVOs">
						<logic:iterate id="employeeContractOtherVO" name="employeeContractOtherVOs" indexId="number">
							<li>
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
										<logic:notEqual name="employeeContractOtherVO" property="cycle" value="13"></logic:notEqual>
										<bean:write name="employeeContractOtherVO" property="decodeCycle" />
									</logic:notEqual>
								）
								<bean:write name="employeeContractOtherVO" property="startDate" />
								<logic:notEmpty name="employeeContractOtherVO" property="endDate"> ~ <bean:write name="employeeContractOtherVO" property="endDate" /></logic:notEmpty>
								<logic:equal name="employeeContractOtherVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
			<div id="__tabContent7" class="kantab" style="display:none">
				<ol class="auto">
					<logic:notEmpty name="employeeContractSettlementVOs">
						<logic:iterate id="employeeContractSettlementVO" name="employeeContractSettlementVOs" indexId="number">
							<li>
								<bean:write name="employeeContractSettlementVO" property="showTitle"/>
								<logic:equal name="employeeContractSettlementVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
	</div>
</div>
</logic:equal>
</logic:equal>


<%-- 新增类型 --%>
<logic:equal name="type" value="1">
<div id="tab">
	<div class="tabMenu"> 
		<ul> 
			<li id="_tabMenu1" onClick="_changeTab(1,8)" class="hover first"><bean:message bundle="public" key="menu.table.title.salary" /> (<span id="numberOfContractSalary"><bean:write name="numberOfContractSalary"/></span>)</li>
			<li id="_tabMenu2" onClick="_changeTab(2,8)" ><bean:message bundle="public" key="menu.table.title.sb" /> (<span id="numberOfContractSB"><bean:write name="numberOfContractSB"/></span>)</li> 
			<li id="_tabMenu3" onClick="_changeTab(3,8)" ><bean:message bundle="public" key="menu.table.title.cb" /> (<span id="numberOfContractCB"><bean:write name="numberOfContractCB"/></span>)</li>
			<li id="_tabMenu4" onClick="_changeTab(4,8)" ><bean:message bundle="public" key="menu.table.title.leave" /> (<span id="numberOfContractLeave"><bean:write name="numberOfContractLeave"/></span>)</li> 
			<li id="_tabMenu5" onClick="_changeTab(5,8)" ><bean:message bundle="public" key="menu.table.title.ot" /> (<span id="numberOfContractOT"><bean:write name="numberOfContractOT"/></span>)</li> 
			<li id="_tabMenu6" onClick="_changeTab(6,8)" ><bean:message bundle="public" key="menu.table.title.other" /> (<span id="numberOfContractOther"><bean:write name="numberOfContractOther"/></span>)</li> 
			<li id="_tabMenu7" onClick="_changeTab(7,8)" ><%=KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) ? KANUtil.getProperty( request.getLocale(), "menu.table.title.cost" ) : "营收" %> (<span id="numberOfContractSettlement"><bean:write name="numberOfContractSettlement"/></span>)</li>  
		</ul> 
	</div>  
	<div id="tabContent" class="tabContent">
			<div id="_tabContent1" class="kantab">
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
							employeeContractVO.getIncomeTaxRangeHeaders().add(0,new MappingVO( "0", "1".equals( BaseAction.getRole( request, null ) ) ? ( lang_en ? "Quote Order" : "参考订单设置" ) : ( lang_en ? "Quote Calculation Rule" : "参照结算规则" ) ) );
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
							<li>
								<bean:write name="employeeContractSalaryVO" property="remark" /> 
								<logic:equal name="employeeContractSalaryVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
			<div id="_tabContent2" class="kantab" style="display:none">
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
								</logic:equal>
									<bean:write name="employeeContractSBVO" property="decodeSbSolutionId" /> 
									<logic:notEmpty name="employeeContractSBVO" property="startDate">
										<bean:write name="employeeContractSBVO" property="startDate" />
									</logic:notEmpty>
									<logic:notEmpty name="employeeContractSBVO" property="endDate">
									 	~ 
									 	<bean:write name="employeeContractSBVO" property="endDate" />
									 </logic:notEmpty>
								 <logic:notEqual name="employeeContractSBVO" property="status" value="0">
								 	（<bean:write name="employeeContractSBVO" property="decodeStatus" />）
								 </logic:notEqual>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div>
		<div id="_tabContent3" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="employeeContractCBVOs">
					<logic:iterate id="employeeContractCBVO" name="employeeContractCBVOs" indexId="number">
						<li>
							<logic:equal name="employeeContractCBVO" property="status" value="0">
							</logic:equal>
								 <logic:notEmpty name="employeeContractCBVO" property="startDate">
								 	<bean:write name="employeeContractCBVO" property="startDate" />
								 </logic:notEmpty>
								 <logic:notEmpty name="employeeContractCBVO" property="endDate">
								 	 ~ <bean:write name="employeeContractCBVO" property="endDate" />
								 </logic:notEmpty>
							<logic:notEqual name="employeeContractCBVO" property="status" value="0">
								（<bean:write name="employeeContractCBVO" property="decodeStatus" />）
							</logic:notEqual>
						 </li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		<div id="_tabContent4" class="kantab" style="display:none">
			<ol class="auto">
				<logic:notEmpty name="employeeContractLeaveVOs">
					<logic:iterate id="employeeContractLeaveVO" name="employeeContractLeaveVOs" indexId="number">
						<li>
							<bean:write name="employeeContractLeaveVO" property="remark" />
							<logic:equal name="employeeContractLeaveVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div> 
		<div id="_tabContent5" class="kantab" style="display:none">
			<logic:notEmpty name="employeeContractForm" property="flag">
				<logic:equal name="employeeContractForm" property="flag" value="2">
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="business.employee.contract.ot.apply.first" /></label>
							<%	
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
							<logic:equal name="employeeContractOTVO" property="status" value="2">
								<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
							</logic:equal>
						</li>
					</logic:iterate>
				</logic:notEmpty>
			</ol>
		</div>
			<div id="_tabContent6" class="kantab" style="display:none">
				<ol class="auto">
					<logic:notEmpty name="employeeContractOtherVOs">
						<logic:iterate id="employeeContractOtherVO" name="employeeContractOtherVOs" indexId="number">
							<li>
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
										<logic:notEqual name="employeeContractOtherVO" property="cycle" value="13"></logic:notEqual>
										<bean:write name="employeeContractOtherVO" property="decodeCycle" />
									</logic:notEqual>
								）
								<bean:write name="employeeContractOtherVO" property="startDate" />
								<logic:notEmpty name="employeeContractOtherVO" property="endDate"> ~ <bean:write name="employeeContractOtherVO" property="endDate" /></logic:notEmpty>
								<logic:equal name="employeeContractOtherVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
			<div id="_tabContent7" class="kantab" style="display:none">
				<ol class="auto">
					<logic:notEmpty name="employeeContractSettlementVOs">
						<logic:iterate id="employeeContractSettlementVO" name="employeeContractSettlementVOs" indexId="number">
							<li>
								<bean:write name="employeeContractSettlementVO" property="showTitle"/>
								<logic:equal name="employeeContractSettlementVO" property="status" value="2">
									<span class="highlight noPosition"><bean:message bundle="public" key="public.stop" /></span>
								</logic:equal>
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</ol>
			</div> 
	</div>
</div>
</logic:equal>

<script type="text/javascript">
		// Tab切换
		function _changeTab(cursel, n){ 
			for(i = 1; i <= n; i++){ 
				$('#_tabMenu' + i).removeClass('hover');
				$('#_tabContent' + i).hide();
			} 
			
			$('#_tabMenu' + cursel).addClass('hover');
			$('#_tabContent' + cursel).show();
		};
		
		// Tab切换
		function __changeTab(cursel, n){ 
			for(i = 1; i <= n; i++){ 
				$('#__tabMenu' + i).removeClass('hover');
				$('#__tabContent' + i).hide();
			} 
			
			$('#__tabMenu' + cursel).addClass('hover');
			$('#__tabContent' + cursel).show();
		};
</script>