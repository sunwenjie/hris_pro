<%@page import="java.util.Map"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
%>
<logic:notEmpty name="MESSAGE">
	<div id="_USER_DEFINE_MSG" class="message <bean:write name="MESSAGE_CLASS" /> fadable" style="display:none;"><bean:write name="MESSAGE" /></div>
</logic:notEmpty>
<table class="table hover" id="resultTable">
	<thead>
		<tr>
			<th class="header <%=pagedListHolder.getCurrentSortClass("fullName")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'fullName', '<%=pagedListHolder.getNextSortOrder("fullName")%>', 'tableWrapper');">
					<span id="fullName">Full Name</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("shortName")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'shortName', '<%=pagedListHolder.getNextSortOrder("shortName")%>', 'tableWrapper');">
					<span id="shortName">Short Name</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("chineseName")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'chineseName', '<%=pagedListHolder.getNextSortOrder("chineseName")%>', 'tableWrapper');">
					<span id="chineseName">Chinese Name</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("employmentEntityEN")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'employmentEntityEN', '<%=pagedListHolder.getNextSortOrder("employmentEntityEN")%>', 'tableWrapper');">
					<span id="employmentEntityEN">Employment Entity <br/>(English)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("employmentEntityZH")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'employmentEntityZH', '<%=pagedListHolder.getNextSortOrder("employmentEntityZH")%>', 'tableWrapper');">
					<span id="employmentEntityZH">Employment Entity <br/>(Chinese)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("companyInitial")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'companyInitial', '<%=pagedListHolder.getNextSortOrder("companyInitial")%>', 'tableWrapper');">
					<span id="companyInitial">Company Initial</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("buFunctionEN")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'buFunctionEN', '<%=pagedListHolder.getNextSortOrder("buFunctionEN")%>', 'tableWrapper');">
					<span id="buFunctionEN">BU/Function (English)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("buFunctionZH")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'buFunctionZH', '<%=pagedListHolder.getNextSortOrder("buFunctionZH")%>', 'tableWrapper');">
					<span id="buFunctionZH">BU/Function (Chinese)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("departmentEN")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'departmentEN', '<%=pagedListHolder.getNextSortOrder("departmentEN")%>', 'tableWrapper');">
					<span id="departmentEN">Department <br/>(English)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("departmentZH")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'departmentZH', '<%=pagedListHolder.getNextSortOrder("departmentZH")%>', 'tableWrapper');">
					<span id="departmentZH">Department <br/>(Chinese)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("costCenter")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'costCenter', '<%=pagedListHolder.getNextSortOrder("costCenter")%>', 'tableWrapper');">
					<span id="costCenter">Cost Center</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("functionCode")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'functionCode', '<%=pagedListHolder.getNextSortOrder("functionCode")%>', 'tableWrapper');">
					<span id="functionCode">Function Code</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("location")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'location', '<%=pagedListHolder.getNextSortOrder("location")%>', 'tableWrapper');">
					<span id="location">Location</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("jobRole")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'jobRole', '<%=pagedListHolder.getNextSortOrder("jobRole")%>', 'tableWrapper');">
					<span id="jobRole">Job Role</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("positionEN")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'positionEN', '<%=pagedListHolder.getNextSortOrder("positionEN")%>', 'tableWrapper');">
					<span id="positionEN">Working title/Position (English)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("positionZH")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'positionZH', '<%=pagedListHolder.getNextSortOrder("positionZH")%>', 'tableWrapper');">
					<span id="positionZH">Working title/Position (Chinese)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("jobGrade")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'jobGrade', '<%=pagedListHolder.getNextSortOrder("jobGrade")%>', 'tableWrapper');">
					<span id="jobGrade">Job Grade</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("lineBizManager")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'lineBizManager', '<%=pagedListHolder.getNextSortOrder("lineBizManager")%>', 'tableWrapper');">
					<span id="dynaColumns.yewuhuibaoxianjingli">Direct Reporting <br/>Manager (Biz Leader)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("lineHRManager")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'lineHRManager', '<%=pagedListHolder.getNextSortOrder("lineHRManager")%>', 'tableWrapper');">
					<span id="lineHRManager">Direct Reporting <br/>Manager (People Manager)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("seniorityDate")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'seniorityDate', '<%=pagedListHolder.getNextSortOrder("seniorityDate")%>', 'tableWrapper');">
					<span id="seniorityDate">Seniority Date</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("employmentDate")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'employmentDate', '<%=pagedListHolder.getNextSortOrder("employmentDate")%>', 'tableWrapper');">
					<span id="employmentDate">Employment Date</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("shareOptions")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'shareOptions', '<%=pagedListHolder.getNextSortOrder("shareOptions")%>', 'tableWrapper');">
					<span id="shareOptions">Current <br/>Stock/Share <br/>Options</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("lastYearPerformanceRating")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'lastYearPerformanceRating', '<%=pagedListHolder.getNextSortOrder("lastYearPerformanceRating")%>', 'tableWrapper');">
					<span id="lastYearPerformanceRating">Last <br/>Performance <br/>Rating</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("lastYearPerformancePromotion")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'lastYearPerformancePromotion', '<%=pagedListHolder.getNextSortOrder("lastYearPerformancePromotion")%>', 'tableWrapper');">
					<span id="lastYearPerformancePromotion">Last YERR <br/>Promotion</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("midYearPromotion")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'midYearPromotion', '<%=pagedListHolder.getNextSortOrder("midYearPromotion")%>', 'tableWrapper');">
					<span id="midYearPromotion">Last Mid-Year <br/>Review <br/>Promotion</span>&nbsp;&nbsp;
					<!-- TODO -->
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("midYearSalaryIncrease")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'midYearSalaryIncrease', '<%=pagedListHolder.getNextSortOrder("midYearSalaryIncrease")%>', 'tableWrapper');">
					<span id="midYearSalaryIncrease">Last Mid-Year <br/>Review Salary <br/>Increase %</span>&nbsp;&nbsp;
					<!-- TODO -->
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("currencyCode")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'currencyCode', '<%=pagedListHolder.getNextSortOrder("currencyCode")%>', 'tableWrapper');">
					<span id="currency">Currency <br/>Code</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("baseSalaryLocal")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'baseSalaryLocal', '<%=pagedListHolder.getNextSortOrder("baseSalaryLocal")%>', 'tableWrapper');">
					<span id="baseSalaryLocal">Current <br/>Monthly Base <br/>Pay (Local)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("baseSalaryUSD")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'baseSalaryUSD', '<%=pagedListHolder.getNextSortOrder("baseSalaryUSD")%>', 'tableWrapper');">
					<span id="baseSalaryUSD">Current <br/>Monthly Base <br/>Pay (USD)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("annualBaseSalaryLocal")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'annualBaseSalaryLocal', '<%=pagedListHolder.getNextSortOrder("annualBaseSalaryLocal")%>', 'tableWrapper');">
					<span id="annualBaseSalaryLocal">Current <br/>Annual Base <br/>Pay (Local)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("annualBaseSalaryUSD")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'annualBaseSalaryUSD', '<%=pagedListHolder.getNextSortOrder("annualBaseSalaryUSD")%>', 'tableWrapper');">
					<span id="annualBaseSalaryUSD">Current <br/>Annual Base <br/>Pay (USD)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("housingAllowanceLocal")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'housingAllowanceLocal', '<%=pagedListHolder.getNextSortOrder("housingAllowanceLocal")%>', 'tableWrapper');">
					<span id="housingAllowanceLocal">Current <br/>Annual Housing <br/>Allowance (Local)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("childrenEduAllowanceLocal")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'childrenEduAllowanceLocal', '<%=pagedListHolder.getNextSortOrder("childrenEduAllowanceLocal")%>', 'tableWrapper');">
					<span id="childrenEduAllowanceLocal">Current <br/>Annual Children <br/>Education Allowance (Local)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("guaranteedCashLocal")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'guaranteedCashLocal', '<%=pagedListHolder.getNextSortOrder("guaranteedCashLocal")%>', 'tableWrapper');">
					<span id="guaranteedCashLocal">Annual <br/>Guaranteed <br/>Cash (Local)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("guaranteedCashUSD")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'guaranteedCashUSD', '<%=pagedListHolder.getNextSortOrder("guaranteedCashUSD")%>', 'tableWrapper');">
					<span id="guaranteedCashUSD">Annual <br/>Guaranteed <br/>Cash (USD)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("monthlyTarget")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'monthlyTarget', '<%=pagedListHolder.getNextSortOrder("monthlyTarget")%>', 'tableWrapper');">
					<span id="monthlyTarget">Current Incentive <br/>(Monthly Target)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("quarterlyTarget")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'quarterlyTarget', '<%=pagedListHolder.getNextSortOrder("quarterlyTarget")%>', 'tableWrapper');">
					<span id="quarterlyTarget">Current Incentive <br/>(Quarterly Target)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("gpTarget")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'gpTarget', '<%=pagedListHolder.getNextSortOrder("gpTarget")%>', 'tableWrapper');">
					<span id="gpTarget">Current Incentive <br/>(Target - &#37; of R/GP)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("targetValueLocal")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'targetValueLocal', '<%=pagedListHolder.getNextSortOrder("targetValueLocal")%>', 'tableWrapper');">
					<span id="targetValueLocal">Current Incentive <br/>Value (Local)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("targetValueUSD")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'targetValueLocal', '<%=pagedListHolder.getNextSortOrder("targetValueUSD")%>', 'tableWrapper');">
					<span id="targetValueUSD">Current Incentive <br/>Value (USD)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("ttcLocal")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'ttcLocal', '<%=pagedListHolder.getNextSortOrder("ttcLocal")%>', 'tableWrapper');">
					<span id="ttcLocal">Current TTC <br/>(Local)</span>&nbsp;&nbsp;
				</a>
			</th>
			<th class="header <%=pagedListHolder.getCurrentSortClass("ttcUSD")%> center">
				<a onclick="submitForm('searchPerformanceForm', null, null, 'ttcUSD', '<%=pagedListHolder.getNextSortOrder("ttcUSD")%>', 'tableWrapper');">
					<span id="ttcUSD">Current TTC <br/>(USD)</span>&nbsp;&nbsp;
				</a>
			</th>
			
			
			<%-- Bottom 绩效评估部分 --%>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">${performanceForm.yearly } <br/>Performance <br/>Rating</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Promotion( P ) <br/>or Reclass( R ) <br/>or Nil</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Recommend <br/>TTC Increase &#37;</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">TTC Increase &#37;</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New TTC (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New TTC (USD)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Monthly <br/>Base Pay (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Monthly <br/>Base Pay (USD)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Annual <br/>Base Pay (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Annual <br/>Base Pay (USD)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New <br/>Annual Housing <br/>Allowance (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New <br/>Annual Children Education <br/>Allowance (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New <br/>Annual Guaranteed <br/>Allowance (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New <br/>Annual Guaranteed <br/>Allowance (USD)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Incentive <br/>(Monthly Target)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Incentive <br/>(Quarterly Target)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Incentive <br/>(Target - &#37; of R/GP)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Incentive <br/>Value (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Incentive <br/>Value (USD)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Job Grade</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">New Internal Title</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Working title/Position <br/>(English)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Working title/Position <br/>(Chinese)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Recommended <br/>Stock/Share Options Award <br/>(Y or N)</span>&nbsp;&nbsp;
			</th>
			
			
			<th class="header-nosort center">
				<span id="">Target Bonus for <br/>payout (Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center">
				<span id="">Proposed Bonus <br/>Payout (xx month)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center">
				<span id="">Proposed Payout <br/>(Local)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center">
				<span id="">Proposed Payout <br/>(USD)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center">
				<span id="">Comments</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center">
				<span id="">Updated By (HR)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center">
				<span id="">Updated Date</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Working Days</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Annual Bonus (Target)</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Annual Remuneration</span>&nbsp;&nbsp;
			</th>
			<th class="header-nosort center" style="background: #DAA520;color: black;">
				<span id="">Actual Year-End Bonus</span>&nbsp;&nbsp;
			</th>
		</tr>
	</thead>
	
	<logic:notEqual name="pagedListHolder" property="holderSize" value="0">
		<tbody id="mytab">
			<logic:iterate id="performanceVO" name="pagedListHolder" property="source" indexId="number">
			<bean:define id="defineEmployeeReportVO" name="performanceVO" toScope="request"></bean:define>
				<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
					<td class="left">
					<%-- 	<a onclick="link('performanceAction.do?proc=to_objectModify&id=<bean:write name="performanceVO" property="encodedId" />')">
						<bean:write name="performanceVO" property="fullName" /></a> --%>
						
						<bean:write name="performanceVO" property="fullName" />
					</td>
					<td class="left"><bean:write name="performanceVO" property="shortName" /></td>
					<td class="left"><bean:write name="performanceVO" property="chineseName" /></td>
					<td class="left"><bean:write name="performanceVO" property="employmentEntityEN" /></td>
					<td class="left"><bean:write name="performanceVO" property="employmentEntityZH" /></td>
					<td class="left"><bean:write name="performanceVO" property="companyInitial" /></td>
					<td class="left"><bean:write name="performanceVO" property="buFunctionEN" /></td>
					<td class="left"><bean:write name="performanceVO" property="buFunctionZH" /></td>
					<td class="left"><bean:write name="performanceVO" property="departmentEN" /></td>
					<td class="left"><bean:write name="performanceVO" property="departmentZH" /></td>
					<td class="left"><bean:write name="performanceVO" property="costCenter" /></td>
					<td class="left"><bean:write name="performanceVO" property="functionCode" /></td>
					<td class="left"><bean:write name="performanceVO" property="location" /></td>
					<td class="left"><bean:write name="performanceVO" property="jobRole" /></td>
					<td class="left"><bean:write name="performanceVO" property="positionEN" /></td>
					<td class="left"><bean:write name="performanceVO" property="positionZH" /></td>
					<td class="left"><bean:write name="performanceVO" property="jobGrade" /></td>
					<td class="left"><bean:write name="performanceVO" property="lineBizManager" /></td>
					<td class="left"><bean:write name="performanceVO" property="lineHRManager" /></td>
					<td class="left"><bean:write name="performanceVO" property="seniorityDate" /></td>
					<td class="left"><bean:write name="performanceVO" property="employmentDate" /></td>
					<td class="left"><bean:write name="performanceVO" property="shareOptions" /></td>
					<td class="left"><bean:write name="performanceVO" property="lastYearPerformanceRating" /></td>
					<td class="left"><bean:write name="performanceVO" property="lastYearPerformancePromotion" /></td>
					<td class="left"><bean:write name="performanceVO" property="midYearPromotion" /></td>
					<td class="left"><bean:write name="performanceVO" property="midYearSalaryIncrease" /></td>
					<td class="left"><bean:write name="performanceVO" property="currencyCode" /></td>
					<td class="right"><bean:write name="performanceVO" property="baseSalaryLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="baseSalaryUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="annualBaseSalaryLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="annualBaseSalaryUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="housingAllowanceLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="childrenEduAllowanceLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="guaranteedCashLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="guaranteedCashUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="monthlyTarget" /></td>
					<td class="right"><bean:write name="performanceVO" property="quarterlyTarget" /></td>
					<td class="right">
						<logic:equal name="performanceVO" property="yesGPTarget" value="1">
							YES
						</logic:equal>
						<logic:notEqual name="performanceVO" property="yesGPTarget" value="1">
							<bean:write name="performanceVO" property="gpTarget" />
						</logic:notEqual>
					</td>
					<td class="right"><bean:write name="performanceVO" property="targetValueLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="targetValueUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="ttcLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="ttcUSD" /></td>
					
					<%-- Bottom 绩效评估部分 --%>
					<td class="right"><bean:write name="performanceVO" property="yearPerformanceRating" /></td>
					<td class="right"><bean:write name="performanceVO" property="yearPerformancePromotion" /></td>
					<td class="right"><bean:write name="performanceVO" property="recommendTTCIncrease" /></td>
					<td class="right"><bean:write name="performanceVO" property="ttcIncrease" /></td>
					<td class="right"><bean:write name="performanceVO" property="newTTCLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="newTTCUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="newBaseSalaryLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="newBaseSalaryUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="newAnnualSalaryLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="newAnnualSalaryUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="newAnnualHousingAllowance" /></td>
					<td class="right"><bean:write name="performanceVO" property="newAnnualChildrenEduAllowance" /></td>
					<td class="right"><bean:write name="performanceVO" property="newAnnualGuaranteedAllowanceLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="newAnnualGuatanteedAllowanceUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="newMonthlyTarget" /></td>
					<td class="right"><bean:write name="performanceVO" property="newQuarterlyTarget" /></td>
					<td class="right">
						<logic:equal name="performanceVO" property="yesNewGPTarget" value="1">
							YES
						</logic:equal>
						<logic:notEqual name="performanceVO" property="yesNewGPTarget" value="1">
							<bean:write name="performanceVO" property="newGPTarget" />
						</logic:notEqual>
					<td class="right"><bean:write name="performanceVO" property="newTargetValueLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="newTargetValueUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="newJobGrade" /></td>
					<td class="right"><bean:write name="performanceVO" property="newInternalTitle" /></td>
					<td class="right"><bean:write name="performanceVO" property="newPositionEN" /></td>
					<td class="right"><bean:write name="performanceVO" property="newPositionZH" /></td>
					<td class="right"><bean:write name="performanceVO" property="newShareOptions" /></td>
					<td class="right"><bean:write name="performanceVO" property="targetBonus" /></td>
					<td class="right"><bean:write name="performanceVO" property="proposedBonus" /></td>
					<td class="right"><bean:write name="performanceVO" property="proposedPayoutLocal" /></td>
					<td class="right"><bean:write name="performanceVO" property="proposedPayoutUSD" /></td>
					<td class="right"><bean:write name="performanceVO" property="description" /></td>
					<td class="right"><bean:write name="performanceVO" property="decodeModifyBy" /></td>
					<td class="right"><bean:write name="performanceVO" property="decodeModifyDate" /></td>
					<td class="right"><bean:write name="performanceVO" property="workingDays" /></td>
					<td class="right"><bean:write name="performanceVO" property="currentYearEndBonus" /></td>
					<td class="right"><bean:write name="performanceVO" property="remark2" /></td>
					<td class="right"><bean:write name="performanceVO" property="yearEndBonus" /></td>
				</tr>
			</logic:iterate>
		</tbody>
	</logic:notEqual>
	<logic:present name="pagedListHolder">
		<tfoot>
			<tr class="total">
				<td colspan="60" class="left">
				<label>&nbsp;<bean:message bundle="public" key="page.total" />： <bean:write name="pagedListHolder" property="holderSize" /> </label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.current" />：<bean:write name="pagedListHolder" property="indexStart" /> - <bean:write name="pagedListHolder" property="indexEnd" /> </label> 
				<label>&nbsp;&nbsp;<a onclick="submitForm('searchPerformanceForm', null, '<bean:write name="pagedListHolder" property="firstPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.first" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPerformanceForm', null, '<bean:write name="pagedListHolder" property="previousPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.previous" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPerformanceForm', null, '<bean:write name="pagedListHolder" property="nextPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.next" /></a></label> 
				<label>&nbsp;<a onclick="submitForm('searchPerformanceForm', null, '<bean:write name="pagedListHolder" property="lastPage" />', null, null, 'tableWrapper');"><bean:message bundle="public" key="page.last" /></a></label> 
				<label>&nbsp;&nbsp;<bean:message bundle="public" key="page.pagination" />：<bean:write name="pagedListHolder" property="realPage" />/<bean:write name="pagedListHolder" property="pageCount" /> </label>&nbsp;</td>
			</tr>
		</tfoot>
	</logic:present>
</table>

<script>
	(function($){
		useFixColumn();
		var colspanSize = 64;
		if( $('tbody#mytab tr').find('td').length != 0 ){
			colspanSize = $('tbody#mytab tr').find('td').length;
		}
		$('tr.total td').attr( 'colspan', colspanSize );
	})(jQuery);
</script>